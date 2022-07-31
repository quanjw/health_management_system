package com.quanjiawei.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.quanjiawei.constant.MessageConstant;
import com.quanjiawei.dao.MemberDao;
import com.quanjiawei.dao.OrderDao;
import com.quanjiawei.dao.OrderSettingDao;
import com.quanjiawei.entity.Result;
import com.quanjiawei.pojo.Member;
import com.quanjiawei.pojo.Order;
import com.quanjiawei.pojo.OrderSetting;
import com.quanjiawei.service.OrderService;
import com.quanjiawei.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Result order(Map map) {
        String orderDate = (String) map.get("orderDate");
        String telephone = (String) map.get("telephone");
        String setmealId = (String) map.get("setmealId");
        String name = (String) map.get("name");
        String idCard = (String) map.get("idCard");
        String sex = (String) map.get("sex");
        try {
            Date date = DateUtils.parseString2Date(orderDate);
            OrderSetting orderSetting = orderSettingDao.findByOrderDate(date);
            System.out.println(orderSetting);
            if (orderSetting == null){
                return  new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
            }
            if (orderSetting.getNumber()<= orderSetting.getReservations()){
                return  new Result(false, MessageConstant.ORDER_FULL);
            }

            Member member = memberDao.findByTelephone(telephone);
            if (member != null){
                Integer memberId = member.getId();
                Order order = new Order(memberId, date, Integer.parseInt(setmealId));
                List<Order> orderList = orderDao.findByCondition(order);
                if (orderList != null && orderList.size()>0){
                    return  new Result(false, MessageConstant.HAS_ORDERED);
                }

            }else {
                //auto register member
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setName(name);
                member.setIdCard(idCard);
                member.setSex(sex);
                member.setRegTime(new Date());
                memberDao.add(member);
            }

            Order order = new Order();
            order.setOrderDate(date);
            order.setMemberId(member.getId());
            order.setSetmealId(Integer.parseInt(setmealId));
            order.setOrderType(Order.ORDERTYPE_WEIXIN);
            order.setOrderStatus(Order.ORDERSTATUS_NO);
            orderDao.add(order);

            orderSetting.setReservations(orderSetting.getReservations()+1);
            orderSettingDao.update(orderSetting);
            return  new Result(true, MessageConstant.ORDER_SUCCESS,order);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.ORDER_FAIL);
        }

    }
}
