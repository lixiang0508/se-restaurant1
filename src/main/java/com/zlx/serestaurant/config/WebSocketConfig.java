package com.zlx.serestaurant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * @author zlx
 * @create 2021-11-27 10:47 下午
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public  ServerEndpointExporter serverEndPointExporter(){

        return new ServerEndpointExporter();

    }
}
