package com.zlx.serestaurant.Interceptor;

import com.zlx.serestaurant.bean.Staff;
import com.zlx.serestaurant.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zlx
 * @create 2021-12-15 10:53 上午
 */

@Slf4j
public class  ManagerInterceptor implements HandlerInterceptor{

    @Autowired
    StaffService staffService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截器生效");
        String userName = (String) request.getAttribute("userName");
          if(userName==null) {
              log.info("未登录") ;
              return false;
          }
          Staff staff = staffService.findByUserName(userName);
        //Staff staff= (Staff) request.getSession().getAttribute("currentstaff");
        if(staff==null)   {
            response.getWriter().print("The staff has not login") ;
            return false;
        }
        String role = staff.getRole();
        if(!role.equals("manager")){
            log.info("不是经理不要瞎搞") ;
            return false;
        }

        return true;
    }
}
