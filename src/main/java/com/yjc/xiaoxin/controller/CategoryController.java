package com.yjc.xiaoxin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjc.xiaoxin.common.R;
import com.yjc.xiaoxin.domain.Category;
import com.yjc.xiaoxin.domain.Dish;
import com.yjc.xiaoxin.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 添加分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("Category = {}",category);
        categoryService.save(category);
        return R.success("分类添加成功");
    }

    /**
     * 分类分页
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> pageInfo(int page,int pageSize){
        log.info("page = {},pageSize = {}",page,pageSize);
        //分页构造器
        Page<Category> page1 = new Page<>(page,pageSize);
        //条件构造器，这里用于通过sort字段排序
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.orderByAsc(Category::getSort);
        categoryService.page(page1,lqw);
        return R.success(page1);
    }

    /**
     * 删除分类
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("删除分类,id为{}",ids);
        categoryService.deleteById(ids);
        return R.success("删除分类成功");
    }

    /**
     * 修改分类信息
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("待修改的分类信息为{}",category);
        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

    /**
     *查询分类列表
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> getList(Category category) {
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.eq(category.getType() != null, Category::getType, category.getType());
        lqw.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(lqw);
        return R.success(list);
    }
}
