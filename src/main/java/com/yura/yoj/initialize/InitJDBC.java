package com.yura.yoj.initialize;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

public class InitJDBC implements ServletContextListener{

	public InitJDBC() {
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
//		DataSource dataSource = sce.
		//보류
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
