package com.quanjiawei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.quanjiawei.constant.MessageConstant;
import com.quanjiawei.entity.Result;
import com.quanjiawei.pojo.Setmeal;
import com.quanjiawei.service.SetmealService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;


    @RequestMapping("/getSetmeal")
    public Result findAll() {
        try {
            List<Setmeal> list = this.setmealService.findAll();
            return  new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    @PostMapping("/findById")
    public Result findById(Integer id){
        try {
            Setmeal  setmeal = this.setmealService.queryById(id);
            return  new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }



}
