package com.zlx.serestaurant.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author zlx
 * @create 2021-10-10 2:34 下午
 */
//餐桌类     餐桌号 和map需要传递给厨师
@Data
public class Table {

    private int orderId;//当前订单号
    private int id;//餐桌号
    private int seats;//有多少座位
    private int  page;//在第几页
    private int status;//使用状态 0清理完毕 1 正在用餐 2 待清理
    //private int price;//这桌菜品的总价格
    @TableField(exist = false)
    private List<Dish> dishes; //所有菜品
    @TableField(exist = false)
    private Map<Dish,Integer> dishMap;  //key是菜品 value是点了多少份
    private int waiterId;//服务员工号
    private int cusId;//顾客id
    
}
