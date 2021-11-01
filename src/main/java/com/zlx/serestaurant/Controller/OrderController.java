package com.zlx.serestaurant.Controller;

import com.alibaba.fastjson.JSON;
import com.zlx.serestaurant.utils.JsonData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author zlx
 * @create 2021-10-31 10:21 下午
 */

@RestController
public class OrderController {

    //下单接口
        @PostMapping("/api/giveorder")
        public void giveOrder(@RequestBody Map<String,Object> params){
           int tableId= (int) params.get("tableId");    //餐桌号
             List dishes= (List) params.get("dishes");  //具体下单信信息 这是一个list
            System.out.println(tableId);
            System.out.println(dishes.get(0).toString());
            
             String ss= dishes.get(0).toString();
             for(char c:ss.toCharArray()) {
                 System.out.println(c);
             }
             /*
             需要拿到 下单菜品的id 以及对应的count
             */
              //需要把下单信息传递给厨子
            //这个 我觉得可以把这个信息往session里写一下
            //除此之外 还要明显的通知一下

        }
}
