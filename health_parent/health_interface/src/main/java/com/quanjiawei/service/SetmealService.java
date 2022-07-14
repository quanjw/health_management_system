package com.quanjiawei.service;

import com.quanjiawei.entity.PageResult;
import com.quanjiawei.entity.QueryPageBean;
import com.quanjiawei.pojo.Setmeal;

import java.util.List;

/**
 * (Setmeal)表服务接口
 *
 * @author makejava
 * @since 2022-07-11 14:05:51
 */
public interface SetmealService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Setmeal queryById(Integer id);

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
     * @param setmeal 实例对象
     * @param checkGroupIds
     * @return 实例对象
     */
    Setmeal insert(Setmeal setmeal, Integer[] checkGroupIds);

    /**
     * 修改数据
     *
     * @param setmeal 实例对象
     * @param checkGroupIds 实例对象
     * @return 实例对象
     */
    Setmeal update(Setmeal setmeal,Integer[] checkGroupIds);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<Setmeal> findAll();

}
