package com.zlx.serestaurant.service.Impl;

import com.zlx.serestaurant.bean.Table;
import com.zlx.serestaurant.mapper.HostMapper;
import com.zlx.serestaurant.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zlx
 * @create 2021-10-29 9:50 下午
 */
@Service
public class HostServiceImpl implements HostService {


    @Autowired
    private HostMapper hostMapper;


    @Override
    public int findTable(int person) {
        if(hostMapper.countTable(person)==0)  return -1;
        return hostMapper.findTable(person);
    }

    @Override
    //根据tableId 找到对应的table
    public Table getTable(int tableId) {
        return hostMapper.getTable(tableId);
    }

    @Override
    //更新table的状态为1
    public void updateStatus1(int tableId) {
       // Table tbltmp= hostMapper.getTable(tableId);
        hostMapper.updateStatus1(tableId) ;
    }

    @Override
    public List<Table> showAllTable() {
        return hostMapper.showAllTable();
    }
}
