package com.fzu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(value = "AchievementUpdateVO", description = "AchievementUpdateVO")
public class AchievementUpdateVO {
    @ApiModelProperty(value = "id", example = "123")
    @NotBlank(message = "编号不能为空")
    private String id;

    @ApiModelProperty(value = "title", example = "江南皮革厂倒闭啦")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "type", example = "科研成果")
    @NotBlank(message = "种类不能为空")
    private String type;

    @ApiModelProperty(value = "description", example = "哈哈哈哈哈哈哈哈哈")
    @NotBlank(message = "描述不能为空")
    private String description;

    @ApiModelProperty(value = "time", example = "2018-07-01")
    @NotNull(message = "时间不能为空")
    private Date time;
}
