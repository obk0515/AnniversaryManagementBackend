package com.fzu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzu.entity.Alumnus;
import com.fzu.mapper.AlumnusMapper;
import com.fzu.service.AlumnusService;
import com.fzu.utils.Page;
import com.fzu.vo.AlumnusPageVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlumnusServiceImpl extends ServiceImpl<AlumnusMapper, Alumnus> implements AlumnusService {
    @Override
    public Page<Alumnus> findPage(Page<Alumnus> page, AlumnusPageVO pageVO) {
        List<Alumnus> list = this.list();
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

    @Override
    public Alumnus getByName(String name) {
        QueryWrapper<Alumnus> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return this.getOne(wrapper);
    }
}
