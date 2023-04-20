package com.yjc.xiaoxin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjc.xiaoxin.common.CustomException;
import com.yjc.xiaoxin.domain.Category;
import com.yjc.xiaoxin.domain.Dish;
import com.yjc.xiaoxin.mapper.CategoryMapper;
import com.yjc.xiaoxin.service.CategoryService;
import com.yjc.xiaoxin.service.DishService;
import com.yjc.xiaoxin.service.SetmealService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author DELL
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2023-04-12 17:19:49
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService {

    @Resource
    private DishService dishService;

    @Resource
    private SetmealService setmealService;

    /**
     * 通过id删除分类
     * @param id
     */
    public void deleteById(Long id){
        LambdaQueryWrapper<Dish> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(lqw1);
        if(count1 > 0){
           throw new CustomException("当前分类已经关联了菜品,不能删除");
        }
        LambdaQueryWrapper<Dish> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(Dish::getCategoryId,id);
        int count2= dishService.count(lqw2);
        if(count2 > 0){
           throw new CustomException("当前分类已经关联了套餐，不能删除");
        }
        this.removeById(id);
    }

}




