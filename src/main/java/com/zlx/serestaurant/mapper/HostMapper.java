package com.zlx.serestaurant.mapper;

import com.zlx.serestaurant.bean.Table;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zlx
 * @create 2021-10-29 9:52 下午
 */

@Mapper
public interface HostMapper {
    int findTable(@Param("person")int person);

    int countTable(@Param("person") int person); //判断有没有符合要求的桌子

    Table getTable(@Param("tableId") int tableId);

    void updateStatus1(@Param("tableId")int tableId);

    List<Table> showAllTable();
}
