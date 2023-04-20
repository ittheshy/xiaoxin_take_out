package com.yjc.xiaoxin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjc.xiaoxin.common.CustomException;
import com.yjc.xiaoxin.common.dto.DishDto;
import com.yjc.xiaoxin.domain.Dish;
import com.yjc.xiaoxin.domain.DishFlavor;
import com.yjc.xiaoxin.mapper.DishMapper;
import com.yjc.xiaoxin.service.DishFlavorService;
import com.yjc.xiaoxin.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author DELL
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2023-04-13 09:25:26
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService {

    @Resource
    private DishFlavorService dishFlavorService;


    /**
     * 保存菜品和菜品口味信息
     * @param dishDto
     */
    @Transactional
    @Override
    public void saveDishWithFlavor(DishDto dishDto) {
        //添加菜品信息
        this.save(dishDto);
        //添加菜品口味信息
        Long id = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(id); return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 根据菜品Id查询菜品和菜品口味信息
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(this.getById(id),dishDto);
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId,id);
        List<DishFlavor> list = dishFlavorService.list(lqw);
        dishDto.setFlavors(list);
        return dishDto;
    }

    /**
     * 修改菜品和菜品口味信息
     * @param dishDto
     */
    @Override
    public void updateDishWithFlavor(DishDto dishDto) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDto,dish);
        this.updateById(dish);
        List<DishFlavor> flavors = dishDto.getFlavors();
        for(DishFlavor t : flavors){
            t.setDishId(dishDto.getId());
        }
        dishFlavorService.updateBatchById(flavors);
    }

    @Override
    public void deleteDishWithFlavor(List<Long> list) {
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.in(Dish::getId,list).eq(Dish::getStatus,1);
        int count = this.count(lqw);
        if(count > 0) throw new CustomException("所选菜品正在售卖，不能删除");
        this.removeByIds(list);
        LambdaQueryWrapper<DishFlavor> lqw1 = new LambdaQueryWrapper<>();
        lqw1.in(DishFlavor::getDishId,list);
        dishFlavorService.remove(lqw1);
    }
}




