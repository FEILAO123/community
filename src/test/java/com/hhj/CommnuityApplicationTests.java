package com.hhj;

import com.hhj.config.JwtConfig;
import com.hhj.dao.DiscussPostMapper;
import com.hhj.dao.UserMapper;
import com.hhj.entity.DiscussPost;
import com.hhj.entity.User;
import com.hhj.utils.CommuniryConstant;
import com.hhj.utils.CommunityUtil;
import com.hhj.utils.MailClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.util.List;
import java.util.Random;

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


    @Autowired
    private MailClient mailClient;
    //模板引擎
    @Autowired
    private TemplateEngine templateEngine;
    @Test
    public void testMail(){
        mailClient.sendMail("1529834552@qq.com","测试","测试11111");
    }

    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username","李四");
        String content = templateEngine.process("/mail/demo", context);
        mailClient.sendMail("1529834552@qq.com","HTML测试",content);
    }

    @Test
    public void testIO() throws IOException {
        try {
            FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\FeiLao\\Desktop\\后端开发实习生-黄鸿杰.pdf"));
            int content;
            while ((content =inputStream.read())!=-1){
                System.out.println(content);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }




    @Test
    public void testInsert(){
        User user = new User();
        user.setSalt(CommunityUtil.generateUUID());
        user.setPassword(CommunityUtil.md5(user.getPassword()+user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID().substring(0,5));
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1001)));
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis(){
       redisTemplate.opsForValue().set("test","test1");
    }

    @Autowired
    JwtConfig jwtConfig;
    @Test
    public void testJWT(){
        String token = jwtConfig.createToken(CommunityUtil.generateUUID());
        System.out.println(token);
        redisTemplate.opsForValue().set(CommunityUtil.generateUUID(),token);

    }
}
