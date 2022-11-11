package com.fzu.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "User", description = "User")
public class User {
    private String id;
    private String openId;
    private String sid;
    private String name;
    private String email;
    private String dormitory;
    private String sex;
    private Date graduationTime;
    private String banFlag;
}
