package com.fzu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzu.entity.User;
import com.fzu.mapper.UserMapper;
import com.fzu.service.UserService;
import com.fzu.utils.Page;
import com.fzu.vo.UserPageVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getByOpenId(String openId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", openId);
        return this.getOne(wrapper);
    }

    @Override
    public Page<User> findPage(Page<User> page, UserPageVO pageVO) {
        QueryWrapper<User> wrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(pageVO.getName())) {
            wrapper.like("name", pageVO.getName());
        }
        if (StringUtils.isNotBlank(pageVO.getSid())) {
            wrapper.like("sid", pageVO.getSid());
        }
        List<User> list = this.list(wrapper);
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
