package com.campus.lostfound.entity;

import lombok.Data;

/**
 * 物品分类
 */
@Data
public class Category {
    private Integer id;
    private String name;
    private Integer sort;
}
