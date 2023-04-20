package com.yjc.xiaoxin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjc.xiaoxin.common.CustomException;
import com.yjc.xiaoxin.common.R;
import com.yjc.xiaoxin.common.dto.DishDto;
import com.yjc.xiaoxin.domain.Category;
import com.yjc.xiaoxin.domain.Dish;
import com.yjc.xiaoxin.domain.DishFlavor;
import com.yjc.xiaoxin.service.CategoryService;
import com.yjc.xiaoxin.service.DishFlavorService;
import com.yjc.xiaoxin.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Resource
    private DishService dishService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private DishFlavorService flavorService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 添加菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info("DishDto = {}",dishDto.toString());
        dishService.saveDishWithFlavor(dishDto);
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
        return R.success("保存菜品成功");
    }

    /**
     * 查询菜品列表
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) throws InvocationTargetException {
        //构造分页构造器
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>(page,pageSize);
        //构造查询构造器
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.like(name != null,Dish::getName,name);
        dishService.page(pageInfo,lqw);
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<DishDto> list = pageInfo.getRecords().stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if(category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    /**
     * 根据id查询dish
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getDishById(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 修改菜品信息
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateDishWithFlavor(dishDto);
        //清理已修改那类菜品的缓存
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
        return R.success("修改成功");
    }

    /**
     * 删除单个菜品信息
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        dishService.deleteDishWithFlavor(ids);
        return R.success("删除成功");
    }

    /**
     * 禁用或启用
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> modifyStatus(@PathVariable int status,String ids){
        List<Long> list = new ArrayList<>();
        String id[] = ids.split(",");
        for(String t : id){
            Long dishId = Long.parseLong(t);
            Dish byId = dishService.getById(dishId);
            if(byId.getStatus() == status) throw new CustomException("所选菜品已" + (status == 1? "启售":"停售"));
            byId.setStatus(status);
            dishService.updateById(byId);
        }
        return R.success("修改成功");
    }

    /**
     * 根据套餐id查询菜品数据
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Long categoryId,Integer status) {
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        List<DishDto> list1;
        //动态构造key，以便存入redis中
       String key = "dish_" + categoryId + "_" +  status;
       list1 = (List<DishDto>) redisTemplate.opsForValue().get(key);
       if(list1 != null){
           return R.success(list1);
       }
        lqw.eq(categoryId != null,Dish::getCategoryId, categoryId).eq(Dish::getStatus,status);
        lqw.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(lqw);
        list1 = list.stream().map((item) ->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            List<DishFlavor> flavors = new ArrayList<>();
            LambdaQueryWrapper<DishFlavor> lqw1 = new LambdaQueryWrapper<>();
            lqw1.eq(DishFlavor::getDishId,item.getId());
            dishDto.setFlavors(flavorService.list(lqw1));
            return dishDto;
        }).collect(Collectors.toList());
        redisTemplate.opsForValue().set(key,list1,60, TimeUnit.MINUTES);
        return R.success(list1);
    }
}
