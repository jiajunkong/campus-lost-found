package com.campus.lostfound.service;

import com.campus.lostfound.entity.Category;
import com.campus.lostfound.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> findAll() {
        return categoryMapper.findAll();
    }

    public boolean save(Category c) {
        if (c.getId() == null) {
            return categoryMapper.insert(c) > 0;
        } else {
            return categoryMapper.update(c) > 0;
        }
    }

    public boolean delete(Integer id) {
        return categoryMapper.delete(id) > 0;
    }
}
