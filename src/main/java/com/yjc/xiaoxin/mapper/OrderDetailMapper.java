package com.yjc.xiaoxin.mapper;

import com.yjc.xiaoxin.domain.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author DELL
* @description 针对表【order_detail(订单明细表)】的数据库操作Mapper
* @createDate 2023-04-18 19:37:32
* @Entity com.yjc.xiaoxin.domain.OrderDetail
*/
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}




