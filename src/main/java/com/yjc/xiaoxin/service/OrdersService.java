package com.yjc.xiaoxin.service;

import com.yjc.xiaoxin.common.R;
import com.yjc.xiaoxin.domain.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author DELL
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2023-04-18 19:36:42
*/
public interface OrdersService extends IService<Orders> {


    R<String> add(Orders orders);
}
