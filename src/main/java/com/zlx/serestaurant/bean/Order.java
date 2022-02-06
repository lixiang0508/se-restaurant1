package com.zlx.serestaurant.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zlx.serestaurant.enums.OrderStatusEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zlx
 * @create 2021-10-10 2:56 下午
 */
//订单类


//订单主表
@Data
public class Order {
    private int cusId;//该订单属于哪个用户
    private int orderId;//订单号
    private int price;//总价
    private int  tableId;//餐桌号  -1为外卖 -2外带
    @TableField(exist=false)
    private List<Orderproduct> orderproductList; //哪个菜品点了多少份
    private int status= OrderStatusEnum.NEW.getCode();//0 新订单 1在制作 2已完成 3 已经取消
    private int payStatus;//支付状态 0 待支付 1 已经支付 

    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    private Date orderDate;       //下单时间
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    private Date  finishDate;//完成时间
      public Order(){

      }
    @Override
    public String toString() {
        return "Order{" +
                "cusId=" + cusId +
                ", orderId=" + orderId +
                ", price=" + price +
                ", tableId=" + tableId +

                ", status=" + status +
                ", orderDate=" + orderDate +
                ", finishDate=" + finishDate +
                '}';
    }
}
