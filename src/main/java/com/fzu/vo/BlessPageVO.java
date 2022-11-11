package com.fzu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel(value = "BlessPageVO", description = "BlessPageVO")
public class BlessPageVO {
    @ApiModelProperty(value = "祝福内容", example = "好")
    private String bless;

    @ApiModelProperty(value = "审核状态", example = "0")
    private String status;

    @ApiModelProperty(value = "上传者", example = "abc")
    private String createBy;

    @ApiModelProperty(name = "date", value = "日期范围", example = "", required = true, dataType = "String")
    private String date;

    @Min(value = 1, message = "当前页码必须为整数或者大于等于1")
    @ApiModelProperty(value = "当前页码", name = "pageNo", example = "1")
    private Integer pageNo;

    @Min(value = 1, message = "每页数量必须为整数或者大于等于1")
    @ApiModelProperty(value = "每页数量", name = "pageSize", example = "20")
    private Integer pageSize;
}
