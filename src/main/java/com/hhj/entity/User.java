package com.hhj.entity;


import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class User {
    //与其他表相连，在其他表中为userId
    private Integer id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private Integer type;
    private Integer status;
    private String activationCode;
    private String headerUrl;
    private Date createTime;
}
