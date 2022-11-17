package com.fzu.controller;

import com.fzu.entity.Alumnus;
import com.fzu.result.ServiceResult;
import com.fzu.service.AlumnusService;
import com.fzu.utils.Page;
import com.fzu.utils.PictureUtil;
import com.fzu.vo.AlumnusPageVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/alumnus")
@Api(value = "AlumnusController", tags = "校友风采管理模块")
public class AlumnusController {
    @Autowired
    private AlumnusService alumnusService;

    @Autowired
    private PictureUtil pictureUtil;

    @ApiOperation(value = "创建校友风采")
    @PostMapping(value = "/add", consumes = "multipart/*", headers = "content-type=multipart/form-date")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true),
    })
    public ServiceResult<Alumnus> addAlumnus(
            @RequestParam @ApiParam(value = "姓名", required = true, type = "query") String name,
            @RequestParam @ApiParam(value = "职位", required = true, type = "query") String position,
            @RequestParam @ApiParam(value = "简介", required = true, type = "query") String brief,
            @ApiParam(value = "上传的照片", required = true) MultipartFile photo) {
        if (alumnusService.getByName(name) != null) {
            return ServiceResult.createByErrorMessage("已存在该校友信息");
        }
        Alumnus alumnus = new Alumnus();
        alumnus.setName(name);
        alumnus.setPosition(position);
        alumnus.setBrief(brief);
        try {
            pictureUtil.sshSftp(photo.getBytes(), name + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        alumnus.setPhoto("http://43.139.44.201/images/" + name + ".jpg");
        if (!alumnusService.save(alumnus)) {
            return ServiceResult.createByErrorMessage("创建失败");
        }
        return ServiceResult.createBySuccess(alumnus);
    }

    @ApiOperation(value = "删除校友风采")
    @PostMapping("/delete/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Alumnus> deleteById(@PathVariable String id) {
        Alumnus alumnus = alumnusService.getById(id);
        if (alumnus == null) {
            return ServiceResult.createByErrorMessage("校友风采不存在");
        }
        if (!alumnusService.removeById(id)) {
            return ServiceResult.createByErrorMessage("删除失败");
        }
        return ServiceResult.createBySuccess(alumnus);
    }

    @ApiOperation(value = "修改校友风采")
    @PostMapping(value = "/update", consumes = "multipart/*", headers = "content-type=multipart/form-date")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Alumnus> updateById(
            @RequestParam @ApiParam(value = "编号", required = true, type = "query") String id,
            @RequestParam @ApiParam(value = "姓名", required = true, type = "query") String name,
            @RequestParam @ApiParam(value = "职位", required = true, type = "query") String position,
            @RequestParam @ApiParam(value = "简介", required = true, type = "query") String brief,
            @ApiParam(value = "上传的照片", required = true) MultipartFile photo) {
        Alumnus alumnus = alumnusService.getById(id);
        if (alumnus == null) {
            return ServiceResult.createByErrorMessage("校友风采不存在");
        }
        alumnus.setName(name);
        alumnus.setPosition(position);
        alumnus.setBrief(brief);
        try {
            pictureUtil.sshSftp(photo.getBytes(), name + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        alumnus.setPhoto("http://43.139.44.201/images/" + name + ".jpg");
        if (!alumnusService.updateById(alumnus)) {
            return ServiceResult.createByErrorMessage("修改失败");
        }
        return ServiceResult.createBySuccess(alumnus);
    }

    @ApiOperation(value = "查找校友风采列表")
    @PostMapping("/select")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Alumnus> selectPage(@RequestBody @ApiParam(value = "AlumnusPageVO") AlumnusPageVO pageVO, BindingResult bindingResult) {
        //参数验证
        if (bindingResult.hasErrors()) {
            return ServiceResult.createByErrorMessage(String.valueOf(bindingResult.getFieldErrors()));
        }
        Page<Alumnus> page = new Page<>(pageVO.getPageNo(), pageVO.getPageSize());
        Page<Alumnus> returnPage = alumnusService.findPage(page, pageVO);
        return ServiceResult.createBySuccess(returnPage.getList(), Math.toIntExact(returnPage.getCount()));
    }
}
