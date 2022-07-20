package com.quanjiawei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.quanjiawei.constant.MessageConstant;
import com.quanjiawei.constant.RedisConstant;
import com.quanjiawei.entity.PageResult;
import com.quanjiawei.entity.QueryPageBean;
import com.quanjiawei.entity.Result;
import com.quanjiawei.pojo.OrderSetting;
import com.quanjiawei.pojo.Setmeal;
import com.quanjiawei.service.OrderSettinglService;
import com.quanjiawei.service.SetmealService;
import com.quanjiawei.utils.POIUtils;
import com.quanjiawei.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * (CheckGroup)表控制层
 *
 * @author makejava
 * @since 2022-07-11 14:05:44
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettinglController {
    /**
     * 服务对象
     */
    @Reference
    private OrderSettinglService orderSettinglService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) {

        try {
            List<String[]> list = POIUtils.readExcel(excelFile);
            ArrayList<OrderSetting> data = new ArrayList<OrderSetting>();
            for (String[] strings : list) {
                SimpleDateFormat formatter = new SimpleDateFormat( "yyyy/MM/dd");
                Date orderDate =  formatter.parse(strings[0]);
                int number = Integer.parseInt(strings[1]);
                OrderSetting orderSetting = new OrderSetting(orderDate,number);
                data.add(orderSetting);
            }
            orderSettinglService.add(data);
            return  new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/getOrdersettingByMonth")
    public Result getOrdersettingByMonth(String date){
        try {
           List<Map> list = orderSettinglService.getOrdersettingByMonth(date);
            return  new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/editOrderSettingByOrderDate")
    public Result editOrderSettingByOrderDate(@RequestBody OrderSetting orderSetting){
        System.out.println(orderSetting.getNumber());
        System.out.println(orderSetting.getOrderDate());

        try {
            orderSettinglService.editOrderSettingByOrderDate(orderSetting);
            return  new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }
}


