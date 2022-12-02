package com.fzu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzu.entity.Visit;
import com.fzu.mapper.VisitMapper;
import com.fzu.service.VisitService;
import com.fzu.utils.Page;
import com.fzu.vo.VisitPageVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitServiceImpl extends ServiceImpl<VisitMapper, Visit> implements VisitService {
    @Override
    public Visit getByName(String name) {
        QueryWrapper<Visit> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return this.getOne(wrapper);
    }

    @Override
    public Page<Visit> findPage(Page<Visit> page, VisitPageVO pageVO) {
        List<Visit> list = this.list();
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
