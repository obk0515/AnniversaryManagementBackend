package com.fzu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzu.entity.Alumnus;
import com.fzu.utils.Page;
import com.fzu.vo.AlumnusPageVO;

public interface AlumnusService extends IService<Alumnus> {
    Page<Alumnus> findPage(Page<Alumnus> page, AlumnusPageVO pageVO);

    Alumnus getByName(String name);
}
