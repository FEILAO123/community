package com.hhj.service.impl;

import com.hhj.dao.UserMapper;
import com.hhj.entity.User;
import com.hhj.service.UserService;
import com.hhj.utils.CommunityUtil;
import com.hhj.utils.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public User selectUserById(int id) {
        User user = userMapper.selectUserById(id);
        return user;
    }

    @Override
    public Map<String,Object> register(User user)  {
        Map<String,Object> map = new HashMap<>();
        if (user == null){
            throw new IllegalArgumentException("参数传递异常");
        }
        if (StringUtils.isBlank(user.getUsername())
                || StringUtils.isBlank(user.getPassword())
                ||StringUtils.isBlank(user.getEmail())){
            map.put("registerERROR","存在用户为空信息");
            return map;
        }
        //如果上述条件都不成立，开始业务逻辑
        //1.校验账号是否存在
        int countByName = userMapper.selectByName(user.getUsername());
        if (countByName>0){
            map.put("usernameERROR","账号已存在，请重新输入");
            return map;
        }
        //2. 此时数据库中不存在该账号，进行下一步对邮箱的验证
        int countByEmail = userMapper.selectByEmail(user.getEmail());
        if (countByEmail>0){
            map.put("emailERROR","邮箱已存在，请重新输入");
            return map;
        }
        //3. 此时没有重复的用户信息，进行数据的添加
        user.setSalt(CommunityUtil.generateUUID());
        user.setPassword(CommunityUtil.md5(user.getPassword()+user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID().substring(0,5));
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1001)));
        int status = userMapper.insertUser(user);
        //4. status>0注册成功，向用户发送激活邮件
        if (status<0){
            map.put("registerERROR","数据请求错误");
        }
        //获取userid
        int userId = userMapper.getUserId(user.getUsername());
        Context context = new Context();
        context.setVariable("email",user.getEmail());
        String url = domain + contextPath +"/activation/" + userId +"/" +user.getActivationCode();
        context.setVariable("url",url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(),"账号激活",content);
        return map;
    }

    @Override
    public int activation(int userId,String code){
        User user = userMapper.selectUserById(userId);
        if (user.getStatus() == 1){
            //说明已经激活过了
            return ACTIVATION_REPEAT;
        }else if (user.getActivationCode().equals(code)){
            userMapper.updateStatus(userId,ACTIVATION_SUCCESS);
            //说明成功激活
            return ACTIVATION_SUCCESS;
        }else {
            return ACTIVATION_FAILURE;
        }
    }
}
