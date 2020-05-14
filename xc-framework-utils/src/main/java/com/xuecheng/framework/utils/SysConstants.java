package com.xuecheng.framework.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SysConstants {

    @Value("${spring.redis.initaomunt.host}")
    protected String host;

    @Value("${spring.redis.initaomunt.port}")
    protected String port;

    @Value("${spring.redis.initaomunt.database}")
    protected String database;

    @Value("${spring.redis.pool.initaomunt.max-active}")
    protected String maxactive;

    @Value("${spring.redis.pool.initaomunt.max-idle}")
    protected String maxidle;

    @Value("${spring.redis.initaomunt.timeout}")
    protected String timeout;

    public static String REDIS_HOST;
    public static int REDIS_PORT;
    public static int REDIS_DATABASE;
    public static int REDIS_POOL_MAXACTIVE;
    public static int REDIS_POOT_MAXIDLE;
    public static int REDIS_TIMEOUT;

    @PostConstruct
    private void init() {
        SysConstants.REDIS_HOST = host;
        SysConstants.REDIS_PORT = Integer.parseInt(port);
        SysConstants.REDIS_DATABASE = Integer.parseInt(database);
        SysConstants.REDIS_POOL_MAXACTIVE = Integer.parseInt(maxactive);
        SysConstants.REDIS_POOT_MAXIDLE = Integer.parseInt(maxidle);
        SysConstants.REDIS_TIMEOUT = Integer.parseInt(timeout);
    }

}
