package com.fzu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fzu.entity.Admin;
import com.fzu.mapper.AdminMapper;
import com.fzu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("loginname", loginName);
        Admin admin = adminMapper.selectOne(wrapper);
        return admin;
    }
}
