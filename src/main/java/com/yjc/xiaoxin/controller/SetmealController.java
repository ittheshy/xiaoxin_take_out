package com.yjc.xiaoxin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjc.xiaoxin.common.CustomException;
import com.yjc.xiaoxin.common.R;
import com.yjc.xiaoxin.common.dto.SetmealDto;
import com.yjc.xiaoxin.domain.Setmeal;
import com.yjc.xiaoxin.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Resource
    private SetmealService setmealService;

    /**
     * 套餐分页
      * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Setmeal> pageInfo =new Page<>();
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(name != null,Setmeal::getName,name);
        setmealService.page(pageInfo,lqw);
        return R.success(pageInfo);
    }

    /**
     * 添加套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> save(@RequestBody SetmealDto setmealDto){
       setmealService.saveSetmealWithDish(setmealDto);
       return R.success("新增套餐成功");
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> delete(@RequestParam List<Long> ids){
        for(Long id : ids){
            if(setmealService.getById(id).getStatus() == 1){
                throw new CustomException("所选套餐正在售卖，不能删除");
            }
        }
        setmealService.deleteSetmealWithDish(ids);
        return R.success("删除成功");
    }

    /***
     * 启售or停售
     */
    @PostMapping("/status/{status}")
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> modifyStatus(@PathVariable int status,@RequestParam List<Long> ids){
        setmealService.modifyStatus(status,ids);
        return status == 1? R.success("启售成功") : R.success("停售成功");
    }


    /**
     * 获取套餐列表
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    @Cacheable(value = "setmealCache",key = "#setmeal.categoryId +'_' + #setmeal.status" )
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(setmeal != null,Setmeal::getCategoryId,setmeal.getCategoryId())
                .eq(setmeal != null,Setmeal::getStatus,setmeal.getStatus());
        lqw.orderByDesc(Setmeal::getUpdateTime);
        return R.success(setmealService.list(lqw));
    }

    /**
     * 通过id获取套餐信息
     * @param setmealId
     * @return
     */
    @GetMapping("/{setmealId}")
    public R<SetmealDto> getById(@PathVariable Long setmealId){
        return (setmealService.findById(setmealId));
    }

    /**
     * 修改套餐信息
     */
    @PutMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> update(@RequestBody SetmealDto setmealDto){
        setmealService.updateById(setmealDto);
        return R.success("更新成功");
    }
}
