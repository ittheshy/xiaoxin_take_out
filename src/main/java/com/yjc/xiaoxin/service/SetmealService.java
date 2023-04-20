package com.yjc.xiaoxin.service;

import com.yjc.xiaoxin.common.dto.SetmealDto;
import com.yjc.xiaoxin.domain.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author DELL
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2023-04-13 09:33:16
*/
public interface SetmealService extends IService<Setmeal> {

    void saveSetmealWithDish(SetmealDto setmealDto);

    void deleteSetmealWithDish(List<Long> list);

    void modifyStatus(int status,List<Long> list);

}
