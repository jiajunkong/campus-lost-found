package com.campus.lostfound.mapper;

import com.campus.lostfound.entity.Category;

import java.util.List;

public interface CategoryMapper {
    List<Category> findAll();
    Category findById(Integer id);
    int insert(Category category);
    int update(Category category);
    int delete(Integer id);
}
