package com.zhangrui.spring.framework.core;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangRui
 * @Date: Created at 2019-07-02-17:29
 * @Description:
 * @Modified: By
 */
@Data
@NoArgsConstructor
public class MyBeanWrapper {

	private Object wrappedInstance;

	private Class<?> wrappedClass;

}
