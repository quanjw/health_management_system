package com.quanjiawei.service;

import com.quanjiawei.entity.PageResult;
import com.quanjiawei.entity.QueryPageBean;
import com.quanjiawei.pojo.CheckGroup;

/**
 * (CheckGroup)表服务接口
 *
 * @author makejava
 * @since 2022-07-11 14:05:51
 */
public interface CheckGroupService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CheckGroup queryById(Integer id);

    /**
     * 分页查询
     *
     * @param queryPageBean      分页对象
     * @return 查询结果
     */
    PageResult queryByPage(QueryPageBean queryPageBean);

    /**
     * 新增数据
     *
     * @param checkGroup 实例对象
     * @param checkitemIds
     * @return 实例对象
     */
    CheckGroup insert(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 修改数据
     *
     * @param checkGroup 实例对象
     * @return 实例对象
     */
    CheckGroup update(CheckGroup checkGroup,Integer[] checkitemIds);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
