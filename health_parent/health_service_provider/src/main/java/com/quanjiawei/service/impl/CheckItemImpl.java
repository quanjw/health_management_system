package com.quanjiawei.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.quanjiawei.dao.CheckItemDao;
import com.quanjiawei.entity.PageResult;
import com.quanjiawei.entity.QueryPageBean;
import com.quanjiawei.pojo.CheckItem;
import com.quanjiawei.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sun.tools.jconsole.JConsole;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page =  checkItemDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> list = page.getResult();
        return new PageResult( total, list);

    }
}
