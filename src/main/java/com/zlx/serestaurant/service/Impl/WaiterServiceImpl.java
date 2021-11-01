package com.zlx.serestaurant.service.Impl;

import com.zlx.serestaurant.bean.Dish;
import com.zlx.serestaurant.mapper.WaiterMapper;
import com.zlx.serestaurant.service.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zlx
 * @create 2021-10-30 11:32 上午
 */

@Service
public class WaiterServiceImpl implements WaiterService {

    @Autowired
    private WaiterMapper waiterMapper;

    @Override
    public List<Dish> getMenu() {
        return waiterMapper.getMenu();
    }
}
