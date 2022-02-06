package com.zlx.serestaurant.config;

import com.zlx.serestaurant.alipay.AlipayProperties;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 配置文件监听器，用来加载自定义配置文件
 */
@Component
//@RefreshScope 实现配置项的动态加载
public class PropertiesListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        AlipayProperties.loadProperties();
    }
}
