package com.hhj.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha getKaptcha() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        //图片边框
        properties.setProperty("kaptcha.border", "no");
        //边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        //字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "red");
        //设置图片的高
        properties.setProperty("kaptcha.image.width", "110");
        //设置字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 字体
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        //验证码
        properties.setProperty("kaptcha.char.string","0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
