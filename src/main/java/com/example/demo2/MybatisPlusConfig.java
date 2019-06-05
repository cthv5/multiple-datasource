package com.example.demo2;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;

@Configuration
@EnableTransactionManagement
@MapperScan("com.example.demo2.**.mapper")
public class MybatisPlusConfig {
    /**
     * 指定插入空值时的类型映射,oracle默认是Other
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return (configuration) -> {
            configuration.setJdbcTypeForNull(JdbcType.NULL);
        };
    }

	@Bean
	public OracleKeyGenerator oracleKeyGenerator() {
		return new OracleKeyGenerator();
	}

    /**
     * mybatis-plus自动填充字段
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
            }
            @Override
            public void updateFill(MetaObject metaObject) {
                this.setFieldValByName("dataUpdatedate", new Date(), metaObject);
            }
        };
    }
}
