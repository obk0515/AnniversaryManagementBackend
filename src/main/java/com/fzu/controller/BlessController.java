package com.fzu.controller;

import com.fzu.entity.Bless;
import com.fzu.result.ServiceResult;
import com.fzu.service.BlessService;
import com.fzu.utils.Page;
import com.fzu.vo.BlessPageVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bless")
@Api(value = "BlessController", tags = "祝福审核模块")
public class BlessController {
    @Autowired
    private BlessService blessService;

    @ApiOperation(value = "删除祝福")
    @PostMapping("/delete/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Bless> deleteById(@PathVariable String id) {
        Bless bless = blessService.getById(id);
        if (bless == null) {
            return ServiceResult.createByErrorMessage("祝福不存在");
        }
        if (!blessService.removeById(id)) {
            return ServiceResult.createByErrorMessage("删除失败");
        }
        return ServiceResult.createBySuccess(bless);
    }

    @ApiOperation(value = "修改审核状态")
    @PostMapping("/update/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Bless> updateById(@PathVariable String id) {
        Bless bless = blessService.getById(id);
        if (bless == null) {
            return ServiceResult.createByErrorMessage("祝福不存在");
        }
        bless.setStatus("1");
        if (!blessService.updateById(bless)) {
            return ServiceResult.createByErrorMessage("修改失败");
        }
        return ServiceResult.createBySuccess(bless);
    }

    @ApiOperation(value = "查找祝福")
    @GetMapping("/select/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Bless> selectById(@PathVariable String id) {
        if (blessService.getById(id) == null) {
            return ServiceResult.createByErrorMessage("祝福不存在");
        }
        return ServiceResult.createBySuccess(blessService.getById(id));
    }

    @ApiOperation(value = "查找用户列表")
    @PostMapping("/select")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<Bless> selectPage(@RequestBody @ApiParam(value = "BlessPageVO") BlessPageVO pageVO, BindingResult bindingResult) {
        //参数验证
        if (bindingResult.hasErrors()) {
            return ServiceResult.createByErrorMessage(String.valueOf(bindingResult.getFieldErrors()));
        }
        Page<Bless> page = new Page<>(pageVO.getPageNo(), pageVO.getPageSize());
        Page<Bless> returnPage = blessService.findPage(page, pageVO);
        return ServiceResult.createBySuccess(returnPage.getList(), Math.toIntExact(returnPage.getCount()));
    }
}
