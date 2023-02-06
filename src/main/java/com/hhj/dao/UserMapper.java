package com.hhj.dao;

import com.hhj.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User selectUserById(@Param("userId") int userId);

    int selectByName(@Param("name") String username);

    int selectByEmail(@Param("email") String email);

    int insertUser(@Param("user") User user);

    void updateStatus(@Param("userId") int userId, @Param("status") int activationSuccess);

    int getUserId(@Param("username") String username);
}
