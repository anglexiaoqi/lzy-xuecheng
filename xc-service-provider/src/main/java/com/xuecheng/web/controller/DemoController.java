package com.xuecheng.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.util.concurrent.RateLimiter;
import com.xuecheng.domain.SysConfig;
import com.xuecheng.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.web.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lzy
 * @version 1.0
 * @date 2020/4/20 15:05
 */

@RestController
@Slf4j
public class DemoController {

    @Autowired
    private SysConfigService sysConfigService;

    @RequestMapping("/demo")
    public ResponseResult demo() {
        PageHelper.startPage(1, 2);
        List<SysConfig> list = sysConfigService.queryAll();
        PageInfo pageInfo = new PageInfo(list);
        //iter 快捷键
        System.out.println(pageInfo);
        ExceptionCast.cast(CmsCode.CMS_COURSE_PERVIEWISNULL);
        return null;
    }

    /**
     * 限流 demo!!!!!
     * Created by lzy on 20/04/23.
     * 有很多个任务，但希望每秒不超过X个，可用此类
     */
    public static void main(String[] args) {
        //创建一个限流器，参数代表每秒生成的令牌数，通过
        RateLimiter rateLimiter = RateLimiter.create(1000);
        List<Runnable> tasks = new ArrayList<Runnable>();
        for (int i = 0; i < 100; i++) {
            tasks.add(new UserRequest(i));
        }
        ExecutorService threadPool = Executors.newCachedThreadPool();
        //阻塞方式访问
        /* for (Runnable runnable : tasks) {
            //来以阻塞的方式获取令牌
            System.out.println("等待时间：" + rateLimiter.acquire());
            threadPool.execute(runnable);
        }*/

        //非阻塞方式
        //tryAcquire(long timeout, TimeUnit unit)：判断是否可以在指定的时间内从ratelimiter获得一个许可，或者在超时期间内未获得许可的话，立即返回false。
        //tryAcquire(int permits)：判断是否可以立即获取相应数量的许可。
        //tryAcquire()：判断是否可以立即获取许可。
        //tryAcquire(int permits, long timeout, TimeUnit unit)：判断是否可以在超时时间内获取相应数量的许可。
        for (Runnable runnable : tasks) {
            //未请求到limiter则立即返回false
            if (!rateLimiter.tryAcquire()) {
                System.out.println("请求过多,稍后重试.");
            } else {
                threadPool.execute(runnable);
            }
        }

    }

    private static class UserRequest implements Runnable {
        private int id;

        public UserRequest(int id) {
            this.id = id;
        }

        public void run() {
            System.out.println(id);
        }
    }

}
