package com.hhj.utils;

public interface CommuniryConstant {
    int ACTIVATION_SUCCESS = 0;
    int ACTIVATION_REPEAT = 1;
    int ACTIVATION_FAILURE = 2;

    //默认状态登录过期时间
    int DEFAULT_EXPIRED_SECONDS = 3600;
    //记住我状态
    int REMEMBER_EXPIRED_SECONDS = 3600*24;
}
