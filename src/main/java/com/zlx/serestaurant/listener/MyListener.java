package com.zlx.serestaurant.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author zlx
 * @create 2021-10-26 6:16 下午
 */
@WebListener
public class MyListener implements ServletContextListener {
//应用启动监听

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("项目启动");
    }

    @Override
     public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("项目销毁");
    }
    
}
