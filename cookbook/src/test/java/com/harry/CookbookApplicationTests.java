package com.harry;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.harry.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class CookbookApplicationTests {

    @Test
    void JWTGen() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "Tom");
        String token = JWT.create()
                .withClaim("user", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .sign(Algorithm.HMAC256("secret"));

        System.out.println(token);
    }

    @Test
    void JWTCheck() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3NDEyNTAxOTcsInVzZXIiOnsiaWQiOjEsInVzZXJuYW1lIjoiVG9tIn19.8vuEpN2LpKiuh6E0Pw6NW7Jz-qzdEryo_5qC4BIUlb0";
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("secret")).build().verify(token);
        System.out.println(decodedJWT.getClaims().get("user"));
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testSet() {
        stringRedisTemplate.opsForValue().set("hello", "world", 15, TimeUnit.SECONDS);
    }

    @Test
    public void testGet() {
        stringRedisTemplate.opsForValue().get("hello");
    }


}
