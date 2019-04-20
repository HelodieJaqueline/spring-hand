package com.zhangrui.spring.v1;

import com.zhangrui.spring.annotation.MyAutowired;
import com.zhangrui.spring.annotation.MyController;
import com.zhangrui.spring.annotation.MyRequestMapping;
import com.zhangrui.spring.annotation.MyService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @Author: ZhangRui
 * @Date: Created at 2019-04-19-16:09
 * @Description:
 * @Modified: By
 */
public class MyDispatcherServlet extends HttpServlet {
	private static final String SEPARATOR = "/";

	private static final String CLASS = ".class";
	private static final String SPOT = ".";

	/**
	 * 保存配置文件
	 */
	private Properties contextConfig = new Properties();
	/**
	 * 保存扫描的所有的类名
	 */
	private List<String> classNames = new ArrayList<String>();

	/**
	 * IOC容器
	 */
	private Map<String, Object> ioc = new HashMap<String, Object>();

	/**
	 * 保存url和Method的对应关系
	 */
	private Map<String,Method> handlerMapping = new HashMap<String,Method>();

	/**
	 * 初始化
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config)
		throws ServletException {
		//加载配置
		doLoadConfig(config);
		//扫描包路径下的类
		doScanner(contextConfig.getProperty("scanPackage"));
		//初始化扫描到的类，加入到IOC容器
		doInStanceAndPutIoc();
		//依赖注入
		doDependencyInjection();
		//初始化HandleMapping
		doInitHandleMapping();
	}

	/**
	 * 初始化HandleMapping
	 */
	private void doInitHandleMapping() {
		if (MapUtils.isEmpty(ioc)) {
			return;
		}
		for (Map.Entry<String, Object> entry : ioc.entrySet()) {
			Class<?> clazz = entry.getValue().getClass();
			if (!clazz.isAnnotationPresent(MyController.class)) {
				continue;
			}
			String baseUrl = "";
			if (clazz.isAnnotationPresent(MyRequestMapping.class)) {
				MyRequestMapping requestMapping = clazz.getAnnotation(MyRequestMapping.class);
				baseUrl = requestMapping.value();
			}
			for (Method method : clazz.getDeclaredMethods()) {
				if (!method.isAnnotationPresent(MyRequestMapping.class)) {
					continue;
				}
				MyRequestMapping requestMapping = method.getAnnotation(MyRequestMapping.class);
				//"/"有重复的可能
				String url = (SEPARATOR + baseUrl + requestMapping.value()).replaceAll("/+", SEPARATOR);
				handlerMapping.put(url, method);
				System.out.println(" Mapped " + url + " …… ");
			}
		}
	}

	/**
	 * 依赖注入
	 */
	private void doDependencyInjection() {
		if (MapUtils.isEmpty(ioc)) {
			return;
		}
		for (Map.Entry<String, Object> entry : ioc.entrySet()) {
			//Declared 所有的，特定的 字段，包括private/protected/default
			Field[] fields = entry.getValue().getClass().getDeclaredFields();
			for (Field field : fields) {
				if (!field.isAnnotationPresent(MyAutowired.class)) {
					continue;
				}
				MyAutowired autowired = field.getAnnotation(MyAutowired.class);
				String beanName = autowired.value();
				if (StringUtils.EMPTY.equals(beanName)) {
					beanName = field.getType().getName();
				}
				//暴力访问
				field.setAccessible(true);
				try {
					field.set(entry.getValue(), ioc.get(beanName));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 初始化IOC容器
	 */
	private void doInStanceAndPutIoc() {
		if (CollectionUtils.isEmpty(classNames)) {
			return;
		}
		try {
			for (String calss : classNames) {
				Class<?> clazz = Class.forName(calss);
				//初始化加了注解的类
				if (clazz.isAnnotationPresent(MyController.class)) {
					Object bean = clazz.newInstance();
					String beanName = toLowerFirstCase(clazz.getSimpleName());
					ioc.put(beanName, bean);
					System.out.println(beanName + " Instanced …… ");
				} else if (clazz.isAnnotationPresent(MyService.class)) {
					MyService service = clazz.getAnnotation(MyService.class);
					String beanName = service.value();
					if (StringUtils.EMPTY.equals(beanName)) {
						beanName = toLowerFirstCase(clazz.getSimpleName());
					}
					Object bean = clazz.newInstance();
					ioc.put(beanName, bean);
					System.out.println(beanName + " Instanced …… ");
					for (Class<?> i : clazz.getInterfaces()) {
						if (ioc.containsKey(i.getName())) {
							throw new Exception("The “" + i.getName() + "” is exists!!");
						}
						ioc.put(beanName, bean);
						System.out.println(beanName + " Instanced …… ");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 首字母小写
	 * @param simpleName
	 * @return
	 */
	private String toLowerFirstCase(String simpleName) {
		char[] chars = simpleName.toCharArray();
		if (65 <= chars[0] && chars[0] <= 90) {
			chars[0] += 32;
		}
		return String.valueOf(chars);
	}

	/**
	 * 扫描包下的类
	 * @param scanPackage
	 */
	private void doScanner(String scanPackage) {
		URL url = getClass().getClassLoader().getResource(SEPARATOR + scanPackage.replaceAll("\\.", "/"));
		if (null == url) {
			return;
		}
		File path = new File(url.getFile());
		for (File file : path.listFiles()) {
			if (file.isDirectory()) {
				doScanner(scanPackage + SPOT + file.getName());
			}else {
				if (!file.getName().endsWith(CLASS)) {
					continue;
				}
				String className = scanPackage + SPOT + file.getName().replace(CLASS, StringUtils.EMPTY);
				classNames.add(className);
				System.out.println("scanned :" + className);
			}
		}
	}

	/**
	 * 加载配置项
	 * @param config
	 */
	private void doLoadConfig(ServletConfig config) {
		String path = config.getInitParameter("contextConfigLocation");
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
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
		try {
			doDispatch(req, resp);
		} catch (Exception e) {
			resp.getWriter().write("500 Error :" + Arrays.toString(e.getStackTrace()));
			e.printStackTrace();
		}
	}

	private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
		String url = req.getRequestURI();
		//相对路径
		String contextPath = req.getContextPath();
		url = url.replaceAll(contextPath, "").replaceAll("/+", "/");
		if (!handlerMapping.containsKey(url)) {
			resp.getWriter().write("404 Not Found!");
		}
		Method method = handlerMapping.get(url);
		if (null == method) {
			return;
		}
		String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
		Map<String, String[]> params = req.getParameterMap();
		method.invoke(ioc.get(beanName), new Object[]{req, resp, params.get("name")[0]});
	}
}
