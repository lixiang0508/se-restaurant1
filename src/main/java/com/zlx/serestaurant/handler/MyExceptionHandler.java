package com.zlx.serestaurant.handler;

import com.zlx.serestaurant.utils.JsonData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zlx
 * @create 2021-10-21 9:36 上午
 * 异常处理类
 * 有多种异常就多写几个方法
 */

@ResponseBody
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    public JsonData handlerException(Exception e, HttpServletRequest request){

        return JsonData.buildError("不好！出异常了",-2) ;
        
    }

}
