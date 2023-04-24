package com.yjc.xiaoxin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjc.xiaoxin.common.BaseContext;
import com.yjc.xiaoxin.common.R;
import com.yjc.xiaoxin.domain.Orders;
import com.yjc.xiaoxin.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrdersService ordersService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        ordersService.add(orders);
        return R.success("提交成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String number){
        Page page1 = new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<Orders>();
        lqw.eq(number != null,Orders::getNumber,number);
        lqw.orderByDesc(Orders::getOrderTime);
        ordersService.page(page1,lqw);
        return R.success(page1);
    }

    @GetMapping("/userPage")
    public R<Page> userPage(int page,int pageSize){
        Page page1 = new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
        Long id = BaseContext.getId();
        lqw.eq(Orders::getUserId,id);
        lqw.orderByDesc(Orders::getOrderTime);
        ordersService.page(page1,lqw);
        return R.success(page1);
    }
}
