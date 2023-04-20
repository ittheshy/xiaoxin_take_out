package com.yjc.xiaoxin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjc.xiaoxin.domain.Category;
import org.apache.ibatis.annotations.Mapper;

/**
* @author DELL
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2023-04-12 17:19:49
* @Entity generator.domain.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




