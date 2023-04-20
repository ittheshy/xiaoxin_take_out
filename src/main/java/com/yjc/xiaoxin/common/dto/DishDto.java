package com.yjc.xiaoxin.common.dto;

import com.yjc.xiaoxin.domain.Dish;
import com.yjc.xiaoxin.domain.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
