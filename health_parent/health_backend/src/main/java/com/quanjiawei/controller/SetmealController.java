package com.quanjiawei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.quanjiawei.constant.MessageConstant;
import com.quanjiawei.constant.RedisConstant;
import com.quanjiawei.entity.PageResult;
import com.quanjiawei.entity.QueryPageBean;
import com.quanjiawei.entity.Result;
import com.quanjiawei.pojo.Setmeal;
import com.quanjiawei.service.SetmealService;
import com.quanjiawei.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * (CheckGroup)表控制层
 *
 * @author makejava
 * @since 2022-07-11 14:05:44
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    /**
     * 服务对象
     */
    @Reference
    private SetmealService setmealService;


    private JedisPool jedisPool;

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 分页查询
     *
     * @param queryPageBean 筛选条件
     * @return 查询结果
     */
    @RequestMapping("/queryByPage")
    public Result queryByPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = this.setmealService.queryByPage(queryPageBean);
            return  new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<Setmeal> list = this.setmealService.findAll();
            return  new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result queryById(@PathVariable("id") Integer id) {

        try {
            Setmeal  setmeal = this.setmealService.queryById(id);
            return  new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }

    }

    /**
     * 新增数据
     *
     * @param setmeal 实体
     * @param checkGroupIds 实体
     * @return 新增结果
     */
    @PostMapping
    public Result add(@RequestBody Setmeal setmeal ,Integer[] checkGroupIds) {
        try {
            Setmeal insertSetmeal = this.setmealService.insert(setmeal, checkGroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return  new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 编辑数据
     *
     * @param setmeal 实体
     * @param checkGroupIds 实体
     * @return 编辑结果
     */
    @PutMapping
    public Result edit(@RequestBody Setmeal setmeal ,Integer[] checkGroupIds) {
        try {
            this.setmealService.update(setmeal,checkGroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
        return  new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public Result deleteById(Integer id) {
        System.out.println(id);
        try {
            setmealService.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
        return  new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        String originalFilename = imgFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String extension = originalFilename.substring(index - 1);
        String fileName = UUID.randomUUID().toString()+extension;
        try {
            //上传七牛云
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //存入redis
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return  new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }
}

