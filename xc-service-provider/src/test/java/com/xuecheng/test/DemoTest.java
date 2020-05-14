package com.xuecheng.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuecheng.domain.SysConfig;
import com.xuecheng.framework.utils.RedisCache;
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
    @Test
    public void test08() {
        /*JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1024);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMaxWaitMillis(100);
        jedisPoolConfig.setTestOnBorrow(false);//jedis 第一次启动时，会报错
        jedisPoolConfig.setTestOnReturn(true);
        // 初始化JedisPool
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "192.168.5.32", 6379, 6000);
        //
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis.get("1006"));
        System.out.println(jedis.get("1006"));
        System.out.println(jedis.get("1006"));
        System.out.println(jedis.get("1006"));
        System.out.println(jedis.get("1006"));
        System.out.println(jedis.get("1006"));
        System.out.println(jedis.get("1006"));
        System.out.println(jedis.get("1006"));
        System.out.println(jedis.get("1006"));
        System.out.println(jedis.get("1006"));
        System.out.println(jedis.get("1006"));*/

        System.out.println("========"+ RedisCache.getRu().get("1006"));
        System.out.println("========"+RedisCache.getRu().get("1006"));
        System.out.println("========"+RedisCache.getRu().get("1006"));
        System.out.println("========"+RedisCache.getRu().get("1006"));
        System.out.println("========"+RedisCache.getRu().get("1006"));
        System.out.println("========"+RedisCache.getRu().get("1006"));
        System.out.println("========"+RedisCache.getRu().get("1006"));
        System.out.println("========"+RedisCache.getRu().get("1006"));
        System.out.println("========"+RedisCache.getRu().get("1006"));
        System.out.println("========"+RedisCache.getRu().get("1006"));
        System.out.println("========"+RedisCache.getRu().get("1006"));
    }
}
