package com.quanjiawei.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.quanjiawei.constant.RedisConstant;
import com.quanjiawei.dao.SetmealDao;
import com.quanjiawei.entity.PageResult;
import com.quanjiawei.entity.QueryPageBean;
import com.quanjiawei.pojo.Setmeal;
import com.quanjiawei.service.SetmealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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

    //TODO quanjw @Value("${out_put_path}")   Could not resolve placeholder 'out_put_path' in value "${out_put_path}"
    //@Value("${out_put_path}")
    //private String out_put_path;


    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;


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

        generateMobileStaticHtml();
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
        return  this.setmealDao.queryAll();
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

    public void generateMobileStaticHtml() {
        List<Setmeal> setmealList = this.findAll();
        generateMobileSetmealListHtml(setmealList);
        generateMobileSetmealDetailHtml(setmealList);
    }

    public void generateMobileSetmealListHtml(List<Setmeal> setmealList) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("setmealList", setmealList);
        this.generateHtml("mobile_setmeal.ftl","m_setmeal.html",dataMap);
    }

    public void generateMobileSetmealDetailHtml(List<Setmeal> setmealList) {
        for (Setmeal setmeal : setmealList) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("setmeal", this.queryById(setmeal.getId()));
            this.generateHtml("mobile_setmeal_detail.ftl",
                    "setmeal_detail_"+setmeal.getId()+".html",
                    dataMap);
        }
    }

    public void generateHtml(String templateName,String htmlPageName,Map<String,Object> dataMap) {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer out = null;
        try {
            Template template = configuration.getTemplate(templateName);

            Properties props = PropertiesLoaderUtils.loadAllProperties("freemarker.properties");
            String outputpath = props.getProperty("out_put_path");

            File docFile = new File(outputpath + "\\" + htmlPageName);
            //File docFile = new File(out_put_path + "\\" + htmlPageName);

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            template.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}


