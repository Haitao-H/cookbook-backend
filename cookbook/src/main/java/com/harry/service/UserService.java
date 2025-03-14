package com.harry.service;

import com.harry.pojo.User;

public interface UserService {

    // find user by id
    User findByUserName(String username);

    // add user
    void register(String username, String email, String password);

    // update user basic info
    void update(User user);

    // update user avatar
    void updateAvatar(String avatarUrl);

    // update password
    void updatePassword(String newPassword);
}
