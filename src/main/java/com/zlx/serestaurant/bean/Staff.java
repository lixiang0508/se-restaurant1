package com.zlx.serestaurant.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author zlx
 * @create 2021-10-10 3:03 下午
 */
//员工类 用于被继承
 @Data
public class Staff {

     public Staff(){}
    private  int  staffId; //工号


    private String userName;//用户名
    private String password;//密码
    private  String name;//姓名
    private int gender;// 性别 0 male 1 female 2 else
    private int age;//年龄
    private int salary;//工资
    private Date onboardDate;//入职日期
    private String phoneNumber;//手机号
    private String role;//五选一 包括管理员  
    @TableField(exist=false)
    private int right;//员工权限

    public Staff(String userName, String password, String name, int gender, int age, String phoneNumber, String role) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
 public Staff(String userName, String password,String phoneNumber) {
        this.userName = userName;
        this.password = password;
        this.phoneNumber=phoneNumber;
    }
}
