package com.zlx.serestaurant.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zlx
 * @create 2021-10-10 10:22 上午
 */

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String getHello()  {
            // int i =1/0;

        return "Welcome to the intelligent restaurant project!" ;
    }
}
