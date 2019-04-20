package com.zhangrui.spring.annotation;

import java.lang.annotation.*;

/**
 * @Author: ZhangRui
 * @Date: Created at 2019-04-19-16:18
 * @Description:
 * @Modified: By
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestParam {
	String value() default "";

	String name() default "";

	boolean required() default true;
}
