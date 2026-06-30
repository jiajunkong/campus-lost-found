package com.campus.lostfound.controller;

import com.campus.lostfound.common.Result;
import com.campus.lostfound.service.CommentService;
import com.campus.lostfound.service.LostGoodsService;
import com.campus.lostfound.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据统计接口
 */
@RestController
@RequestMapping("/stat")
public class StatController {

    @Autowired
    private UserService userService;
    @Autowired
    private LostGoodsService lostGoodsService;
    @Autowired
    private CommentService commentService;

    /**
     * 概览统计
     * GET /stat/overview
     */
    @GetMapping("/overview")
    public Result<Map<String, Object>> overview() {
        Map<String, Object> res = new HashMap<>();
        res.put("userCount", userService.countAll());
        res.put("goodsCount", lostGoodsService.countAll());
        res.put("commentCount", commentService.countAll());
        res.put("pendingCount", lostGoodsService.countByAuditStatus(0));
        return Result.success(res);
    }

    /** GET /stat/byType - 按类型统计 (lost / found) */
    @GetMapping("/byType")
    public Result<List<Map<String, Object>>> byType() {
        return Result.success(lostGoodsService.countByType());
    }

    /** GET /stat/byCategory - 按分类统计 */
    @GetMapping("/byCategory")
    public Result<List<Map<String, Object>>> byCategory() {
        return Result.success(lostGoodsService.countByCategory());
    }
}
