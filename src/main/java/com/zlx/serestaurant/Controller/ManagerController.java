package com.zlx.serestaurant.Controller;

import com.zlx.serestaurant.bean.Order;
import com.zlx.serestaurant.bean.Staff;
import com.zlx.serestaurant.service.ManagerService;
import com.zlx.serestaurant.utils.JsonData;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @PostMapping("api/findAllStaff")
    public JsonData findAllStaff() {
        List<Staff> allStaff = managerService.findAllStaff();
        return  JsonData.buildSuccess(allStaff,"查找成功");
    }
    @PostMapping("api/dismiss")
    public JsonData dismissStaff(@Param("staffId")int staffId){
        Staff staff = managerService.findStaffById(staffId);
        if(staff == null){
            return JsonData.buildError("不存在该员工");
        }
        managerService.deleteStaff(staffId);
        return JsonData.buildSuccess(staffId,"删除成功");
    }
    @PostMapping("api/adjustSalary")
    public JsonData adjustStaffSalary(@Param("staffId")int staffId,@Param("newSalary")int newSalary){
        Staff staff = managerService.findStaffById(staffId);
        if(staff == null){
            return JsonData.buildSuccess("不存在该员工");
        }
        managerService.adjustStaffSalary(staffId,newSalary);
        return JsonData.buildSuccess(staffId,"修改成功");
    }
    @PostMapping("api/adjustRole")
    public JsonData adjustStaffRole(@Param("StaffId")int staffId,@Param("newRole")String newRole){
        Staff staff = managerService.findStaffById(staffId);
        if(staff == null){
            return JsonData.buildSuccess("不存在该员工");
        }
        managerService.adjustStaffRole(staffId,newRole);
        return JsonData.buildSuccess(staffId,"修改成功");
    }
    @PostMapping("api/dailyTurnover")
    public JsonData getTurnover(@Param("date")String date){
        List<Order> orderList = managerService.turnoverByday(date);
        int sum=0;
        for(int i=0;i<orderList.size();i++)
            sum+=orderList.get(i).getPrice();
        return JsonData.buildSuccess(sum,date+"营业额");
    }
}
