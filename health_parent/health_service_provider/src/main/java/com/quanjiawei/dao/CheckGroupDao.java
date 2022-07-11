package com.quanjiawei.dao;

import com.quanjiawei.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (CheckGroup)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-11 14:05:45
 */
public interface CheckGroupDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CheckGroup queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param checkGroup 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<CheckGroup> queryAllByLimit(@Param("checkGroup") CheckGroup checkGroup, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param checkGroup 查询条件
     * @return 总行数
     */
    long count(CheckGroup checkGroup);

    /**
     * 新增数据
     *
     * @param checkGroup 实例对象
     * @return 影响行数
     */
    int insert(CheckGroup checkGroup);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<CheckGroup> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<CheckGroup> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<CheckGroup> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<CheckGroup> entities);

    /**
     * 修改数据
     *
     * @param checkGroup 实例对象
     * @return 影响行数
     */
    int update(CheckGroup checkGroup);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

