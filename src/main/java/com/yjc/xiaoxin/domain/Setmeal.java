package com.yjc.xiaoxin.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 套餐
 * @TableName setmeal
 */
@TableName(value ="setmeal")
@Data
@ApiModel("套餐")
public class Setmeal implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId
    private Long id;

    /**
     * 菜品分类id
     */
    @ApiModelProperty("菜品id")
    private Long categoryId;

    /**
     * 套餐名称
     */
    @ApiModelProperty("套餐名称")
    private String name;

    /**
     * 套餐价格
     */
    @ApiModelProperty("套餐价格")
    private BigDecimal price;

    /**
     * 状态 0:停用 1:启用
     */
    private Integer status;

    /**
     * 编码
     */
    private String code;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 图片
     */
    private String image;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}