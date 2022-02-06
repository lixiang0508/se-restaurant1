package com.zlx.serestaurant.Interceptor;

import com.zlx.serestaurant.bean.Staff;
import com.zlx.serestaurant.utils.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zlx
 * @create 2021-11-26 3:23 下午
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         log.info("拦截器生效");

        Staff staff= (Staff) request.getSession().getAttribute("currentstaff");
        if(staff==null)   {
            response.getWriter().print("The staff has not login") ;
            return false;
        }
        return true;
    }

}
