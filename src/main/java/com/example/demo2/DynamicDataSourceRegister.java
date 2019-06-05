package com.example.demo2;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源注册
 * @author cth
 * @date 2019-06-05
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    private Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);
    private DataSource defaultDataSource;
    /**
     * 用户自定义数据源
     */
    private Map<String, DataSource> slaveDataSources = new HashMap<>();

    @Override
    public void setEnvironment(Environment environment) {
        initDefaultDataSource(environment);
        initslaveDataSources(environment);
    }

    private void initDefaultDataSource(Environment env) {
        // 读取主数据源
        Map<String, Object> dsMap = new HashMap<>(5);
        dsMap.put("driver", env.getProperty("spring.primary.datasource.driver-class"));
        dsMap.put("url", env.getProperty("spring.primary.datasource.url"));
        dsMap.put("username", env.getProperty("spring.primary.datasource.username"));
        dsMap.put("password", env.getProperty("spring.primary.datasource.password"));
        defaultDataSource = buildDataSource(dsMap);
    }

    private void initslaveDataSources(Environment env) {
        // 读取配置文件获取更多数据源
        String slaveName = env.getProperty("slave.datasource.names");
        if (slaveName == null) {
            throw new RuntimeException("slave.datasource.names 不存在...");
        }
        for (String dsPrefix : slaveName.split(",")) {
            // 多个数据源
            Map<String, Object> dsMap = new HashMap<>();
            dsMap.put("driver", env.getProperty("spring." + dsPrefix + ".datasource.driver-class"));
            dsMap.put("url", env.getProperty("spring." + dsPrefix + ".datasource.url"));
            dsMap.put("username", env.getProperty("spring." + dsPrefix + ".datasource.username"));
            dsMap.put("password", env.getProperty("spring." + dsPrefix + ".datasource.password"));
            logger.info(JSON.toJSONString(dsMap));
            DataSource ds = buildDataSource(dsMap);
            slaveDataSources.put(dsPrefix, ds);
        }
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        //添加默认数据源
        targetDataSources.put("dataSource", this.defaultDataSource);
        DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
        //添加其他数据源
        targetDataSources.putAll(slaveDataSources);
        slaveDataSources.keySet().forEach(key -> DynamicDataSourceContextHolder.dataSourceIds.add(key));

        //创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        //注册 - BeanDefinitionRegistry
        beanDefinitionRegistry.registerBeanDefinition("dataSource", beanDefinition);
        logger.info("Dynamic DataSource Registry");
    }

    private DataSource buildDataSource(Map<String, Object> dataSourceMap) {
        // 默认使用Druid数据源
        Class<? extends DataSource> dataSourceType =  DruidDataSource.class;
        String driverClassName = dataSourceMap.get("driver").toString();
        String url = dataSourceMap.get("url").toString();
        String username = dataSourceMap.get("username").toString();
        String password = dataSourceMap.get("password").toString();
        // 自定义DataSource配置
        DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
                .username(username).password(password).type(dataSourceType);
        return factory.build();
    }

}

