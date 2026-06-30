package com.campus.lostfound.controller;

import com.campus.lostfound.common.Result;
import com.campus.lostfound.entity.User;
import com.campus.lostfound.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户相关接口（注册/登录/个人中心/管理员对用户的管理）
 * 接口前缀：/user/xxx
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * POST /user/login
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody User param) {
        User user = userService.login(param.getUsername(), param.getPassword());
        if (user == null) {
            return Result.error("用户名或密码错误，或账号已禁用");
        }
        user.setPassword(null);
        return Result.success(user);
    }

    /**
     * 用户注册
     * POST /user/register
     */
    @PostMapping("/register")
    public Result<Void> register(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return Result.error("用户名或密码不能为空");
        }
        boolean ok = userService.register(user);
        return ok ? Result.success() : Result.error("用户名已存在");
    }

    /**
     * 根据 id 查询用户
     * GET /user/info/{id}
     */
    @GetMapping("/info/{id}")
    public Result<User> info(@PathVariable Integer id) {
        User user = userService.findById(id);
        if (user != null) user.setPassword(null);
        return Result.success(user);
    }

    /**
     * 修改个人资料
     * PUT /user/update
     */
    @PutMapping("/update")
    public Result<Void> update(@RequestBody User user) {
        return userService.update(user) ? Result.success() : Result.error("修改失败");
    }

    /**
     * 修改密码
     * POST /user/updatePassword
     */
    @PostMapping("/updatePassword")
    public Result<Void> updatePassword(@RequestBody Map<String, Object> param) {
        Integer id = (Integer) param.get("id");
        String oldPwd = (String) param.get("oldPassword");
        String newPwd = (String) param.get("newPassword");
        boolean ok = userService.updatePassword(id, oldPwd, newPwd);
        return ok ? Result.success() : Result.error("原密码错误");
    }

    /**
     * 用户列表（分页，管理员用）
     * GET /user/list?pageNum=1&pageSize=10&keyword=xx
     */
    @GetMapping("/list")
    public Result<Map<String, Object>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam(required = false) String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userService.findList(keyword);
        list.forEach(u -> u.setPassword(null));
        PageInfo<User> page = new PageInfo<>(list);

        Map<String, Object> res = new HashMap<>();
        res.put("list", page.getList());
        res.put("total", page.getTotal());
        return Result.success(res);
    }

    /**
     * 启用/禁用账号
     * POST /user/updateStatus
     */
    @PostMapping("/updateStatus")
    public Result<Void> updateStatus(@RequestBody Map<String, Integer> param) {
        boolean ok = userService.updateStatus(param.get("id"), param.get("status"));
        return ok ? Result.success() : Result.error("操作失败");
    }
}
