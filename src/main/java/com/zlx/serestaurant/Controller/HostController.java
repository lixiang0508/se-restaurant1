package com.zlx.serestaurant.Controller;

import com.zlx.serestaurant.bean.Table;
import com.zlx.serestaurant.service.HostService;
import com.zlx.serestaurant.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zlx
 * @create 2021-10-29 9:47 下午
 */

@RestController
public class HostController {

    @Autowired
    private HostService hostService;

    @GetMapping("/api/findtable")
    public JsonData findtable(@RequestParam("person") int person) {
           //找到比当前这一波客人人数多的 空闲的 座位数最少的 桌子
        int tableId = hostService.findTable(person);
        if(tableId==-1) {
            return JsonData.buildError("不存在符合要求的餐桌",-1)  ;
        }
        //根据查出来的tableId 找到table 并将这个桌子的状态设置为 使用中 （status=1)
        Table table = hostService.getTable(tableId);
        table.setStatus(1);
        hostService.updateStatus1(tableId);
        // to update 通知服务员
        //需要显示查找到了几号桌子
        return JsonData.buildSuccess(tableId,"查找成功");
    }

    @GetMapping("/api/showAllTable")
    public JsonData showAllTable() {
        List<Table> tables = hostService.showAllTable();
        return JsonData.buildSuccess(tables,"查找成功");

    }
}
