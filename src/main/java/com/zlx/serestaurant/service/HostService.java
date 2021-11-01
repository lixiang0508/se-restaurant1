package com.zlx.serestaurant.service;

import com.zlx.serestaurant.bean.Table;

import java.util.List;

/**
 * @author zlx
 * @create 2021-10-29 9:50 下午
 */
public interface HostService {

    int findTable(int person);
    Table getTable(int tableId) ;
    void updateStatus1(int tableId);
    List<Table> showAllTable() ;
}
