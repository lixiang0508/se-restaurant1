package com.zlx.serestaurant.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.zlx.serestaurant.Interceptor.LoginInterceptor;
import com.zlx.serestaurant.Interceptor.ManagerInterceptor;
import com.zlx.serestaurant.Interceptor.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zlx
 * @create 2021-10-16 7:08 下午
 */
//放行 "/" "/index" "/api/login" "/api/register"
@Configuration
public class Myconfig implements WebMvcConfigurer {

      @Bean
      MyInterceptor    myInterceptor(){
          return new MyInterceptor();
      }

      @Bean   //管理员拦截器
    ManagerInterceptor managerInterceptor() {
          return new ManagerInterceptor();
      }
    
    @Override         //添加 拦截器 登录判断
    public void addInterceptors(InterceptorRegistry registry) {

          registry.addInterceptor(myInterceptor()).addPathPatterns("/api/**")
            .excludePathPatterns("/api/login","/api/register");  

          registry.addInterceptor(managerInterceptor()).addPathPatterns("/api/dismiss")
                  .addPathPatterns("/api/adjustSalary").addPathPatterns("/api/adjustRole").
                  addPathPatterns("api/dailyTurnover");

    WebMvcConfigurer.super.addInterceptors(registry);
        /*
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/api/**")
                .excludePathPatterns("/api/login","/api/register","/api/login1") ;   */
    }

}
