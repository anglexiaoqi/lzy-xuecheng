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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author lzy
 * @version 1.0
 * @date 2020/4/23 14:08
 */
@Configuration
@MapperScan(basePackages = "com.xuecheng.web.dao.master", sqlSessionTemplateRef = "masterSqlSessionTemplate")
public class MybatisDbAConfig {

    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    @Primary
    public DataSource setDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "masterTransactionManager")
    @Primary
    public DataSourceTransactionManager setTransactionManager(@Qualifier("masterDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/master/*.xml"));
        bean.setTypeAliasesPackage("com.xuecheng.domain");
        return bean.getObject();
    }

    @Bean(name = "masterSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
