package com.xuecheng.web.service.impl;

import com.xuecheng.domain.SysConfig;
import com.xuecheng.web.dao.SysConfigMapper;
import com.xuecheng.web.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzy
 * @version 1.0
 * @date 2020/4/20 17:07
 */
@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public List<SysConfig> queryAll() {
        return sysConfigMapper.selectList(null);
    }
}
