package com.quanjiawei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.quanjiawei.entity.PageResult;
import com.quanjiawei.entity.QueryPageBean;
import com.quanjiawei.pojo.CheckGroup;
import com.quanjiawei.service.CheckGroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
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
    public PageResult queryByPage(@RequestBody QueryPageBean queryPageBean) {
        CheckGroup checkGroup = new CheckGroup();
        //checkGroup.setName(queryPageBean.getQueryString());
        PageRequest pageRequest = PageRequest.of(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        return this.checkGroupService.queryByPage(queryPageBean);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<CheckGroup> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.checkGroupService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param checkGroup 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<CheckGroup> add(CheckGroup checkGroup) {
        return ResponseEntity.ok(this.checkGroupService.insert(checkGroup));
    }

    /**
     * 编辑数据
     *
     * @param checkGroup 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<CheckGroup> edit(CheckGroup checkGroup) {
        return ResponseEntity.ok(this.checkGroupService.update(checkGroup));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.checkGroupService.deleteById(id));
    }

}

