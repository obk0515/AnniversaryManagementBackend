package com.fzu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzu.entity.Program;
import com.fzu.utils.Page;
import com.fzu.vo.ProgramPageVO;

public interface ProgramService extends IService<Program> {
    Program getByPriority(Integer priority);

    Page<Program> findPage(Page<Program> page, ProgramPageVO pageVO);
}
