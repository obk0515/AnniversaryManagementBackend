package com.fzu.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "Background", description = "Background")
public class Background {
    private String id;
    private String name;
    private String photo;
}
