package com.yjc.xiaoxin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjc.xiaoxin.common.dto.DishDto;
import com.yjc.xiaoxin.domain.Dish;

import java.util.List;

/**
* @author DELL
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2023-04-13 09:25:26
*/
public interface DishService extends IService<Dish> {
     void saveDishWithFlavor(DishDto dishDto);

     DishDto getByIdWithFlavor(Long id);

     void updateDishWithFlavor(DishDto dishDto);

     void deleteDishWithFlavor(List<Long> list);
}
