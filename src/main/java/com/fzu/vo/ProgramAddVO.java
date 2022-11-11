package com.fzu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "ProgramAddVO", description = "ProgramAddVO")
public class ProgramAddVO {

    @ApiModelProperty(value = "name", example = "test1")
    @NotBlank(message = "节目名不能为空")
    private String name;

    @ApiModelProperty(value = "type", example = "小品")
    @NotBlank(message = "节目类型不能为空")
    private String type;

    @ApiModelProperty(value = "priority", example = "10")
    @NotNull(message = "节目优先级不能为空")
    @Min(value = 1, message = "优先级不低于1")
    @Max(value = 100, message = "优先级不超过100")
    private Integer priority;
}
