package com.quanjiawei.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.quanjiawei.dao.CheckGroupDao;
import com.quanjiawei.entity.PageResult;
import com.quanjiawei.entity.QueryPageBean;
import com.quanjiawei.pojo.CheckGroup;
import com.quanjiawei.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
        PageRequest pageRequest = PageRequest.of(queryPageBean.getCurrentPage()-1, queryPageBean.getPageSize());
        CheckGroup checkGroup = new CheckGroup();
        checkGroup.setName(queryPageBean.getQueryString());
        checkGroup.setCode(queryPageBean.getQueryString());
        checkGroup.setHelpcode(queryPageBean.getQueryString());
        List<CheckGroup> checkGroups = this.checkGroupDao.queryAllByLimit( checkGroup, pageRequest);
        long count = this.checkGroupDao.count(checkGroup);
        return new PageResult( count,checkGroups);
    }

    /**
     * 新增数据
     *
     * @param checkGroup 实例对象
     * @param checkitemIds
     * @return 实例对象
     */
    public CheckGroup insert(CheckGroup checkGroup, Integer[] checkitemIds) {
        this.checkGroupDao.insert(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        setCheckGroupIdAndCheckItemID(checkitemIds, checkGroupId);

        return checkGroup;
    }

    /**
     * 修改数据
     *
     * @param checkGroup 实例对象
     * @return 实例对象
     */
    public CheckGroup update(CheckGroup checkGroup,Integer[] checkitemIds) {
        this.checkGroupDao.update(checkGroup);

        Integer checkGroupId = checkGroup.getId();
        this.checkGroupDao.deleteAssoicationOfCheckItem(checkGroupId);

        setCheckGroupIdAndCheckItemID(checkitemIds, checkGroupId);
        return this.queryById(checkGroupId);
    }

    private void setCheckGroupIdAndCheckItemID(Integer[] checkitemIds, Integer checkGroupId) {
        if (checkGroupId >0 &&  checkitemIds != null && checkitemIds.length>0  ){
            for (Integer checkitemId : checkitemIds) {
                HashMap<String, Integer> map = new HashMap<String, Integer>();
                map.put("checkgroupId",checkGroupId);
                map.put("checkitemId",checkitemId);
                checkGroupDao.setCheckGroupIdAndCheckItemId(map);
            }

        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer id) {
        this.checkGroupDao.deleteAssoicationOfCheckItem(id);
        return this.checkGroupDao.deleteById(id) > 0;
    }
}
