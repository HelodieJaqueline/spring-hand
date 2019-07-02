package com.zhangrui.spring.framework.core;

/**
 * @Author: YSTen
 * @Date: Created at 2019-07-02-16:51
 * @Description: 单例工厂顶层接口
 * @Modified: By
 */
public interface MyBeanFactory {

	/**
	 * get bean from IOC Container by name
	 * @param beanName
	 * @return
	 * @throws Exception
	 */
	Object getBean(String beanName) throws Exception;

	/**
	 * get bean from IOC Container by class
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	Object getBean(Class<?> clazz) throws Exception;
}
