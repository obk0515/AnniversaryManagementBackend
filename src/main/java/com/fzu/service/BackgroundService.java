package com.fzu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzu.entity.Background;
import com.fzu.entity.User;
import com.fzu.utils.Page;
import com.fzu.vo.BackgroundPageVO;

public interface BackgroundService extends IService<Background> {
    Page<Background> findPage(Page<Background> page, BackgroundPageVO pageVO);

    Background getByName(String name);
}
