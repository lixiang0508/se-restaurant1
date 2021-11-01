package com.zlx.serestaurant.service.Impl;

import com.zlx.serestaurant.bean.Staff;
import com.zlx.serestaurant.mapper.StaffMapper;
import com.zlx.serestaurant.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zlx
 * @create 2021-10-18 9:44 上午
 */
@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    StaffMapper staffMapper;


    @Override
    public void addNewStaff(Staff staff) {
        staffMapper.insertNewStaff(staff);
    }

    @Override
    public Staff selectStaff(String userName, String password) {
       return staffMapper.selectByUserNameAndPwd(userName, password);
    }
}
