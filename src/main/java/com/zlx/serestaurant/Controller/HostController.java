package com.zlx.serestaurant.Controller;

import com.zlx.serestaurant.DTO.TableDTO;
import com.zlx.serestaurant.bean.Customer;
import com.zlx.serestaurant.bean.Dish;
import com.zlx.serestaurant.bean.Orderproduct;
import com.zlx.serestaurant.bean.Table;
import com.zlx.serestaurant.exception.OrderException;
import com.zlx.serestaurant.service.HostService;
import com.zlx.serestaurant.service.OrderService;
import com.zlx.serestaurant.service.Websocket;
import com.zlx.serestaurant.utils.JsonData;
import com.zlx.serestaurant.utils.WebsocketVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author zlx
 * @create 2021-10-29 9:47 下午
 */

@RestController
@Slf4j
public class HostController {



    @Autowired
    private HostService hostService;
    @Autowired
    OrderService orderService;
    @Autowired
    private Websocket websocket;

    @GetMapping("/api/findtable")
    public JsonData findtable(@RequestParam("person") int person) {
           //找到比当前这一波客人人数多的 空闲的 座位数最少的 桌子
        int tableId = hostService.findTable(person);
        if(tableId==-1) {
            return JsonData.buildError("不存在符合要求的餐桌",-1)  ;
        }
        //根据查出来的tableId 找到table 并将这个桌子的状态设置为 使用中 （status=0)
        Table table = hostService.getTable(tableId);
        table.setStatus(0);
        table.setRealOn(person);//当前实际就座人数
        hostService.updateStatus1(tableId);
        //根据table_id 查到 waiter_id
        int waiterId= hostService.findWaiterBytableId(tableId);
        log.info("{}号服务员负责服务",waiterId);
        //TODO   通知服务员 "table\":\"4"
        websocket.sendMessage(WebsocketVO.buildSuccess("有新的客人！服务员请做好准备！","waiter","waiterId\":\""+waiterId).toString());
        //websocket.sendMessage("有新的客人！"+table.getId()+" 号桌"+person+"位");
       // websocket.sendMessage(waiterId+"号服务员请做好准备！");


        //TODO 前端需要展示给迎宾员 当前是查到了几号桌子

        //TODO 建立一个customer对象  插入数据库
        int cusId = (int) (System.currentTimeMillis() % 1000+ new Random().nextInt(90000));
        Customer customer= new Customer(cusId,table.getId(),person);
        customer.setStartTime(new Date());
        log.info("customer id ={}",cusId);
        hostService.addCustomer(customer);

        //修改桌子 更新他的cus_id
            table.setCusId(cusId);
            //table.setStatus(0);//TODO 餐桌状态为 有顾客但是没点菜
            hostService.updateTable(table) ;

        return JsonData.buildSuccess(tableId,"查找成功,新建顾客");
    }

    @GetMapping("/api/showAllTable")
    public JsonData showAllTable() {
        List<Table> tables = hostService.showAllTable();
        List<TableDTO>  ret = new ArrayList<>();
         for  (Table table:tables) {
             //tableId 找到orderId
             //orderId找到所有orderProduct
             int orderId = table.getOrderId();
             List<Orderproduct> orderDetails = orderService.findOrderDetails(orderId);
             TableDTO tb= new TableDTO() ;
             tb.setTable(table);tb.setList(orderDetails);
             ret.add(tb);


         }
        return JsonData.buildSuccess(ret,"查找成功");

    }
    @PostMapping("/api/changeTableStatus") //修改餐桌状态
    public JsonData changeTableStatus(@RequestParam("orderId")int orderId,@RequestParam("id")int id,
                                      @RequestParam("seats")int seats,@RequestParam("page")int page, @RequestParam("xaxis") int xaxis,@RequestParam("yaxis") int yaxis,
                                      @RequestParam("status")int status,@RequestParam("cusId")int cusId,@RequestParam("waiterId")int waiterId){
        hostService.changeTableStatus(id,seats,page,status,cusId,waiterId,orderId,xaxis,yaxis);
        return JsonData.buildSuccess(null,"修改成功");
    }


    @PostMapping("/api/cusLeave")
    public JsonData cusLeave(@RequestParam("table_id") int tableId) {
        //某一桌顾客离店
        //找到 设置顾客的endTime  不重要
        Table table = hostService.getTable(tableId);
        if(table.getCusId()==0) {
            throw new OrderException("客人已经离店，不能再次操作");
           // return JsonData.buildError("客人已经离店，不能再次操作");
        }

        //TODO 通知busboy 发送一条慢走
       // websocket.sendMessage(tableId+ "桌子客人离店");
        websocket.sendMessage(WebsocketVO.buildSuccess("客人离店","host","tableId\":\""+table).toString());
        //更新一下table的信息
        table.setRealOn(0);
        table.setCusId(0);
        table.setStatus(-1) ;//顾客已经离店
        hostService.updateTable(table);

        return JsonData.buildSuccess(table,"客人已经离店");
        

        
    }
}
