package com.quanjiawei.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.quanjiawei.dao.OrderSettingDao;
import com.quanjiawei.pojo.OrderSetting;
import com.quanjiawei.service.OrderSettinglService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = OrderSettinglService.class)
@Transactional
public class OrderSettinglServiceImpl implements OrderSettinglService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    public void add(List<OrderSetting> list) {
        for (OrderSetting orderSetting : list) {
            if (orderSettingDao.countNumberByOrderDate(orderSetting) > 0){
                //update
                orderSettingDao.updateByOrderDate(orderSetting);
            }else {
                //add
                orderSettingDao.insert(orderSetting);
            }
        }
    }

    public List<Map> getOrdersettingByMonth(String date) {
        String begin = date+"-"+1;
        String end = date+"-"+31;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("begin",begin);
        map.put("end",end);
        List<OrderSetting> list = orderSettingDao.getOrdersettingByMonth(map);

        ArrayList<Map> arrayList = new ArrayList<Map>();
        if (list != null && list.size()>0) {
            for (OrderSetting orderSetting : list) {
                // { date: 1, number: 120, reservations: 1 },
                HashMap<String, Object> tempMap = new HashMap<String,Object>();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(orderSetting.getOrderDate());
                tempMap.put("date",calendar.get(Calendar.DATE));

                tempMap.put("number",orderSetting.getNumber());
                tempMap.put("reservations",orderSetting.getReservations());

                arrayList.add(tempMap);
            }
        }
        return arrayList;
    }
}
