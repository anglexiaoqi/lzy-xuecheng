package com.xuecheng.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuecheng.domain.SysConfig;
import com.xuecheng.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.web.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lzy
 * @version 1.0
 * @date 2020/4/20 15:05
 */

@RestController
public class DemoController {

    @Autowired
    private SysConfigService sysConfigService;

    @RequestMapping("/demo")
    public ResponseResult demo(){
        PageHelper.startPage(1,2);
        List<SysConfig> list = sysConfigService.queryAll();
        PageInfo pageInfo = new PageInfo(list);
        //iter 快捷键
        System.out.println(pageInfo);
        ExceptionCast.cast(CmsCode.CMS_COURSE_PERVIEWISNULL);
        return null;
    }

}
