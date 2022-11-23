package com.fzu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzu.entity.Achievement;
import com.fzu.mapper.AchievementMapper;
import com.fzu.service.AchievementService;
import com.fzu.utils.Page;
import com.fzu.vo.AchievementPageVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AchievementServiceImpl extends ServiceImpl<AchievementMapper, Achievement> implements AchievementService {
    @Override
    public Achievement getByTitle(String title) {
        return this.getOne(new QueryWrapper<Achievement>().eq("title", title));
    }

    @Override
    public Page<Achievement> findPage(Page<Achievement> page, AchievementPageVO pageVO) {
        QueryWrapper<Achievement> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(pageVO.getType()) && !Objects.equals(pageVO.getType(), "")) {
            wrapper.eq("type", pageVO.getType());
        }
        List<Achievement> list = this.list(wrapper);
        //设置总数
        page.setCount(list.size());
        // 每页条数
        page.setPageSize(pageVO.getPageSize());
        // 第几页
        long firstResult = (long) (page.getPageNo() - 1) * page.getPageSize();
        if (firstResult >= page.getCount()) {
            return page;
        }
        page.setPageNo(page.getFirstResult());
        list = list.stream().skip(firstResult).limit(pageVO.getPageSize()).collect(Collectors.toList());
        page.setList(list);
        return page;
    }
}
