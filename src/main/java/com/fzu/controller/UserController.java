package com.fzu.controller;

import com.alibaba.fastjson.JSON;
import com.fzu.entity.User;
import com.fzu.result.ServiceResult;
import com.fzu.service.ClassService;
import com.fzu.service.UserService;
import com.fzu.utils.BeanUtil;
import com.fzu.utils.Page;
import com.fzu.vo.UserAddVO;
import com.fzu.vo.UserPageVO;
import com.fzu.vo.UserUpdateVO;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(value = "UserController", tags = "用户管理模块")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ClassService classService;

    @ApiOperation(value = "创建用户")
    @PostMapping("/add")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<User> addUser(@Validated @RequestBody UserAddVO userAddVO, BindingResult bindingResult) {
        //参数验证
        if (bindingResult.hasErrors()) {
            return ServiceResult.createByErrorMessage(String.valueOf(bindingResult.getFieldErrors()));
        }
        if (userService.getByOpenId(userAddVO.getOpenId()) != null) {
            return ServiceResult.createByErrorMessage("已存在微信绑定用户");
        }
        User user = new User();
        BeanUtils.copyProperties(userAddVO, user);
        user.setBanFlag("0");
        if (!userService.save(user)) {
            return ServiceResult.createByErrorMessage("创建失败");
        }
        return ServiceResult.createBySuccess(user);
    }

    @ApiOperation(value = "删除用户")
    @PostMapping("/delete/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<User> deleteById(@PathVariable String id) {
        User user = userService.getById(id);
        if (user == null) {
            return ServiceResult.createByErrorMessage("用户不存在");
        }
        if (!userService.removeById(id)) {
            return ServiceResult.createByErrorMessage("删除失败");
        }
        return ServiceResult.createBySuccess(user);
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping("/update")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<User> updateUserById(@Validated @RequestBody UserUpdateVO userUpdateVO, BindingResult bindingResult) {
        //参数验证
        if (bindingResult.hasErrors()) {
            return ServiceResult.createByErrorMessage(String.valueOf(bindingResult.getFieldErrors()));
        }
        if (userService.getById(userUpdateVO.getId()) == null) {
            return ServiceResult.createByErrorMessage("用户不存在");
        }
        User user = userService.getById(userUpdateVO.getId());
        BeanUtils.copyProperties(userUpdateVO, user, BeanUtil.getNullPropertyNames(userUpdateVO));
        if (!userService.updateById(user)) {
            return ServiceResult.createByErrorMessage("修改失败");
        }
        return ServiceResult.createBySuccess(user);
    }

    @ApiOperation(value = "查找用户")
    @GetMapping("/select/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<User> selectById(@PathVariable String id) {
        if (userService.getById(id) == null) {
            return ServiceResult.createByErrorMessage("用户不存在");
        }
        return ServiceResult.createBySuccess(userService.getById(id));
    }

    @ApiOperation(value = "查找用户列表")
    @PostMapping("/select")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "访问token", paramType = "header", dataType = "string", required = true)
    })
    public ServiceResult<User> selectPage(@RequestBody @ApiParam(value = "UserPageVO") UserPageVO pageVO, BindingResult bindingResult) {
        //参数验证
        if (bindingResult.hasErrors()) {
            return ServiceResult.createByErrorMessage(String.valueOf(bindingResult.getFieldErrors()));
        }
        Page<User> page = new Page<>(pageVO.getPageNo(), pageVO.getPageSize());
        Page<User> returnPage = userService.findPage(page, pageVO);
        return ServiceResult.createBySuccess(returnPage.getList(), Math.toIntExact(returnPage.getCount()));
    }
}
