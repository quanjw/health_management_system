package com.quanjiawei.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.quanjiawei.constant.RedisConstant;
import com.quanjiawei.dao.SetmealDao;
import com.quanjiawei.entity.PageResult;
import com.quanjiawei.entity.QueryPageBean;
import com.quanjiawei.pojo.CheckGroup;
import com.quanjiawei.pojo.Setmeal;
import com.quanjiawei.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;

/**
 * (CheckGroup)表服务实现类
 *
 * @author makejava
 * @since 2022-07-11 14:05:53
 */

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {



    private SetmealDao setmealDao;

    private JedisPool jedisPool;


    @Autowired
    public void setSetmealDao(SetmealDao setmealDao) {
        this.setmealDao = setmealDao;
    }

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public Setmeal queryById(Integer id) {
        return this.setmealDao.queryById(id);
    }

    public PageResult queryByPage(QueryPageBean queryPageBean) {
        PageRequest pageRequest = PageRequest.of(queryPageBean.getCurrentPage()-1, queryPageBean.getPageSize());
        Setmeal setmeal = new Setmeal();
        setmeal.setName(queryPageBean.getQueryString());
        setmeal.setCode(queryPageBean.getQueryString());
        setmeal.setHelpCode(queryPageBean.getQueryString());
        List<Setmeal> setmeals = this.setmealDao.queryAllByLimit( setmeal, pageRequest);
        long count = this.setmealDao.count(setmeal);
        return new PageResult( count,setmeals);
    }

    public Setmeal insert(Setmeal setmeal, Integer[] checkGroupIds) {
        this.setmealDao.insert(setmeal);
        Integer SetmealId = setmeal.getId();
        setSetmealAndCheckGroupId(checkGroupIds, SetmealId);

        savePic2Redis(setmeal.getImg());
        return  setmeal;
    }

    private void savePic2Redis(String img) {
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, img);
    }

    public Setmeal update(Setmeal setmeal, Integer[] checkGroupIds) {
        this.setmealDao.update(setmeal);

        Integer setmealId = setmeal.getId();
        this.setmealDao.deleteAssoicationOfCheckGroup(setmealId);

        setSetmealAndCheckGroupId(checkGroupIds, setmealId);

        savePic2Redis(setmeal.getImg());
        return this.queryById(setmealId);
    }

    public boolean deleteById(Integer id) {
        this.setmealDao.deleteAssoicationOfCheckGroup(id);
        return this.setmealDao.deleteById(id) > 0;
    }

    public List<Setmeal> findAll() {
        return null;
    }

    private void setSetmealAndCheckGroupId(Integer[] checkGroupIds, Integer SetmealId) {
        if (SetmealId >0 &&  checkGroupIds != null && checkGroupIds.length>0  ){
            for (Integer checkGroupId : checkGroupIds) {
                HashMap<String, Integer> map = new HashMap<String, Integer>();
                map.put("setmealId",SetmealId);
                map.put("checkGroupId",checkGroupId);
                setmealDao.setSetmealAndCheckGroupId(map);
            }

        }
    }
}
