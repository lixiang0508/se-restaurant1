package com.zlx.serestaurant.service;

import com.zlx.serestaurant.bean.Dish;

import java.util.List;

/**
 * @author zlx
 * @create 2021-10-30 11:31 上午
 */
public interface WaiterService {

    List<Dish> getMenu();
}
