package com.quanjiawei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.quanjiawei.constant.MessageConstant;
import com.quanjiawei.entity.PageResult;
import com.quanjiawei.entity.QueryPageBean;
import com.quanjiawei.entity.Result;
import com.quanjiawei.pojo.CheckGroup;
import com.quanjiawei.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (CheckGroup)表控制层
 *
 * @author makejava
 * @since 2022-07-11 14:05:44
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    /**
     * 服务对象
     */
    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 分页查询
     *
     * @param queryPageBean 筛选条件
     * @return 查询结果
     */
    @RequestMapping("/queryByPage")
    public Result queryByPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = this.checkGroupService.queryByPage(queryPageBean);
            return  new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<CheckGroup> list = this.checkGroupService.findAll();
            return  new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
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

        CheckGroup checkGroup;
        try {
            checkGroup = this.checkGroupService.queryById(id);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
        return  new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    /**
     * 新增数据
     *
     * @param checkGroup 实体
     * @return 新增结果
     */
    @PostMapping
    public Result add(@RequestBody CheckGroup checkGroup ,Integer[] checkitemIds) {
        try {
            CheckGroup insertCheckGroup = this.checkGroupService.insert(checkGroup, checkitemIds);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return  new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 编辑数据
     *
     * @param checkGroup 实体
     * @return 编辑结果
     */
    @PutMapping
    public Result edit(@RequestBody CheckGroup checkGroup ,Integer[] checkitemIds) {
        try {
            this.checkGroupService.update(checkGroup,checkitemIds);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return  new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
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
            checkGroupService.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return  new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("/findCheckGroupIdBySetmealId")
    public Result findCheckItemIdByCheckGroupId(Integer setmealId){
        try {
            Integer[] ids = checkGroupService.findCheckGroupIdBySetmealId(setmealId);
            return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,ids);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

}

