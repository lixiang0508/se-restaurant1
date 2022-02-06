package com.zlx.serestaurant.Interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlx.serestaurant.utils.JWTUtils;
import com.zlx.serestaurant.utils.JsonData;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author zlx
 * @create 2021-10-16 7:06 下午
 */
@Slf4j
public class MyInterceptor implements HandlerInterceptor {
     //进入到Controller之前的方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         String method= request.getMethod();
         if("OPTIONS".equalsIgnoreCase(method)) {
             return true;//这是一个 试探性链接 需要放行
         }

        try {
            String token = request.getHeader("token");//从header中取出token
            if (token == null) {
                token = request.getParameter("token");
            }
            if(StringUtils.isNotBlank(token)) {
                Claims claims= JWTUtils.checkJWT(token)  ;
                if(claims==null){
                    //登陆过期了 需要重新登录
                    sendJsonMessage(response, JsonData.buildError("登录过期，请重新登陆"));
                    log.info("登录过期，请重新登陆");
                    return false;
                }
                String userName=(String)claims.get("userName");
                 String staffId =(String)  claims.get("staffId");
                //String phoneNumber =(String) claims.get("phoneNumber") ;
                log.info("{}尝试请求登录",userName);
                request.setAttribute("userName",userName);
                request.setAttribute("staffId",staffId) ;
                return true;       //放行
            }
        }catch (Exception e) {
            //通知登录失败
            sendJsonMessage(response, JsonData.buildError("登录失败，请重新登陆"));
        }
        return false;
    }
      //响应json数据给前端
    public static void sendJsonMessage(HttpServletResponse response,Object obj) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer =response.getWriter();
            writer.print(objectMapper.writeValueAsString(obj)) ;
            writer.close() ;

        } catch (Exception e) {
              e.printStackTrace();
        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
