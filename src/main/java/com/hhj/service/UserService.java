package com.hhj.service;

import com.hhj.entity.User;
import com.hhj.utils.CommuniryConstant;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserService extends CommuniryConstant {
    User selectUserById(int id);
    Map<String,Object> register (User user);
    int activation(int userId,String code);
    Map<String,Object> login(String username,String password,int expiredSeconds);
    boolean checkCode(String code, HttpSession session);
}
