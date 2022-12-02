package com.fzu.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "Visit", description = "Visit")
public class Visit {
    private String id;
    private String name;
    private String photo;
}
