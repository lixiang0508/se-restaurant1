package com.zlx.serestaurant.bean;

import lombok.Data;

/**
 * @author zlx
 * @create 2021-10-29 8:49 下午
 */
@Data
public class Orderproduct {
    private int orderId; // 属于哪个order
    private String name; //当前菜品名称
    private int count ;//当前菜品数量
    private int price;//当前菜品价格
private    int  id;//当前菜品id
    public void countplus() {
        this.count++;
    }

}
