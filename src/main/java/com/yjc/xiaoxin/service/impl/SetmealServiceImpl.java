package com.yjc.xiaoxin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjc.xiaoxin.common.CustomException;
import com.yjc.xiaoxin.common.dto.SetmealDto;
import com.yjc.xiaoxin.domain.Setmeal;
import com.yjc.xiaoxin.domain.SetmealDish;
import com.yjc.xiaoxin.service.SetmealDishService;
import com.yjc.xiaoxin.service.SetmealService;
import com.yjc.xiaoxin.mapper.SetmealMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author DELL
* @description 针对表【setmeal(套餐)】的数据库操作Service实现
* @createDate 2023-04-13 09:33:16
*/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService{

    @Resource
    private SetmealDishService setmealDishService;

    @Transactional
    /**
     * 保存套餐及菜品信息
     */
    @Override
    public void saveSetmealWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        List<SetmealDish> list = setmealDishes.stream().map((item) ->{
            item.setSetmealId(setmealDto.getId().toString());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(list);
    }

    /**
     * 删除套餐及菜品信息
     * @param list
     */
    @Override
    public void deleteSetmealWithDish(List<Long> list) {
        this.removeByIds(list);
        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        for(Long id :list){
            lqw.eq(SetmealDish::getSetmealId,id);
            setmealDishService.remove(lqw);
        }
    }

    /**
     * 启售or停售套餐
     * @param status
     * @param list
     */
    @Override
    public void modifyStatus(int status, List<Long> list) {
        if(list == null) throw new CustomException("请选择待操作的套餐");
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.in(Setmeal::getId,list).eq(Setmeal::getStatus,status);
        int count = this.count(lqw);
        if(count > 0){
            throw new CustomException("所选菜品已" + (status == 1 ? "启售" : "停售"));
        }
        List<Setmeal> list1 = this.listByIds(list).stream().map((item) -> {
            item.setStatus(status);
            return item;
        }).collect(Collectors.toList());
        this.updateBatchById(list1);
    }
}




