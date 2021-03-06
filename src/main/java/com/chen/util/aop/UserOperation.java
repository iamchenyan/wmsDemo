package com.chen.util.aop;

import java.lang.annotation.*;

/**
 *  用户操作注解
 *  用于标注用户操作的方法名称
 * @author chenyan
 *  2019年3月20日
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UserOperation {
    String value();
}
