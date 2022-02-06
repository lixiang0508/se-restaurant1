package com.zlx.serestaurant.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author zlx
 * @create 2021-10-29 8:49 下午
 */
@Data
//订单详情
public class Orderproduct {
    private int orderId; // 属于哪个order
    private int dishId;//当前菜品ID
    private String name; //当前菜品名称
    private int count ;//当前菜品数量
    private int price;//当前菜品价格
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    private Date orderDate;       //下单时间
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    private Date  finishDate;//完成时间
    private int status=0;//0 未完成 1 已完成
    private String img;//图片

    public Orderproduct() {
        
    }

    public void countplus() {
        this.count++;
    }

    public Orderproduct(int orderId,  int count) {
        this.orderId = orderId;

        this.count = count;
    }
}
