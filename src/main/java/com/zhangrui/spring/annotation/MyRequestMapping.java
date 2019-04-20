package com.zhangrui.spring.annotation;

import java.lang.annotation.*;

/**
 * @Author: ZhangRui
 * @Date: Created at 2019-04-19-15:57
 * @Description:
 * @Modified: By
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestMapping {
	String value() default "";
}
