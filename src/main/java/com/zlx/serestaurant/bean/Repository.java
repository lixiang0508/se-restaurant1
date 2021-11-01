package com.zlx.serestaurant.bean;

import lombok.Data;

/**
 * @author zlx
 * @create 2021-10-10 3:45 下午
 */
//库存类
@Data
public class Repository {


    private int id;//库存类别 蔬菜 豆制品 菌类 调料 啤酒 饮料
    private String name; //名称
    private int count;//剩余数量
    private int  price;//进价
}
