package com.xuecheng.web.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author lzy
 * @version 1.0
 * @date 2020/4/23 14:08
 */
@Configuration
@MapperScan(basePackages = "com.xuecheng.web.dao.second", sqlSessionTemplateRef = "secondSessionTemplate")
public class MybatisDbBConfig {

    @Bean(name = "secondDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSource setDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "secondTransactionManager")
    public DataSourceTransactionManager setTransactionManager(@Qualifier("secondDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("secondDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/second/*.xml"));
        bean.setTypeAliasesPackage("com.xuecheng.domain");
        return bean.getObject();
    }

    @Bean(name = "secondSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("secondSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
