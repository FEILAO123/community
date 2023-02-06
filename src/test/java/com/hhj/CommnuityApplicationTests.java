package com.hhj;

import com.hhj.dao.DiscussPostMapper;
import com.hhj.dao.UserMapper;
import com.hhj.entity.DiscussPost;
import com.hhj.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CommnuityApplication.class)
@SpringBootTest
@Slf4j
class CommnuityApplicationTests {


    @Autowired
    DiscussPostMapper discussPostMapper;


    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        //111
    }


    @Test
    public void testDiscussPost(){
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPost(101, 1, 5);
        System.out.println(discussPosts);
    }

    @Test
    public void testUserMapper(){
        User user = userMapper.selectUserById(101);
        System.out.println(user);
    }


    @Test
    public void testLog(){
        log.error("error");
        log.warn("warn");
        log.info("info");
        log.debug("debug");
    }
}
