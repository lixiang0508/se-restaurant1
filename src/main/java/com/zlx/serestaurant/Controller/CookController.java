package com.zlx.serestaurant.Controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zlx.serestaurant.DTO.DishDTO;
import com.zlx.serestaurant.DTO.OrderDTO;
import com.zlx.serestaurant.bean.Orderproduct;
import com.zlx.serestaurant.bean.Table;
import com.zlx.serestaurant.exception.OrderException;
import com.zlx.serestaurant.service.CookService;
import com.zlx.serestaurant.service.HostService;
import com.zlx.serestaurant.service.OrderService;
import com.zlx.serestaurant.service.Websocket;
import com.zlx.serestaurant.utils.JsonData;
import com.zlx.serestaurant.utils.WebsocketVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zlx
 * @create 2021-11-28 11:32 上午
 */
@RestController
public class CookController {

    @Autowired
   private  CookService cookService;

    @Autowired
    private OrderService orderService;

    @Autowired
   private  Websocket websocket;

    @Autowired
    private HostService hostService;

    @PostMapping("/api/findDishesTodo")
    public JsonData findDishesTodo() {

        List<Orderproduct> dishesTodo = orderService.findDishesTodo();
        return JsonData.buildSuccess(dishesTodo,"待完成的菜品");

    }


    @PostMapping("/api/findDishesDone")
    public JsonData findDishesDone() {
        List<Orderproduct> dishesDone = orderService.findDishesDone();
        return JsonData.buildSuccess(dishesDone,"已完成的菜品");
    }

    //多个菜品同时做完
    @PostMapping("/api/finishDishes")
    public  JsonData finishDishes(@RequestParam("items") String items){

        Gson gson = new Gson();
        List<DishDTO>   dishDTOList = new ArrayList<> ();
        try{
            dishDTOList=gson.fromJson(items,new TypeToken<List<DishDTO>>(){}.getType());
        }  catch(Exception e)  {
            e.printStackTrace();
        }
        for(DishDTO dishDTO:dishDTOList) {
             int dishId=  dishDTO.getDishId();
             int orderId= dishDTO.getOrderId();
            try {
                findOneDishHelper(orderId,dishId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
      return JsonData.buildSuccess(dishDTOList,"成功完成这几个菜");
    }

    public void findOneDishHelper(int orderId,int dishId) throws Exception {
        //通过orderId 和dishId 查找到唯一的orderproduct
        Orderproduct orderproduct= cookService.findDish(orderId,dishId);

        if(orderproduct==null)  {
            throw new Exception("没有待完成的菜");
        }
        OrderDTO orderByOrderId = orderService.findOrderByOrderId(orderId);
        int tableId=orderByOrderId.getTableId();//桌号
        //根据tableId找到table
        Table table = hostService.getTable(tableId);
        //table.setStatus(2);//顾客正在用餐 但是还没吃完
        hostService.updateTable(table);


        orderproduct.setFinishDate(new Date());
        orderproduct.setStatus(1);//设置状态为完成

        orderService.updateOrderproduct(orderproduct); ;

        //传递信息
       websocket.sendMessage( WebsocketVO.buildSuccess("服务员请取餐，上菜","waiter","tableId\":\""+tableId+"\","+"\"orderproductName\":\""+orderproduct.getName()).toString());
        //
       //websocket.sendMessage("服务员请取餐，上菜"+tableId+"桌的 "+orderproduct.getName()+"好了");
        //return JsonData.buildSuccess(tableId,"完成一道菜") ;
    }
    @PostMapping("/api/finishOneDish")
    public JsonData finishOneDish(@RequestParam("order_id") int orderId,@RequestParam("dish_id")int dishId){

        //通过orderId 和dishId 查找到唯一的orderproduct
        Orderproduct orderproduct= cookService.findDish(orderId,dishId);




        if(orderproduct==null)  {
            return  JsonData.buildError("没有待完成的菜") ;
        }
        OrderDTO orderByOrderId = orderService.findOrderByOrderId(orderId);
        int tableId=orderByOrderId.getTableId();//桌号
                //根据tableId找到table
        Table table = hostService.getTable(tableId);
        table.setStatus(2);//顾客正在用餐 但是还没吃完
        hostService.updateTable(table);


        orderproduct.setFinishDate(new Date());
        orderproduct.setStatus(1);//设置状态为完成

        orderService.updateOrderproduct(orderproduct);
        websocket.sendMessage(WebsocketVO.buildSuccess("服务员请取餐，上菜","waiter","tableId\":\""+tableId+"orderproductName:"+orderproduct.getName()).toString());
        //

     //   websocket.sendMessage(WebsocketVO.buildSuccess("服务员请取餐，上菜"+tableId+"桌的 "+orderproduct.getName()+"好了","waiter",).toString());

        //websocket.sendMessage("服务员请取餐，上菜"+tableId+"桌的 "+orderproduct.getName()+"好了");
        return JsonData.buildSuccess(tableId,"完成一道菜") ;
    }

}
