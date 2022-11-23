package com.fzu.controller;

import com.fzu.entity.Background;
import com.fzu.result.ServiceResult;
import com.fzu.service.BackgroundService;
import com.fzu.utils.Page;
import com.fzu.utils.PictureUtil;
import com.fzu.vo.BackgroundPageVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/background")
@Api(value = "BackgroundController", tags = "虚拟背景管理模块")
public class BackgroundController {
    @Autowired
    private BackgroundService backgroundService;

    @Autowired
    private PictureUtil pictureUtil;

    @ApiOperation(value = "创建虚拟背景")
    @PostMapping(value = "/add", consumes = "multipart/*", headers = "content-type=multipart/form-date")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Background> addBackground(
            @RequestParam @ApiParam(value = "名称", required = true, type = "query") String name,
            @ApiParam(value = "上传的照片", required = true) MultipartFile photo) {
        if (backgroundService.getByName(name) != null) {
            return ServiceResult.createByErrorMessage("已存在该名称的虚拟背景");
        }
        Background background = new Background();
        background.setName(name);
        try {
            pictureUtil.sshSftp(photo.getBytes(), "background_" + name + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        background.setPhoto("https://www.prxdong.top/images/background_" + name + ".jpg");
        if (!backgroundService.save(background)) {
            return ServiceResult.createByErrorMessage("创建失败");
        }
        return ServiceResult.createBySuccess(background);
    }

    @ApiOperation(value = "删除虚拟背景")
    @PostMapping("/delete/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Background> deleteById(@PathVariable String id) {
        Background background = backgroundService.getById(id);
        if (background == null) {
            return ServiceResult.createByErrorMessage("虚拟背景不存在");
        }
        if (!backgroundService.removeById(id)) {
            return ServiceResult.createByErrorMessage("删除失败");
        }
        return ServiceResult.createBySuccess(background);
    }

    @ApiOperation(value = "修改虚拟背景")
    @PostMapping(value = "/update", consumes = "multipart/*", headers = "content-type=multipart/form-date")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Background> updateById(
            @RequestParam @ApiParam(value = "编号", required = true, type = "query") String id,
            @RequestParam @ApiParam(value = "名称", required = true, type = "query") String name,
            @ApiParam(value = "上传的照片", required = true) MultipartFile photo) {
        Background background = backgroundService.getById(id);
        if (background == null) {
            return ServiceResult.createByErrorMessage("虚拟背景不存在");
        }
        background.setName(name);
        try {
            pictureUtil.sshSftp(photo.getBytes(), "background_" + name + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        background.setPhoto("https://www.prxdong.top/images/background_" + name + ".jpg");
        if (!backgroundService.updateById(background)) {
            return ServiceResult.createByErrorMessage("修改失败");
        }
        return ServiceResult.createBySuccess(background);
    }


    @ApiOperation(value = "查找虚拟背景列表")
    @PostMapping("/select")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Background> selectPage(@RequestBody @ApiParam(value = "BackgroundPageVO") BackgroundPageVO pageVO, BindingResult bindingResult) {
        //参数验证
        if (bindingResult.hasErrors()) {
            return ServiceResult.createByErrorMessage(String.valueOf(bindingResult.getFieldErrors()));
        }
        Page<Background> page = new Page<>(pageVO.getPageNo(), pageVO.getPageSize());
        Page<Background> returnPage = backgroundService.findPage(page, pageVO);
        return ServiceResult.createBySuccess(returnPage.getList(), Math.toIntExact(returnPage.getCount()));
    }
}
