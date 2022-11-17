package com.fzu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzu.entity.Achievement;
import com.fzu.utils.Page;
import com.fzu.vo.AchievementPageVO;

public interface AchievementService extends IService<Achievement> {
    Achievement getByTitle(String title);

    Page<Achievement> findPage(Page<Achievement> page, AchievementPageVO pageVO);

}
