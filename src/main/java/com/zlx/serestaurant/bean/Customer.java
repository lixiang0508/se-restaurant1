package com.zlx.serestaurant.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.Setter;
import lombok.Getter;

import java.util.Date;
import java.util.List;

/**
 * @author zlx
 * @create 2021-10-10 3:23 下午
 */
//顾客类
@Data
@Setter
@Getter
public class Customer {
    private int cusId;//用户Id
    private  String userName;//用户名
    private String pwd;//密码
    private int mode=2;//0 外卖 1 订餐 2 直接进店吃饭
    private int status;//0 未注册 1 已注册
    private Date startTime; //开始时间（下单）
    private Date endTime;//结束时间(离店)
    private int tableId; //餐桌id
    private int amount;//用餐人数

    public Customer(int cusId,  int tableId, int amount) {
        this.cusId = cusId;

        this.tableId = tableId;
        this.amount = amount;
    }

}
