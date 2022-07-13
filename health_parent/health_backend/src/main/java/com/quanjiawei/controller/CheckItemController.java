package com.quanjiawei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.quanjiawei.constant.MessageConstant;
import com.quanjiawei.entity.PageResult;
import com.quanjiawei.entity.QueryPageBean;
import com.quanjiawei.entity.Result;
import com.quanjiawei.pojo.CheckItem;
import com.quanjiawei.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            checkItemService.add(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return  new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = checkItemService.findPage(queryPageBean);
            return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            PageResult pageResult = checkItemService.findAll();
            return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }



    @RequestMapping("/delete")
    public Result add(Integer id){
        try {
            checkItemService.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return  new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }



    @RequestMapping("/findById")
    public Result findById(Integer id){
        CheckItem checkItem;
        try {
            checkItem = checkItemService.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
        return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    @RequestMapping("/findCheckItemIdByCheckGroupId")
    public Result findCheckItemIdByCheckGroupId(Integer checkGroupId){
        try {
            Integer[] ids = checkItemService.findCheckItemIdByCheckGroupId(checkGroupId);
            return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,ids);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            checkItemService.editById(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return  new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
}
