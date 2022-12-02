package com.fzu.controller;

import com.fzu.entity.Visit;
import com.fzu.result.ServiceResult;
import com.fzu.service.VisitService;
import com.fzu.utils.Page;
import com.fzu.utils.PictureUtil;
import com.fzu.vo.VisitPageVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/visit")
@Api(value = "VisitController", tags = "云参观管理模块")
public class VisitController {
    @Autowired
    private VisitService visitService;

    @Autowired
    private PictureUtil pictureUtil;

    @ApiOperation(value = "创建云参观图片")
    @PostMapping(value = "/add", consumes = "multipart/*", headers = "content-type=multipart/form-date")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Visit> addBackground(
            @RequestParam @ApiParam(value = "名称", required = true, type = "query") String name,
            @ApiParam(value = "上传的照片", required = true) MultipartFile photo) {
        if (visitService.getByName(name) != null) {
            return ServiceResult.createByErrorMessage("已存在该名称的云参观图片");
        }
        Visit visit = new Visit();
        visit.setName(name);
        try {
            pictureUtil.sshSftp(photo.getBytes(), "visit_" + name + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        visit.setPhoto("https://www.prxdong.top/images/visit_" + name + ".jpg");
        if (!visitService.save(visit)) {
            return ServiceResult.createByErrorMessage("创建失败");
        }
        return ServiceResult.createBySuccess(visit);
    }

    @ApiOperation(value = "删除虚拟背景")
    @PostMapping("/delete/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Visit> deleteById(@PathVariable String id) {
        Visit visit = visitService.getById(id);
        if (visit == null) {
            return ServiceResult.createByErrorMessage("虚拟背景不存在");
        }
        if (!visitService.removeById(id)) {
            return ServiceResult.createByErrorMessage("删除失败");
        }
        return ServiceResult.createBySuccess(visit);
    }

    @ApiOperation(value = "修改虚拟背景")
    @PostMapping(value = "/update", consumes = "multipart/*", headers = "content-type=multipart/form-date")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Visit> updateById(
            @RequestParam @ApiParam(value = "编号", required = true, type = "query") String id,
            @RequestParam @ApiParam(value = "名称", required = true, type = "query") String name,
            @ApiParam(value = "上传的照片", required = true) MultipartFile photo) {
        Visit visit = visitService.getById(id);
        if (visit == null) {
            return ServiceResult.createByErrorMessage("虚拟背景不存在");
        }
        visit.setName(name);
        try {
            pictureUtil.sshSftp(photo.getBytes(), "visit_" + name + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        visit.setPhoto("https://www.prxdong.top/images/visit_" + name + ".jpg");
        if (!visitService.updateById(visit)) {
            return ServiceResult.createByErrorMessage("修改失败");
        }
        return ServiceResult.createBySuccess(visit);
    }


    @ApiOperation(value = "查找虚拟背景列表")
    @PostMapping("/select")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Visit> selectPage(@RequestBody @ApiParam(value = "VisitPageVO") VisitPageVO pageVO, BindingResult bindingResult) {
        //参数验证
        if (bindingResult.hasErrors()) {
            return ServiceResult.createByErrorMessage(String.valueOf(bindingResult.getFieldErrors()));
        }
        Page<Visit> page = new Page<>(pageVO.getPageNo(), pageVO.getPageSize());
        Page<Visit> returnPage = visitService.findPage(page, pageVO);
        return ServiceResult.createBySuccess(returnPage.getList(), Math.toIntExact(returnPage.getCount()));
    }
}
