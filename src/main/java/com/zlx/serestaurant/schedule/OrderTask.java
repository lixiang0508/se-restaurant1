package com.zlx.serestaurant.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author zlx
 * @create 2021-10-29 1:00 下午
 */
  //定时统计 订单
@Component
public class OrderTask {

   // @Scheduled(fixedRate = 2000) //2s 执行一次 单位是ms
    public void  sum() { //交易总额
        System.out.println(LocalDateTime.now()+"当前交易额"+Math.random());
    }
}
