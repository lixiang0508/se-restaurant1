package com.zlx.serestaurant.service.Impl;

import com.zlx.serestaurant.bean.Staff;
import com.zlx.serestaurant.mapper.AdminMapper;
import com.zlx.serestaurant.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zlx
 * @create 2021-10-30 2:56 下午
 */
@Service
public class AdminServiceImpl implements AdminService {
     @Autowired
     private AdminMapper adminMapper;



    @Override
    public List<Staff> showAllemps() {
        return adminMapper.showAllemps();
    }
}
