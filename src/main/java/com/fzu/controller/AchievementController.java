package com.fzu.controller;

import com.fzu.entity.Achievement;
import com.fzu.result.ServiceResult;
import com.fzu.service.AchievementService;
import com.fzu.utils.Page;
import com.fzu.vo.AchievementAddVO;
import com.fzu.vo.AchievementPageVO;
import com.fzu.vo.AchievementUpdateVO;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/achievement")
@Api(value = "AchievementController", tags = "学院风采管理模块")
public class AchievementController {
    @Autowired
    private AchievementService achievementService;

    @ApiOperation(value = "创建学院风采")
    @PostMapping(value = "/add")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Achievement> addAchievement(@Validated @RequestBody AchievementAddVO achievementAddVO, BindingResult bindingResult) {
        //参数验证
        if (bindingResult.hasErrors()) {
            return ServiceResult.createByErrorMessage(String.valueOf(bindingResult.getFieldErrors()));
        }
        if (achievementService.getByTitle(achievementAddVO.getTitle()) != null) {
            return ServiceResult.createByErrorMessage("已存在该学院风采信息");
        }
        Achievement achievement = new Achievement();
        BeanUtils.copyProperties(achievementAddVO, achievement);
        if (!achievementService.save(achievement)) {
            return ServiceResult.createByErrorMessage("创建失败");
        }
        return ServiceResult.createBySuccess(achievement);
    }

    @ApiOperation(value = "删除学院风采")
    @PostMapping("/delete/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Achievement> deleteById(@PathVariable String id) {
        Achievement achievement = achievementService.getById(id);
        if (achievement == null) {
            return ServiceResult.createByErrorMessage("学院风采不存在");
        }
        if (!achievementService.removeById(id)) {
            return ServiceResult.createByErrorMessage("删除失败");
        }
        return ServiceResult.createBySuccess(achievement);
    }

    @ApiOperation(value = "修改学院风采")
    @PostMapping(value = "/update")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Achievement> updateById(@Validated @RequestBody AchievementUpdateVO achievementUpdateVO, BindingResult bindingResult) {
        //参数验证
        if (bindingResult.hasErrors()) {
            return ServiceResult.createByErrorMessage(String.valueOf(bindingResult.getFieldErrors()));
        }
        Achievement achievement = achievementService.getById(achievementUpdateVO.getId());
        if (achievement == null) {
            return ServiceResult.createByErrorMessage("学院风采不存在");
        }
        BeanUtils.copyProperties(achievementUpdateVO, achievement);
        if (!achievementService.updateById(achievement)) {
            return ServiceResult.createByErrorMessage("修改失败");
        }
        return ServiceResult.createBySuccess(achievement);
    }

    @ApiOperation(value = "查找学院风采列表")
    @PostMapping("/select")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Achievement> selectPage(@RequestBody @ApiParam(value = "AchievementPageVO") AchievementPageVO pageVO, BindingResult bindingResult) {
        //参数验证
        if (bindingResult.hasErrors()) {
            return ServiceResult.createByErrorMessage(String.valueOf(bindingResult.getFieldErrors()));
        }
        Page<Achievement> page = new Page<>(pageVO.getPageNo(), pageVO.getPageSize());
        Page<Achievement> returnPage = achievementService.findPage(page, pageVO);
        return ServiceResult.createBySuccess(returnPage.getList(), Math.toIntExact(returnPage.getCount()));
    }
}
