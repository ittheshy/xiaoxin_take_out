package com.yjc.xiaoxin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjc.xiaoxin.common.BaseContext;
import com.yjc.xiaoxin.common.CustomException;
import com.yjc.xiaoxin.common.R;
import com.yjc.xiaoxin.domain.*;
import com.yjc.xiaoxin.service.*;
import com.yjc.xiaoxin.mapper.OrdersMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
* @author DELL
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2023-04-18 19:36:42
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

    @Resource
    private ShoppingCartService shoppingCartService;

    @Resource
    private UserService userService;

    @Resource
    private AddressBookService addressBookService;

    @Resource
    private OrderDetailService orderDetailService;

    /**
     * 保存订单
     * @param orders
     * @return
     */
    public R<String> add(Orders orders){
        //查询购物车信息
        Long userId = BaseContext.getId();
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> list = shoppingCartService.list(lqw);
        if(list == null || list.size() == 0){
            throw new CustomException("购物车为空，不能下单");
        }
        //查询用户信息
        User user = userService.getById(userId);
        //查询地址信息
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if(addressBook == null){
            throw new CustomException("用户地址有误，不能下单");
        }
        //保存一条订单信息
        long id = IdWorker.getId(); //订单号

        AtomicInteger amount = new AtomicInteger(0);
        List<OrderDetail> orderDetails = list.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(id);
            BeanUtils.copyProperties(item,orderDetail);
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());
        orders.setNumber(String.valueOf(id));
        orders.setUserId(userId);
        orders.setAddressBookId(addressBookId);
        orders.setAddress((addressBook.getProvinceName() == null ? "":addressBook.getProvinceName())
        + (addressBook.getCityName() == null ? "":addressBook.getCityName())
        + (addressBook.getDistrictName() == null ? "":addressBook.getDistrictName())
        + (addressBook.getDetail() == null ? "":addressBook.getDetail()));
        orders.setOrderTime(LocalDateTime.now());
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setConsignee(addressBook.getConsignee());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setPhone(addressBook.getPhone());
        this.save(orders);
        //保存多条订单明细信息
        orderDetailService.saveBatch(orderDetails);
        //清空购物车
        shoppingCartService.remove(lqw);
        return R.success("保存订单成功");
    }

}




