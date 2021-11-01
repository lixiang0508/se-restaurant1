package com.zlx.serestaurant.service;

import com.zlx.serestaurant.bean.Staff;

/**
 * @author zlx
 * @create 2021-10-18 9:43 上午
 */
public interface StaffService {

     void addNewStaff(Staff staff);
     Staff selectStaff(String userName,String password);
}
