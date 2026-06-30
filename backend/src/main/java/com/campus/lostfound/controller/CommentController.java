package com.campus.lostfound.controller;

import com.campus.lostfound.common.Result;
import com.campus.lostfound.entity.Comment;
import com.campus.lostfound.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 留言接口
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /** GET /comment/list/{goodsId} */
    @GetMapping("/list/{goodsId}")
    public Result<List<Comment>> list(@PathVariable Integer goodsId) {
        return Result.success(commentService.findByGoodsId(goodsId));
    }

    /** POST /comment/add */
    @PostMapping("/add")
    public Result<Void> add(@RequestBody Comment c) {
        return commentService.add(c) ? Result.success() : Result.error("留言失败");
    }

    /** DELETE /comment/delete/{id} */
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        return commentService.delete(id) ? Result.success() : Result.error("删除失败");
    }
}
