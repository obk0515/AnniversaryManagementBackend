package com.fzu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzu.entity.Visit;
import com.fzu.utils.Page;
import com.fzu.vo.VisitPageVO;

public interface VisitService extends IService<Visit> {
    Page<Visit> findPage(Page<Visit> page, VisitPageVO pageVO);

    Visit getByName(String name);
}
