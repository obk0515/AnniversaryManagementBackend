package com.fzu.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "Class", description = "Class")
public class Class {
    private String id;
    private String cid;
    private String stage;
    private String academy;
    private String grade;
    private String major;
    private Integer classNo;
}
