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
import com.quanjiawei.service.MemberService;
import com.quanjiawei.utils.DateUtils;
import com.quanjiawei.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;


    @Override
    public Member findByTelephone(String telephone) {
        Member member = memberDao.findByTelephone(telephone);
        return  member;
    }

    @Override
    public void add(Member member) {
        String password = member.getPassword();
        if (password != null){
            member.setPassword(MD5Utils.md5(password));
        }
        memberDao.add(member);
    }
}
