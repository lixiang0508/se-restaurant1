package com.zlx.serestaurant.Controller;

import com.zlx.serestaurant.bean.Table;
import com.zlx.serestaurant.service.HostService;
import com.zlx.serestaurant.utils.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zlx
 * @create 2021-12-12 4:49 下午
 */
 @Slf4j
@RestController
public class BusBoyController {

    @Autowired
    private HostService hostService;

    @PostMapping("/api/cleanTable")
    public JsonData cleanTable(@RequestParam("table_id") int tableId)  {
        Table table = hostService.getTable(tableId);
        table.setStatus(-1);
        log.info("打扫了{}号桌子",tableId);

        hostService.updateTable(table);
        return JsonData.buildSuccess(table,"清理成功！") ;



    }
}
