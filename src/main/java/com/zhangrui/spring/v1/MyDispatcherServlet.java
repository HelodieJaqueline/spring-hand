package com.zhangrui.spring.v1;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: ZhangRui
 * @Date: Created at 2019-04-19-16:09
 * @Description:
 * @Modified: By
 */
public class MyDispatcherServlet extends HttpServlet {
	private Properties contextConfig = new Properties();

	private Map<String, Object> ioc = new HashMap<String, Object>();

	@Override
	public void init(ServletConfig config)
		throws ServletException {
		//加载配置
		doLoadConfig(config);
		//扫描包路径下的类
		doScanner();
		//初始化扫描到的类，加入到IOC容器
		doInStanceAndPutIoc();
		//依赖注入
		doDependencyInjection();
		//初始化HandleMapping
		doInitHandleMapping();
	}

	private void doInitHandleMapping() {

	}

	private void doDependencyInjection() {

	}

	private void doInStanceAndPutIoc() {

	}

	private void doScanner() {
	}

	private void doLoadConfig(ServletConfig config) {
		String path = config.getInitParameter("contextConfigLocation");
		InputStream is = this.getClass().getResourceAsStream(path);
		try {
			contextConfig.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (is != null) {
				try {
					is.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		doDispatch(req, resp);
	}

	private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {

	}
}
