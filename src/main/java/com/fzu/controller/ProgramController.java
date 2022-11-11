package com.fzu.controller;

import com.fzu.entity.Program;
import com.fzu.result.ServiceResult;
import com.fzu.service.ProgramService;
import com.fzu.utils.Page;
import com.fzu.vo.ProgramAddVO;
import com.fzu.vo.ProgramPageVO;
import com.fzu.vo.ProgramUpdateVO;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/program")
@Api(value = "ProgramController", tags = "节目管理模块")
public class ProgramController {
    @Autowired
    private ProgramService programService;

    @ApiOperation(value = "创建节目")
    @PostMapping("/add")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Program> addProgram(@Validated @RequestBody ProgramAddVO programAddVO, BindingResult bindingResult) {
        //参数验证
        if (bindingResult.hasErrors()) {
            return ServiceResult.createByErrorMessage(String.valueOf(bindingResult.getFieldErrors()));
        }
        if (programService.getByPriority(programAddVO.getPriority()) != null) {
            return ServiceResult.createByErrorMessage("已存在相同优先级节目");
        }
        Program program = new Program();
        BeanUtils.copyProperties(programAddVO, program);
        if (!programService.save(program)) {
            return ServiceResult.createByErrorMessage("创建失败");
        }
        return ServiceResult.createBySuccess(program);
    }

    @ApiOperation(value = "删除节目")
    @PostMapping("/delete/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Program> deleteById(@PathVariable String id) {
        Program program = programService.getById(id);
        if (program == null) {
            return ServiceResult.createByErrorMessage("节目不存在");
        }
        if (!programService.removeById(id)) {
            return ServiceResult.createByErrorMessage("删除失败");
        }
        return ServiceResult.createBySuccess(program);
    }

    @ApiOperation(value = "修改节目")
    @PostMapping("/update")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Program> updateById(@Validated @RequestBody ProgramUpdateVO programUpdateVO, BindingResult bindingResult) {
        //参数验证
        if (bindingResult.hasErrors()) {
            return ServiceResult.createByErrorMessage(String.valueOf(bindingResult.getFieldErrors()));
        }
        Program program = programService.getById(programUpdateVO.getId());
        if (program == null) {
            return ServiceResult.createByErrorMessage("节目不存在");
        }
        BeanUtils.copyProperties(programUpdateVO, program);
        if (!programService.updateById(program)) {
            return ServiceResult.createByErrorMessage("修改失败");
        }
        return ServiceResult.createBySuccess(program);
    }

    @ApiOperation(value = "查找节目列表")
    @PostMapping("/select")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Program> selectPage(@RequestBody @ApiParam(value = "ProgramPageVO") ProgramPageVO pageVO, BindingResult bindingResult) {
        //参数验证
        if (bindingResult.hasErrors()) {
            return ServiceResult.createByErrorMessage(String.valueOf(bindingResult.getFieldErrors()));
        }
        Page<Program> page = new Page<>(pageVO.getPageNo(), pageVO.getPageSize());
        Page<Program> returnPage = programService.findPage(page, pageVO);
        return ServiceResult.createBySuccess(returnPage.getList(), Math.toIntExact(returnPage.getCount()));
    }
}
