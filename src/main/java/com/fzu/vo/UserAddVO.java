package com.fzu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "UserAddVO", description = "UserAddVO")
public class UserAddVO {
    @ApiModelProperty(value = "openId", example = "test1")
    @NotBlank(message = "openId不能为空")
    private String openId;
}
