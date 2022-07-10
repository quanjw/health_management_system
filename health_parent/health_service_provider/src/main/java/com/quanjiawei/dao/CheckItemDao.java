package com.quanjiawei.dao;

import com.github.pagehelper.Page;
import com.quanjiawei.pojo.CheckItem;

public interface CheckItemDao {
    public void add(CheckItem checkItem);

    public Page<CheckItem> selectByCondition(String queryString);

    public void deleteById(Integer id);

    public long findCountCheckItemById(Integer id);

    public void editById(CheckItem checkItem);

    public CheckItem findById(Integer id);
}