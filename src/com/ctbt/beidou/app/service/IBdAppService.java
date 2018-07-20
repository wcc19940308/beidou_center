package com.ctbt.beidou.app.service;

import java.util.List;
import java.util.Map;

public interface IBdAppService {

    //查询最新消息
    List<Map<String, Object>> findAll(Map<String, Object> params);
    
}
