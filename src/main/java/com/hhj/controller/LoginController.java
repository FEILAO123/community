package com.hhj.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.hhj.entity.User;
import com.hhj.service.impl.UserServiceImpl;
import com.hhj.utils.CommuniryConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController implements CommuniryConstant {
    @Autowired
    private UserServiceImpl userService;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private DefaultKaptcha kaptcha;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

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
    //生成验证码
    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse response, HttpSession session){
        //生成验证码
        String text = kaptcha.createText();
        BufferedImage image = kaptcha.createImage(text);
        //将验证码存入session
        session.setAttribute("kaptcha",text);
        //将图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image,"png",os);
        } catch (IOException e) {
            logger.error("响应验证码失败"+e.getMessage());
        }
    }

    @PostMapping("/login")
    public String login(Model model,String username,String password,String code,Boolean rememberMe,HttpSession session,HttpServletResponse response){
        boolean checkCode = userService.checkCode(code,session);
        if (!checkCode){
            model.addAttribute("codeMsg","验证码错误，请重新输入");
            return "/site/login";
        }
        //检查账号密码是否正确
        int expiredSeconds = rememberMe ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
        Map<String, Object> map = userService.login(username, password, expiredSeconds);
        if (map.containsKey("ticket")){
            Cookie cookie = new Cookie("token", map.get("ticket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
            return "redirect:/index";
        }else {
            model.addAttribute("usernameMsg",map.get("AccountIsNULL"));
            model.addAttribute("passwordMsg",map.get("passwordERROR"));
            return "/site/login";
        }
    }
    @GetMapping("/logout")
    public String logout(){
        return "/site/login";
    }
}
