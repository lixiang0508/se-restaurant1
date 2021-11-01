package com.zlx.serestaurant.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zlx
 * @create 2021-10-10 3:23 下午
 */
//顾客类
@Data
public class Customer {
    private int cusId;//用户Id
    private  String userName;//用户名
    private String pwd;//密码
    private int mode;//0 外卖 1 订餐 2 直接进店吃饭
    private int status;//0 未注册 1 已注册
    private Date startTime; //开始时间（下单）
    private Date endTime;//结束时间(离店)
    private List<Order> orderList ;//  该用户的订单列表
    public void cancelOrder( int orderId) {
            //通过orderId找到order list里删除对应Order
    }
    public void addOrder(){
       //增添一个Order
    }
    public void updateOrder(){
        //修改一个Order
    }

}
