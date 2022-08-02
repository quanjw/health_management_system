package com.quanjiawei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.quanjiawei.constant.MessageConstant;
import com.quanjiawei.constant.RedisMessageConstant;
import com.quanjiawei.entity.Result;
import com.quanjiawei.pojo.Member;
import com.quanjiawei.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @RequestMapping("/login")
    public Result submit(HttpServletResponse response, @RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");

        String codeRedis = jedisPool.getResource().get(telephone+ RedisMessageConstant.SENDTYPE_LOGIN);
        if (validateCode !=null && codeRedis != null && validateCode.equals(codeRedis)){
            Member member = memberService.findByTelephone(telephone);
            if (member == null){
                member = new Member();
                member.setRegTime(new Date());
                member.setPhoneNumber(telephone);
                memberService.add(member);
            }

            Cookie cookie = new Cookie("login_member_telephone",telephone);
            cookie.setPath("/");
            cookie.setMaxAge(30*24*60*60);
            response.addCookie(cookie);

            String json = JSON.toJSONString(member);
            jedisPool.getResource().setex(telephone,60*30,json);
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        }else {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

    }
}
