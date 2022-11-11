package com.fzu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzu.entity.Bless;
import com.fzu.utils.Page;
import com.fzu.vo.BlessPageVO;

public interface BlessService extends IService<Bless> {
    Page<Bless> findPage(Page<Bless> page, BlessPageVO pageVO);
}
