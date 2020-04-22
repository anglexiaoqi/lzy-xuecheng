package com.xuecheng.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuecheng.domain.SysConfig;
import com.xuecheng.web.ProviderApplication;
import com.xuecheng.web.service.SysConfigService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author lzy
 * @version 1.0
 * @date 2020/4/20 15:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= ProviderApplication.class)
//@ContextConfiguration(classes = ProviderApplication.class)
public class DemoTest {

    @Autowired
    private SysConfigService sysConfigService;
    @Test
    public void test1() {
        PageHelper.startPage(1,2);
        List<SysConfig> list = sysConfigService.queryAll();
        PageInfo pageInfo = new PageInfo(list);
        //iter 快捷键
        System.out.println(pageInfo);
        //ExceptionCast.cast(CommonCode.SERVER_ERROR);
    }
}
