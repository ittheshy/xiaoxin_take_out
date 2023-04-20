package com.yjc.xiaoxin.mapper;

import com.yjc.xiaoxin.domain.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author DELL
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2023-04-18 19:36:42
* @Entity com.yjc.xiaoxin.domain.Orders
*/
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}




