package com.yjc.xiaoxin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjc.xiaoxin.domain.OrderDetail;
import com.yjc.xiaoxin.service.OrderDetailService;
import com.yjc.xiaoxin.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author DELL
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2023-04-18 19:37:32
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




