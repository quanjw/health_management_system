package com.quanjiawei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.quanjiawei.constant.MessageConstant;
import com.quanjiawei.constant.RedisMessageConstant;
import com.quanjiawei.entity.Result;
import com.quanjiawei.pojo.Order;
import com.quanjiawei.service.OrderService;
import com.quanjiawei.utils.SMSUtils;
import com.quanjiawei.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    private JedisPool jedisPool;
    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }


    @Reference
    private OrderService orderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) {

            String telephone = (String) map.get("telephone");
            String validateCode = (String) map.get("validateCode");
            String codeRedis = jedisPool.getResource().get(telephone+ RedisMessageConstant.SENDTYPE_ORDER);

            if (validateCode != null && validateCode.equals(codeRedis)){
                map.put("orderType",Order.ORDERTYPE_WEIXIN);
                Result result = null;
                try {
                    result = orderService.order(map);
                }catch (Exception e){
                    e.printStackTrace();
                    return  new Result(false, MessageConstant.ORDER_FAIL);
                }

                if (result.isFlag()){
                    HashMap<String, Integer> hashMap = new HashMap<>();
                    Integer orderCode = ValidateCodeUtils.generateValidateCode(6);
                    hashMap.put("code",orderCode);
                    String param = JSON.toJSONString(map);
                    try {
                        SMSUtils.sendShortMessage(SMSUtils.getOrderNotice(),telephone, param);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return  result;
                //return  new Result(true, MessageConstant.ORDER_SUCCESS);
            }else {
                return  new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }

    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Map map;
        try {
            map = orderService.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
        return  new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,map);
    }



}
