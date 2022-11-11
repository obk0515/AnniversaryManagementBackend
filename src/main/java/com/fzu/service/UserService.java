package com.fzu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzu.entity.User;
import com.fzu.utils.Page;
import com.fzu.vo.UserPageVO;

public interface UserService extends IService<User> {
    User getByOpenId(String openId);

    Page<User> findPage(Page<User> page, UserPageVO pageVO);
}
