package com.fzu.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "UserClass", description = "UserClass")
public class UserClass {
    private String uid;
    private String sid;
    private String cid;
}
