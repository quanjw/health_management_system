package com.quanjiawei.service;

import com.quanjiawei.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettinglService {
    void add(List<OrderSetting> list);

    List<Map> getOrdersettingByMonth(String date);
}
