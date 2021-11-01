package com.zlx.serestaurant.bean;

import lombok.Data;
import com.zlx.serestaurant.bean.Repository;
import java.util.Map;
/**
 * @author zlx
 * @create 2021-10-10 2:36 下午
 */
//菜品类
 @Data
public class Dish {
    private int price;//价格
    private String name;//菜品名称
    private String img;//菜品图标
    private int saleCount;//菜品销量

    //private Map<Repository,Integer> costMap; //需要的原料名称和数量
    //private int cost;//原料成本
}
