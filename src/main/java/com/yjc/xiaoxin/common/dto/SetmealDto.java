package com.yjc.xiaoxin.common.dto;

import com.yjc.xiaoxin.domain.Setmeal;
import com.yjc.xiaoxin.domain.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
