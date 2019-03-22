package cn.forever.listerner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.forever.dao.ObjectDao;
import cn.forever.utils.CommonFunction;
import cn.forever.utils.SpringBeanManager;

public class MyServletContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * application在服务器启动后就放入
	 */
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		CommonFunction.setApplication(application);
		//CommonFunction.flushApplication((ObjectDao)SpringBeanManager.getBean("objectDao"));
	}

}
