package com.yjc.xiaoxin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjc.xiaoxin.domain.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
* @author DELL
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2023-04-13 09:25:26
* @Entity generator.domain.Dish
*/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}




