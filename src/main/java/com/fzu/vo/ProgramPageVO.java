package com.fzu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel(value = "ProgramPageVO", description = "ProgramPageVO")
public class ProgramPageVO {

    @Min(value = 1, message = "当前页码必须为整数或者大于等于1")
    @ApiModelProperty(value = "当前页码", name = "pageNo", example = "1")
    private Integer pageNo;

    @Min(value = 1, message = "每页数量必须为整数或者大于等于1")
    @ApiModelProperty(value = "每页数量", name = "pageSize", example = "20")
    private Integer pageSize;
}
