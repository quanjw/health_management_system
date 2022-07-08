package com.quanjiawei.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.quanjiawei.dao.CheckItemDao;
import com.quanjiawei.pojo.CheckItem;
import com.quanjiawei.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }
}
