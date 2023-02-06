package com.hhj.controller;

import com.hhj.entity.DiscussPost;
import com.hhj.entity.Page;
import com.hhj.entity.User;
import com.hhj.service.impl.DiscussPostServiceImpl;
import com.hhj.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@Controller
@Slf4j
public class HomeController {

    @Autowired
    private DiscussPostServiceImpl discussPostService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/index")
    public String getIndexPage(Model model,Page page){
        log.info("start.....");
        page.setRows(discussPostService.DiscussPostRows(0));
        page.setPath("/index");
        List<DiscussPost> posts = discussPostService.selectDiscussPost(0, page.getOffset(), page.getLimit());
        //因为上面只是获取了帖子，现在需要一个集合能够存储帖子和用户
        List<Map<String,Object>> discussPosts = new ArrayList<>();
        if (posts !=null){
            for (DiscussPost post : posts) {
                Map<String,Object> map = new HashMap<>();
                User user = userService.selectUserById(post.getUserId());
                map.put("post",post);
                map.put("user",user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "/index";
    }

    @GetMapping("/register")
    public String toRegister(){
        return "/site/register";
    }

    @GetMapping("/login")
    public String toLogin(){
        return "/site/login";
    }
}
