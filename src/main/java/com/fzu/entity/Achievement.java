package com.fzu.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "Achievement", description = "Achievement")
public class Achievement {
    private String id;
    private String type;
    private String title;
    private String description;
    private String rank;
}
