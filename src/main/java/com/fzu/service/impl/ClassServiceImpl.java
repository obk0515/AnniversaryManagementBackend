package com.fzu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzu.entity.Class;
import com.fzu.mapper.ClassMapper;
import com.fzu.service.ClassService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {

    @Override
    public String getClassId(JSONObject jsonObject) {
        Map<String, Object> map = new HashMap();
        map.put("academy", (String.valueOf(jsonObject.get("academy"))));
        map.put("grade", (String.valueOf(jsonObject.get("grade"))));
        map.put("major", (String.valueOf(jsonObject.get("major"))));
        map.put("class_no", (jsonObject.get("class_no")));
        QueryWrapper<Class> wrapper = new QueryWrapper<>();
        wrapper.allEq(map);
        if (this.getOne(wrapper) == null) {
            return null;
        }
        return this.getOne(wrapper).getCid();
    }
}
