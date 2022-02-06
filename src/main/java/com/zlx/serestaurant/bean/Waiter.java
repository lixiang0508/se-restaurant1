package com.zlx.serestaurant.bean;

import lombok.Data;

import java.util.List;

/**
 * @author zlx
 * @create 2021-10-10 2:34 下午
 */
//员工类
@Data
public class Waiter extends Staff {

    private List<Table> tables;  //服务员负责的餐桌 列表实现
    public void placeOrder(int tableId){
        //某一桌下单 通过tableId  找到这一桌当前订单号

    }
    public void payOrder(int tableId){
        //某一桌结账
        //这一桌设为待整理 busboy
    }

}
