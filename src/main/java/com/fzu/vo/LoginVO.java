package com.fzu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "LoginVO", description = "LoginVO")
public class LoginVO {

    @ApiModelProperty(name = "loginname", value = "登陆名", example = "", required = true, dataType = "String")
    @NotBlank(message = "登陆名不能为空")
    private String loginname;

    @ApiModelProperty(name = "password", value = "密码", example = "", required = true, dataType = "String")
    @NotBlank(message = "密码不能为空")
    private String password;
}
