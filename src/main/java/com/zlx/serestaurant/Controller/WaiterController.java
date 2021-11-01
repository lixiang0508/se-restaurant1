package com.zlx.serestaurant.Controller;

import com.zlx.serestaurant.bean.Dish;
import com.zlx.serestaurant.service.WaiterService;
import com.zlx.serestaurant.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zlx
 * @create 2021-10-29 9:30 下午
 */

@RestController
public class WaiterController { //服务员操作

    @Autowired
    private WaiterService waiterService;

    //菜单界面       查看所有菜单
    @PostMapping("api/menu")
    public JsonData getMenu(){
        List<Dish> menu = waiterService.getMenu();
        return JsonData.buildSuccess(menu,"查找成功");

    }

}
