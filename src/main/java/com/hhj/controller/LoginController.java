package com.hhj.controller;

import com.hhj.entity.User;
import com.hhj.service.impl.UserServiceImpl;
import com.hhj.utils.CommuniryConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class LoginController implements CommuniryConstant {
    @Autowired
    private UserServiceImpl userService;

    //注册请求
    @PostMapping("/register")
    public String register(Model model, User user)  {
        Map<String,Object> map = userService.register(user);
        if (map==null || map.isEmpty()){
            //注册成功，则跳到首页，因为还没有激活，所以不能跳到登陆页面
            model.addAttribute("msg","注册成功，已向您邮箱发送一封激活邮件");
            model.addAttribute("target","/index");
            return "/site/operate-result";
        }else {
            //注册失败,则传递失败的信息
            model.addAttribute("registerERROR",map.get("registerERROR"));
            model.addAttribute("usernameERROR",map.get("usernameERROR"));
            model.addAttribute("emailERROR",map.get("emailERROR"));
            return "/site/register";
        }
    }

    //激活请求
    @GetMapping("/activation/{userId}/{code}")
    public String active(Model model, @PathVariable("userId")int userId,@PathVariable("code")String code){
        int result = userService.activation(userId, code);
        if (result == ACTIVATION_SUCCESS){
            //成功
            model.addAttribute("msg","激活成功!");
            model.addAttribute("target","/login");
        } else if (result == ACTIVATION_REPEAT) {
            //重复激活
            model.addAttribute("msg","无效操作，该账号已激活!");
            model.addAttribute("target","/index");
        }else {
            //激活失败
            model.addAttribute("msg","激活码错误，激活失败");
            model.addAttribute("target","/index");
        }
        return "/site/operate-result";
    }
}
