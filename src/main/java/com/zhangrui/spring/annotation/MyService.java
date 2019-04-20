package com.zhangrui.spring.annotation;

import java.lang.annotation.*;

/**
 * @Author: ZhangRui
 * @Date: Created at 2019-04-19-16:16
 * @Description:
 * @Modified: By
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyService {
	String value() default "";
}
