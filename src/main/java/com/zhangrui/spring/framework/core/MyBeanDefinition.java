package com.zhangrui.spring.framework.core;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangRui
 * @Date: Created at 2019-07-02-17:18
 * @Description: 存储配置文件信息
 * @Modified: By
 */
@Data
@NoArgsConstructor
public class MyBeanDefinition {
	private String beanClassName;

	private boolean lazyInit;

	private String factoryBeanName;
}
