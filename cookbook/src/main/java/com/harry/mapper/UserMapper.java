package com.harry.mapper;

import com.harry.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from cookbook.user where username=#{username}")
    User findByUserName(String username);

    @Insert("insert into cookbook.user(username, email, password, created_at, updated_at)" +
            " values(#{username}, #{email}, #{password}, now(), now())")
    void add(String username, String email, String password);

    @Update("update cookbook.user set username=#{username}, email=#{email}, updated_at=#{updatedAt} where id=#{id}")
    void update(User user);

    @Update("update cookbook.user set avatar=#{avatarUrl}, updated_at=now() where id=#{id}")
    void updateAvatar(String avatarUrl, Integer id);

    @Update("update cookbook.user set password=#{newPassword}, updated_at=now() where id=#{id}")
    void updatePassword(String newPassword, Integer id);
}
