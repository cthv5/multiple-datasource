package com.example.demo2;


import java.lang.annotation.*;

/**
 * 在方法上使用，用于指定使用哪个数据源
 * @author cth
 * @date 2019-06-05
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String name();
}