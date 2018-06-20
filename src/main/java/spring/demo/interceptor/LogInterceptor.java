/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package spring.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import spring.demo.utils.LogUtils;

/**
 * 日志拦截器
 * @author 
 * @version 
 */
public class LogInterceptor  implements HandlerInterceptor {

	protected Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
	private static final ThreadLocal<Long> startTimeThreadLocal =
			new NamedThreadLocal<Long>("ThreadLocal StartTime");

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		    if(request.getRequestURI().startsWith("/static/")){ //静态资源不保存日志
		    	return;
		    } 
		    if(logger.isInfoEnabled()){
				Long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）  
				long now = System.currentTimeMillis(); 	//2、结束时间  
				//系统时间 uri 耗时 请求参数 返回结果
				Object[] args = new Object[5];
				args[0] = now;
				args[1] = request.getRequestURI();
				args[2] = now - beginTime;
				args[3] = request.getParameterMap();
		        logger.info("{} {} {} {}",args);
			}
		    // 保存日志
			LogUtils.saveLog(request, handler, ex, null);
			
			
		
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
		//if (modelAndView != null){
		//	logger.info("ViewName: " + modelAndView.getViewName());
		//}
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		if (logger.isInfoEnabled()){
			long beginTime = System.currentTimeMillis();//1、开始时间  
	        startTimeThreadLocal.set(beginTime);		//线程绑定变量（该数据只有当前请求的线程可见）  
		}
		return true;
	}
	
	
	

}
