package com.quanjiawei.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.quanjiawei.dao.CheckGroupDao;
import com.quanjiawei.entity.PageResult;
import com.quanjiawei.entity.QueryPageBean;
import com.quanjiawei.pojo.CheckGroup;
import com.quanjiawei.service.CheckGroupService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (CheckGroup)表服务实现类
 *
 * @author makejava
 * @since 2022-07-11 14:05:53
 */

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public CheckGroup queryById(Integer id) {
        return this.checkGroupDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param queryPageBean 筛选条件
     * @return 查询结果
     */
    public PageResult queryByPage(QueryPageBean queryPageBean) {
        PageRequest pageRequest = PageRequest.of(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        CheckGroup checkGroup = new CheckGroup();
        checkGroup.setName(queryPageBean.getQueryString());
        List<CheckGroup> checkGroups = this.checkGroupDao.queryAllByLimit( checkGroup, pageRequest);
        long count = this.checkGroupDao.count(checkGroup);
        return new PageResult( count,checkGroups);
    }

    /**
     * 新增数据
     *
     * @param checkGroup 实例对象
     * @return 实例对象
     */
    public CheckGroup insert(CheckGroup checkGroup) {
        this.checkGroupDao.insert(checkGroup);
        return checkGroup;
    }

    /**
     * 修改数据
     *
     * @param checkGroup 实例对象
     * @return 实例对象
     */
    public CheckGroup update(CheckGroup checkGroup) {
        this.checkGroupDao.update(checkGroup);
        return this.queryById(checkGroup.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer id) {
        return this.checkGroupDao.deleteById(id) > 0;
    }
}
