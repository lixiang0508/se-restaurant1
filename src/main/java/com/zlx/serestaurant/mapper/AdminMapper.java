package com.zlx.serestaurant.mapper;

import com.zlx.serestaurant.bean.Staff;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zlx
 * @create 2021-10-30 2:57 下午
 */

@Mapper
public interface AdminMapper {

    List<Staff> showAllemps();
}
