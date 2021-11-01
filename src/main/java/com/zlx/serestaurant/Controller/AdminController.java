package com.zlx.serestaurant.Controller;

import com.zlx.serestaurant.bean.Staff;
import com.zlx.serestaurant.service.AdminService;
import com.zlx.serestaurant.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zlx
 * @create 2021-10-30 2:54 下午
 */

@RestController
public class AdminController {

        @Autowired
        private AdminService adminService;

    @PostMapping("api/employeeStatus")
    public JsonData employeeStatus(){
          //查找所有员工
        List<Staff> staff = adminService.showAllemps();
        return JsonData.buildSuccess(staff,"请求成功");


    }
}
