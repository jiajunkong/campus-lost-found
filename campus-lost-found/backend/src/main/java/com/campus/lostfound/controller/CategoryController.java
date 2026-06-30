package com.campus.lostfound.controller;

import com.campus.lostfound.common.Result;
import com.campus.lostfound.entity.Category;
import com.campus.lostfound.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 物品分类接口
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /** GET /category/list */
    @GetMapping("/list")
    public Result<List<Category>> list() {
        return Result.success(categoryService.findAll());
    }

    /** POST /category/save */
    @PostMapping("/save")
    public Result<Void> save(@RequestBody Category c) {
        return categoryService.save(c) ? Result.success() : Result.error("保存失败");
    }

    /** DELETE /category/delete/{id} */
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        return categoryService.delete(id) ? Result.success() : Result.error("删除失败");
    }
}
