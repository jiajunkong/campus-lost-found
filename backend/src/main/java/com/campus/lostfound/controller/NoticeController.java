package com.campus.lostfound.controller;

import com.campus.lostfound.common.Result;
import com.campus.lostfound.entity.Notice;
import com.campus.lostfound.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public Result<List<Notice>> list() {
        return Result.success(noticeService.findAll());
    }

    @GetMapping("/detail/{id}")
    public Result<Notice> detail(@PathVariable Integer id) {
        return Result.success(noticeService.findById(id));
    }

    @PostMapping("/save")
    public Result<Void> save(@RequestBody Notice n) {
        return noticeService.save(n) ? Result.success() : Result.error("保存失败");
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        return noticeService.delete(id) ? Result.success() : Result.error("删除失败");
    }
}
