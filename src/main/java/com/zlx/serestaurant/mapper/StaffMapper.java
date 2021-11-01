package com.zlx.serestaurant.mapper;

import com.zlx.serestaurant.bean.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zlx
 * @create 2021-10-18 9:44 上午
 */

@Mapper
public interface StaffMapper {

    void insertNewStaff(Staff staff);
    Staff selectByUserNameAndPwd(@Param("userName")String userName,@Param("password")String password) ;
}
