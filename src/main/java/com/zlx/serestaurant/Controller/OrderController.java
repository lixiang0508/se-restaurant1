package com.zlx.serestaurant.Controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.zlx.serestaurant.DTO.OrderDTO;
import com.zlx.serestaurant.alipay.AlipayBean;
import com.zlx.serestaurant.bean.Order;
import com.zlx.serestaurant.bean.Orderproduct;
import com.zlx.serestaurant.bean.Table;
import com.zlx.serestaurant.exception.OrderException;
import com.zlx.serestaurant.request.OrderRequest;
import com.zlx.serestaurant.service.*;
import com.zlx.serestaurant.utils.JsonData;
import com.zlx.serestaurant.utils.OrderRequest2OrderDTOConverter;
import com.zlx.serestaurant.utils.WebsocketVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zlx
 * @create 2021-10-31 10:21 下午
 */

@RestController
@Slf4j
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    DishService dishService;

    @Autowired
    PayService payService;

    @Autowired
    HostService hostService;

    @Autowired
    Websocket websocket;

    //创建订单
    //下单接口
    @PostMapping("/api/createOrder")
    public JsonData createOrder(OrderRequest orderRequest, BindingResult bindingResult) {
          //传入customerId tableId items(订单中的具体商品)
        log.info("参数为,{}",orderRequest.toString());
        if(bindingResult.hasErrors()){
            log.error("创建订单参数不正确");
            throw new OrderException("创建订单参数不正确") ;
            //return JsonData.buildError("创建订单失败 参数不正确");
        }

        OrderDTO orderDTO = OrderRequest2OrderDTOConverter.convert(orderRequest);
        if(orderDTO.getOrderproductList().size()==0){
            throw new OrderException("创建订单菜品不能为空") ;
        }
        orderDTO.setOrderDate(new Date());
        OrderDTO createResult = orderService.create(orderDTO);

        //TODO 传递给厨师   发送websocket消息 给浏览器
        websocket.sendMessage(WebsocketVO.buildSuccess("Attention!有新的订单","cook","orderId\":\""+orderDTO.getOrderId()).toString());
        //websocket.sendMessage("Attention!有新的订单");//TODO 用json序列化一下 to: 角色 err 错误码 msg 信息
       // websocket.sendMessage(orderDTO.getOrderId()+"");
        
        int table_id = orderRequest.getTable_id();
        int cus_id = orderRequest.getCus_id();
        Table table = hostService.getTable(table_id);
        table.setStatus(1); //点完单没上菜
        table.setCusId(cus_id);
        table.setOrderId(orderDTO.getOrderId());

        hostService.updateTable(table);


        return JsonData.buildSuccess(createResult,"创建成功") ;
    }


  




            //根据用户id查找订单
        @PostMapping("/api/findOrderByCusId")
        public JsonData findOrderByCusId(@RequestParam("cusId")int cusId) {

            List<Order> orders = orderService.findOrderByCusId(cusId);
            if(orders==null)     {
                return JsonData.buildError("订单未找到");
            }
            return JsonData.buildSuccess(orders,"查找订单成功");

        }
        //根据orderId查找订单详情
        @PostMapping("/api/findOrderDetails")
       public JsonData findOrderDetails(@RequestParam("orderId") int orderId) {
            List<Orderproduct> orderDetails = orderService.findOrderDetails(orderId);
            if(orderDetails==null) {
                return JsonData.buildError("订单详情未找到");
            }
            return JsonData.buildSuccess(orderDetails,"找到订单详情") ;
        }
        //订单取消
    @PostMapping("/api/cancelOrder")
    public JsonData  cancelOrder(@RequestParam("orderId") int orderId){
        OrderDTO orderByOrderId = orderService.findOrderByOrderId(orderId);
        if(orderByOrderId==null){
            return JsonData.buildError("订单未找到,不能取消");
        }
        Table table = hostService.findTableByOrderId(orderId);
        table.setOrderId(0);
        hostService.updateTable(table);

        OrderDTO cancel = orderService.cancel(orderByOrderId);


return JsonData.buildSuccess(cancel,"取消订单成功");
    }
    //查找所有订单
    @PostMapping("/api/findAllOrders")
    public JsonData findAllOrders() {
        List<OrderDTO> allOrders = orderService.findAllOrders();
        return JsonData.buildSuccess(allOrders,"找到当前所有订单");

    }


    @PostMapping("/api/payOrder")
    public JsonData payOrder(@RequestParam("order_id")int orderId)  {
        OrderDTO orderByOrderId = orderService.findOrderByOrderId(orderId);
        int tableId = orderByOrderId.getTableId();
        Table table = hostService.getTable(tableId);
        
        if(orderByOrderId==null){
            return JsonData.buildError("订单未找到,不能支付");
        }
        OrderDTO paidOrder = orderService.paid(orderByOrderId);
        table.setStatus(3);  //顾客已经结账 还没清扫
        table.setOrderId(0);
        hostService.updateTable(table);
        //TODO 调用支付宝接口
        
        return JsonData.buildSuccess(paidOrder,"订单支付成功");
        
    }
    @PostMapping(value = "/api/alipay")
    public String alipay(@RequestParam ("outTradeNo") String outTradeNo, @RequestParam ("subject") String subject, @RequestParam("total_amount") String totalAmount, @RequestParam("body") String body) throws AlipayApiException {
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setOut_trade_no(outTradeNo);
        alipayBean.setSubject(subject);
        alipayBean.setTotal_amount(totalAmount);
        alipayBean.setBody(body);
        return payService.aliPay(alipayBean);
    }
}
