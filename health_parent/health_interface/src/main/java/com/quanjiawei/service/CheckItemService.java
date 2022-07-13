package com.quanjiawei.service;

import com.quanjiawei.entity.PageResult;
import com.quanjiawei.entity.QueryPageBean;
import com.quanjiawei.pojo.CheckItem;

public interface CheckItemService {

    public void add(CheckItem checkItem);

    public PageResult findPage(QueryPageBean queryPageBean);

    public void deleteById(Integer id);

    public void editById(CheckItem checkItem);

    public CheckItem findById(Integer id);

    public PageResult findAll();

    public Integer[] findCheckItemIdByCheckGroupId(Integer checkGroupId);
}
