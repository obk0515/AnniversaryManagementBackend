package com.fzu.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "Bless", description = "Bless")
public class Bless {
    private String id;
    private String bless;
    private String status;
    private String createBy;
    private Date createTime;
}
