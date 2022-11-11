package com.fzu.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "Program", description = "Program")
public class Program {
    private String id;
    private String name;
    private String type;
    private Integer priority;
}
