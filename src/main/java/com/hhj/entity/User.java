package com.hhj.entity;


import com.hhj.utils.CommunityUtil;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.Random;

@Data
@ToString
public class User {
    //与其他表相连，在其他表中为userId
    private Integer id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private Integer type ;
    private Integer status;
    private String activationCode;
    private String headerUrl;
    private Date createTime;
}
