package com.zlx.serestaurant.mapper;

import com.zlx.serestaurant.bean.Dish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zlx
 * @create 2021-10-30 11:35 上午
 */

@Mapper
public interface WaiterMapper {
    List<Dish> getMenu() ;
}
