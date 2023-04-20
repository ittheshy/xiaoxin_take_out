package com.yjc.xiaoxin.mapper;

import com.yjc.xiaoxin.domain.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author DELL
* @description 针对表【shopping_cart(购物车)】的数据库操作Mapper
* @createDate 2023-04-18 16:21:10
* @Entity com.yjc.xiaoxin.domain.ShoppingCart
*/
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}




