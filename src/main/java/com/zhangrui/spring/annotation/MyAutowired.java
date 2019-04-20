package com.zhangrui.spring.annotation;

import java.lang.annotation.*;

/**
 * @Author: ZhangRui
 * @Date: Created at 2019-04-19-16:03
 * @Description:
 * @Modified: By
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAutowired {
	boolean required() default true;
}
