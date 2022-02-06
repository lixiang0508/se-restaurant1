package com.zlx.serestaurant.Controller;

import com.zlx.serestaurant.bean.Dish;
import com.zlx.serestaurant.bean.Staff;
import com.zlx.serestaurant.service.AdminService;
import com.zlx.serestaurant.service.DishService;
import com.zlx.serestaurant.service.OrderService;
import com.zlx.serestaurant.utils.JsonData;
import com.zlx.serestaurant.vo.DishInvetVO;
import com.zlx.serestaurant.vo.DishSaleVO;
import com.zlx.serestaurant.vo.FirstPageVO;
import com.zlx.serestaurant.vo.SalaryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zlx
 * @create 2021-10-30 2:54 下午
 */

@RestController
public class AdminController {

        @Autowired
        private AdminService adminService;
        @Autowired
        private DishService dishService;
        @Autowired
        private OrderService orderService;

        @PostMapping("api/firstPage")
        public JsonData firstPage() {
          int orderCount=  orderService.findAllOrders().size();
            FirstPageVO firstPageVO = new FirstPageVO();
            firstPageVO.setOrderCount(orderCount);
            List<SalaryVO>  salaryVOList = new ArrayList<>();
             List<DishInvetVO>   dishInvetVOList =  new ArrayList<>();
             List<DishSaleVO>  dishSaleVOList = new ArrayList<>();

            List<Staff> staffs = adminService.showAllemps();
            for(Staff staff:staffs){
                int salary =staff.getSalary();
                int staffId = staff.getStaffId();
                SalaryVO salaryVO = new SalaryVO(salary,staffId+"");
                salaryVOList.add(salaryVO) ;

            }
            Collections.sort(salaryVOList,(a,b)->a.getSalary()-b.getSalary());
            firstPageVO.setSalaryInfo(salaryVOList);
            List<Dish> allDishes = dishService.findAllDishes();
            for(Dish dish:allDishes) {
                int id = dish.getId();
                int saleCount = dish.getSaleCount();
                int repository = dish.getRepository();
                DishSaleVO dishSaleVO = new DishSaleVO(id+"",saleCount);
                DishInvetVO dishInvetVO =new DishInvetVO(id+"",repository);
                dishInvetVOList.add(dishInvetVO);
                dishSaleVOList.add(dishSaleVO);



            }
            Collections.sort(dishSaleVOList,(a,b)->a.getSaleCount()-b.getSaleCount()) ;
            Collections.sort(dishInvetVOList,(a,b)->a.getInventory()-b.getInventory()) ;
            firstPageVO.setDishInventory(dishInvetVOList);

            firstPageVO.setDishSaleCount(dishSaleVOList);
            return JsonData.buildSuccess(firstPageVO,"查找成功了") ;

        }




    @PostMapping("api/employeeStatus")
    public JsonData employeeStatus(){
          //查找所有员工
        List<Staff> staff = adminService.showAllemps();
        return JsonData.buildSuccess(staff,"请求成功");


    }
    @PostMapping("api/dishOn")
    public JsonData  dishOn(Dish dish){
       adminService.dishOn(dish);
          return JsonData.buildSuccess(dish,"上架成功");
    }
    @PostMapping("api/dishDown")
    public JsonData dishDown(@Param("dish_id")Integer dishId){
          adminService.dishDown(dishId);
        return JsonData.buildSuccess(dishId,"下架成功");
    }
}
