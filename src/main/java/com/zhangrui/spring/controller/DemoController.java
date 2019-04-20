package com.zhangrui.spring.controller;

import com.zhangrui.spring.annotation.MyAutowired;
import com.zhangrui.spring.annotation.MyController;
import com.zhangrui.spring.annotation.MyRequestMapping;
import com.zhangrui.spring.annotation.MyRequestParam;
import com.zhangrui.spring.service.DemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: ZhangRui
 * @Date: Created at 2019-04-19-16:21
 * @Description:
 * @Modified: By
 */
@MyController
@MyRequestMapping(name = "/demo")
public class DemoController {

	@MyAutowired
	private DemoService demoService;

	@MyRequestMapping(name = "/query")
	public void query(HttpServletRequest request, HttpServletResponse response,@MyRequestParam(name = "name") String name) {
		String resp = "The name is" + name;
		try {
			response.getWriter().write(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
