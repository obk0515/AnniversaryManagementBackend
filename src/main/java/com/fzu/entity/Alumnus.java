package com.fzu.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "Alumnus", description = "Alumnus")
public class Alumnus {
    private String id;
    private String name;
    private String brief;
    private String photo;
}
