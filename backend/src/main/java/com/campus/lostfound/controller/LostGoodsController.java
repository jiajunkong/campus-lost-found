package com.campus.lostfound.controller;

import com.campus.lostfound.common.Result;
import com.campus.lostfound.entity.LostGoods;
import com.campus.lostfound.service.LostGoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 失物信息接口
 */
@RestController
@RequestMapping("/goods")
public class LostGoodsController {

    @Autowired
    private LostGoodsService lostGoodsService;

    /**
     * 失物分页列表（前台/后台共用，根据 auditStatus 区分）
     * GET /goods/list?pageNum=1&pageSize=10&type=lost&categoryId=1&keyword=&auditStatus=1&userId=
     */
    @GetMapping("/list")
    public Result<Map<String, Object>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam(required = false) String type,
                                            @RequestParam(required = false) Integer categoryId,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) Integer auditStatus,
                                            @RequestParam(required = false) Integer userId) {
        PageHelper.startPage(pageNum, pageSize);
        List<LostGoods> list = lostGoodsService.findList(type, categoryId, keyword, auditStatus, userId);
        PageInfo<LostGoods> page = new PageInfo<>(list);

        Map<String, Object> res = new HashMap<>();
        res.put("list", page.getList());
        res.put("total", page.getTotal());
        return Result.success(res);
    }

    /** GET /goods/detail/{id} */
    @GetMapping("/detail/{id}")
    public Result<LostGoods> detail(@PathVariable Integer id) {
        return Result.success(lostGoodsService.findById(id));
    }

    /** POST /goods/save - 新增/编辑 */
    @PostMapping("/save")
    public Result<Void> save(@RequestBody LostGoods g) {
        return lostGoodsService.save(g) ? Result.success() : Result.error("保存失败");
    }

    /** POST /goods/audit  - 审核（通过/驳回/下架） */
    @PostMapping("/audit")
    public Result<Void> audit(@RequestBody Map<String, Integer> param) {
        boolean ok = lostGoodsService.updateAuditStatus(param.get("id"), param.get("auditStatus"));
        return ok ? Result.success() : Result.error("操作失败");
    }

    /** DELETE /goods/delete/{id} */
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        return lostGoodsService.delete(id) ? Result.success() : Result.error("删除失败");
    }
}
