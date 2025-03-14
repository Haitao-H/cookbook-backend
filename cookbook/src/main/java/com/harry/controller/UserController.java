package com.harry.controller;

import ch.qos.logback.core.util.StringUtil;
import com.harry.pojo.Result;
import com.harry.pojo.User;
import com.harry.service.UserService;
import com.harry.utils.JWTUtil;
import com.harry.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // new user register
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, String email, @Pattern(regexp = "^\\S{5,16}$") String password) {

        // check?
        User user = userService.findByUserName(username);
        if (user != null) {
            return Result.error("Username exists");
        } else {
            userService.register(username, email, encoder.encode(password));
            return Result.success();


        }
    }

    // user login
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        // query
        User user = userService.findByUserName(username);

        // user exist?
        if (user == null) {
            return Result.error("Username is incorrect");
        }

        // password check
        if (!encoder.matches(password, user.getPassword())) {
            return Result.error("Incorrect password");
        } else {
            // login success --> create token
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username", user.getUsername());
            String token = JWTUtil.createJWT(claims);
            // put token in redis
            stringRedisTemplate.opsForValue().set(token, token, 6, TimeUnit.HOURS);
            return Result.success(token);
        }

    }

    // get user info
    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        System.out.println(claims);
        String username = (String) claims.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    // update user basic info
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    // update user avatar
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    // update user password
    @PatchMapping("/updatePassword")
    public Result updatePassword(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {

        // validation
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        String confirmPassword = params.get("confirmPassword");

        // check empty
        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            return Result.error("missing information");
        }

        // check old password
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = userService.findByUserName(username);
        if (!encoder.matches(oldPassword, user.getPassword())) {
            return Result.error("old password does not match");
        }

        // new password match confirm password
        if (!newPassword.equals(confirmPassword)) {
            return Result.error("new password does not match");
        }

        // update password
        userService.updatePassword(encoder.encode(newPassword));

        // delete old token from redis
        stringRedisTemplate.opsForValue().getOperations().delete(token);

        return Result.success();
    }


}