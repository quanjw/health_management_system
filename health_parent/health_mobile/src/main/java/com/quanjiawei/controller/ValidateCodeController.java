package com.quanjiawei.controller;

import com.alibaba.fastjson.JSON;
import com.quanjiawei.constant.MessageConstant;
import com.quanjiawei.constant.RedisMessageConstant;
import com.quanjiawei.entity.Result;
import com.quanjiawei.utils.SMSUtils;
import com.quanjiawei.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    private JedisPool jedisPool;

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){

        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("code",validateCode);
        String param = JSON.toJSONString(map);
        try {
            SMSUtils.sendShortMessage(SMSUtils.getValidateCode(),telephone, param);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,300,validateCode.toString());
        return  new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){

        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("code",validateCode);
        String param = JSON.toJSONString(map);
        try {
            SMSUtils.sendShortMessage(SMSUtils.getValidateCode(),telephone, param);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_LOGIN,300,validateCode.toString());
        return  new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }



}
