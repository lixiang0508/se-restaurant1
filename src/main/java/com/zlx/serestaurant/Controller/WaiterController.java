package com.zlx.serestaurant.Controller;

import com.zlx.serestaurant.DTO.OrderDTO;
import com.zlx.serestaurant.DTO.TableDTO;
import com.zlx.serestaurant.bean.Dish;
import com.zlx.serestaurant.bean.Orderproduct;
import com.zlx.serestaurant.bean.Table;
import com.zlx.serestaurant.service.DishService;
import com.zlx.serestaurant.service.HostService;
import com.zlx.serestaurant.service.OrderService;
import com.zlx.serestaurant.service.WaiterService;
import com.zlx.serestaurant.utils.JsonData;
import javafx.scene.control.TableColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zlx
 * @create 2021-10-29 9:30 下午
 */

@RestController
public class WaiterController { //服务员操作

    @Autowired
    private WaiterService waiterService;

    @Autowired
    private HostService hostService;

    @Autowired
    private OrderService orderService;

    @Autowired
    DishService dishService;

    //菜单界面       查看所有菜单
    @GetMapping("api/menu")
    public JsonData getMenu(){
        List<Dish> menu = waiterService.getMenu();
        return JsonData.buildSuccess(menu,"查找成功");

    }
    //根据菜品类别查找菜品（比如查找出所有凉菜）
    @PostMapping("api/getBycategory")
    public JsonData getBycategory(@RequestParam("category") int category)  {
        List<Dish> dishList = waiterService.getByCategory(category);
        if(dishList.isEmpty()||dishList==null) {
            return JsonData.buildError("查找失败");
        }
        return JsonData.buildSuccess(dishList,"查找成功");
    }
    //根据菜品id查找菜品
    @GetMapping("/api/getByDishId")
    public JsonData getByDishId(@RequestParam("dishId") int dishId) {

        Dish dish = waiterService.getByDishId(dishId);
        if(dish==null)               {
            return JsonData.buildError("查找失败");
        }
        return JsonData.buildSuccess(dish,"查找成功");
    }

    //服务员引导就坐
    @PostMapping("/api/sit")
    public  JsonData sit(@RequestParam("table_id")int tableId) {
        Table table = hostService.getTable(tableId);
            table.setStatus(0); //有顾客，没点单
        hostService.updateTable(table);
        return JsonData.buildSuccess(table,"就坐成功");

    }
    @PostMapping("/api/getTable")
    public JsonData getTable(@RequestParam("table_id")int tableId) {
        Table table = hostService.getTable(tableId);
        if(table==null)       {
            try {
                throw new Exception("没有这个餐桌号");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int orderId = table.getOrderId();//这个餐桌的orderId
        List<Orderproduct> orderDetails = orderService.findOrderDetails(orderId);
        TableDTO tbdto = new TableDTO();
        tbdto.setList(orderDetails);
        tbdto.setTable(table);

        return JsonData.buildSuccess(tbdto,"找到餐桌");

    }
     //TODO getTable 返回orderproduct list


    
    //TODO 上菜接口
    //传入 order_id 找到order_id 对应的所有orderproduct 把status改成2
    //判断 所有的 如果还有没做好的 失败 不能上菜   也就是说一桌的菜必须一起上
    @PostMapping("api/serveDish")
    public JsonData serveDish(@RequestParam("order_id")int orderId) {
        List<Orderproduct> orderDetails = orderService.findOrderDetails(orderId);
        for(Orderproduct odp:orderDetails){
            if(odp.getStatus()==0){
                return JsonData.buildError("菜没做好,不能上菜!");
            }
            if(odp.getStatus()==2){
                return JsonData.buildError("菜已经上完了,不能上菜!");
            }

        }
        OrderDTO orderByOrderId = orderService.findOrderByOrderId(orderId);//int tableId=orderByOrderId.getTableId();//桌号
        //根据tableId找到table
        int tableId=orderByOrderId.getTableId();//桌号
        Table table = hostService.getTable(tableId);
        table.setStatus(2);//顾客正在用餐 但是还没吃完
        hostService.updateTable(table);

        orderService.changeOrderStatus(orderId);
        return JsonData.buildSuccess(orderDetails,orderId+"成功上菜") ;


    }
}


