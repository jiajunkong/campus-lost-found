package com.campus.lostfound.service;

import com.campus.lostfound.entity.User;
import com.campus.lostfound.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null) return null;
        if (!password.equals(user.getPassword())) return null;
        if (user.getStatus() != null && user.getStatus() == 0) return null;
        return user;
    }

    public boolean register(User user) {
        User exist = userMapper.findByUsername(user.getUsername());
        if (exist != null) return false;
        user.setRole("user");
        user.setStatus(1);
        return userMapper.insert(user) > 0;
    }

    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    public List<User> findList(String keyword) {
        return userMapper.findList(keyword);
    }

    public boolean update(User user) {
        return userMapper.update(user) > 0;
    }

    public boolean updateStatus(Integer id, Integer status) {
        return userMapper.updateStatus(id, status) > 0;
    }

    public boolean updatePassword(Integer id, String oldPwd, String newPwd) {
        User user = userMapper.findById(id);
        if (user == null) return false;
        if (!user.getPassword().equals(oldPwd)) return false;
        return userMapper.updatePassword(id, newPwd) > 0;
    }

    public int countAll() {
        return userMapper.countAll();
    }
}
