package com.yjc.xiaoxin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yjc.xiaoxin.common.BaseContext;
import com.yjc.xiaoxin.common.CustomException;
import com.yjc.xiaoxin.common.R;
import com.yjc.xiaoxin.domain.ShoppingCart;
import com.yjc.xiaoxin.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Resource
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        //给当前购物车赋userId
        shoppingCart.setUserId(BaseContext.getId());
        //判断当前购物车是否已经在数据库中
        //若是，则直接number + 1
        LambdaQueryWrapper<ShoppingCart> lqw =new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,shoppingCart.getUserId());
        Long id = shoppingCart.getDishId();
        if(id != null){
            lqw.eq(ShoppingCart::getDishId,id);
        }else{
            lqw.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart one = shoppingCartService.getOne(lqw);
        if(one != null){
            int number = one.getNumber();
            one.setNumber(number + 1);
            one.setCreateTime(LocalDateTime.now());
            shoppingCartService.updateById(one);
        }
        //若不是，则将购物车加到数据库中
        else{
            one = shoppingCart;
            one.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(one);
        }
        return R.success(one);
    }

    /**
     * 查找购物车列表
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        log.info("查看购物车");
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,BaseContext.getId());
        lqw.orderByDesc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(lqw);
        return R.success(list);
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean(){
        log.info("清空购物车");
        shoppingCartService.removeById(BaseContext.getId());
        return R.success("删除购物车成功");
    }


    @PostMapping("/sub")
    public R<String> subSc(@RequestBody Map<String,Long> map){
        log.info("减少套餐或菜品数量");
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        if(map.get("setmealId") == null){
            Long dishId = map.get("dishId");
            lqw.eq(ShoppingCart::getDishId,dishId).eq(ShoppingCart::getUserId,BaseContext.getId());
        }else{
            Long setmealId = map.get("setmealId");
            lqw.eq(ShoppingCart::getSetmealId,setmealId).eq(ShoppingCart::getUserId,BaseContext.getId());
        }
        ShoppingCart shoppingCart = shoppingCartService.getOne(lqw);
        if(shoppingCart.getNumber() <= 0) throw new CustomException("该餐品数量已为0");
        Integer number = shoppingCart.getNumber();
        shoppingCart.setNumber(number - 1);
        shoppingCartService.updateById(shoppingCart);
        return R.success("修改成功");
    }
}
