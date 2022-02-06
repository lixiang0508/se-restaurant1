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
    private int realOn; //实际就座人数
    private int  page;//在第几页
    private int xaxis;//位置 横坐标
    private int yaxis;//位置 纵坐标
    private int status;//使用状态 -1 没顾客  0有顾客但是没点菜  1 点完单没上菜 2 正在用餐 3 餐桌等待清理


    private int waiterId;//服务员工号
    private int cusId;//顾客id


    
}
