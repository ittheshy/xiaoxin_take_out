package com.yjc.xiaoxin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjc.xiaoxin.domain.Category;

/**
* @author DELL
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2023-04-12 17:19:49
*/
public interface CategoryService extends IService<Category> {
    void deleteById(Long id);
}
