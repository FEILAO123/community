package com.hhj.dao;

import com.hhj.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User selectUserById(@Param("userId") int userId);
}
