package com.fzu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzu.entity.Bless;
import com.fzu.mapper.BlessMapper;
import com.fzu.service.BlessService;
import com.fzu.utils.Page;
import com.fzu.vo.BlessPageVO;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlessServiceImpl extends ServiceImpl<BlessMapper, Bless> implements BlessService {

    @Override
    public Page<Bless> findPage(Page<Bless> page, BlessPageVO pageVO) {
        QueryWrapper<Bless> wrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(pageVO.getCreateBy())) {
            wrapper.like("create_by", pageVO.getCreateBy());
        }
        if (StringUtils.isNotBlank(pageVO.getBless())) {
            wrapper.like("bless", pageVO.getBless());
        }
        if (StringUtils.isNotBlank(pageVO.getStatus())) {
            wrapper.eq("status", pageVO.getStatus());
        }
        if (StringUtils.isNotBlank(pageVO.getDate())) {
            String date = pageVO.getDate();
            String date1 = date.substring(0, 10) + " 00:00:00";
            String date2 = date.substring(13) + " 23:59:59";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date newDate1 = format.parse(date1);
                Date newDate2 = format.parse(date2);
                wrapper.between("create_time", newDate1, newDate2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        List<Bless> list = this.list(wrapper);
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
