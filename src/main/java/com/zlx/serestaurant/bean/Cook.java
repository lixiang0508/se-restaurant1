package com.zlx.serestaurant.bean;

import lombok.Data;

import java.util.Queue;

/**
 * @author zlx
 * @create 2021-10-10 3:01 下午
 */
@Data
public class Cook extends Staff {
   private  Queue<Order> orderQueue; //订单队列
    public void addOrder(Order order) {
        orderQueue.offer(order) ;
    }
    public void pollOrder(){
        orderQueue.poll();
    }
}
