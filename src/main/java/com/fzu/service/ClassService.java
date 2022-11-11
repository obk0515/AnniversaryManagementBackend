package com.fzu.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzu.entity.Class;

public interface ClassService extends IService<Class> {
    String getClassId(JSONObject jsonObject);
}
