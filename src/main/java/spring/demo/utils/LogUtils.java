/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package spring.demo.utils;

import java.awt.Menu;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.method.HandlerMethod;


/**
 * 字典工具类
 * @author ThinkGem
 * @version 2014-11-7
 */
public class LogUtils {
	
	public static final String CACHE_MENU_NAME_PATH_MAP = "menuNamePathMap";
	
	//private static SysLogDao logDao = SpringContextHolder.getBean(SysLogDao.class);
	//private static SysMenuDao menuDao = SpringContextHolder.getBean(SysMenuDao.class);
	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request, String title){
		saveLog(request, null, null, title);
	}
	
	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title){
		Object log = new Object();
		/*SysUserBean user = UserUtils.getUser();
		if (user != null && user.getUserId()!= null){
			log.setIp(request.getRemoteAddr());
			log.setLogId(SeqUUIDUtil.toSequenceUUID());
			log.setName(title);
			log.setCreateUserId(user.getUserId());
			log.setUri(request.getRequestURI());
			log.setCreateTime(System.currentTimeMillis());*/
			new SaveLogThread(log, handler, ex).start();
		}
	

	/**
	 * 保存日志线程
	 */
	public static class SaveLogThread extends Thread{
		
		private Object log;
		private Object handler;
		private Exception ex;
		
		public SaveLogThread(Object log, Object handler, Exception ex){
			super(SaveLogThread.class.getSimpleName());
			this.log =log;
			this.handler =handler;
			this.ex =ex;
		}
		
		@Override
		public void run() {
			/*// 获取日志标题
			if (StringUtil.isEmpty(log.getName())){
				String permission = "";
				if (handler instanceof HandlerMethod){
					Method m = ((HandlerMethod)handler).getMethod();
					RequiresPermissions rp = m.getAnnotation(RequiresPermissions.class);
					permission = (rp != null ? StringUtils.join(rp.value(), ",") : "");
				}
				log.setName(getMenuNamePath(log.getUri(), permission));
			}
			// 如果有异常，设置异常信息
			log.setDescribe(StringUtils.getStackTraceAsString(ex));
			// 如果无标题并
			if (StringUtils.isBlank(log.getName()) && StringUtils.isBlank(log.getDescribe())){
				return;
			}
			// 保存日志信息
			//log.preInsert();
			logDao.insertOne(log);*/
		}
	}

	
}
