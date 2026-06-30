package com.campus.lostfound.mapper;

import com.campus.lostfound.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    User findByUsername(@Param("username") String username);

    User findById(@Param("id") Integer id);

    int insert(User user);

    int update(User user);

    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    int updatePassword(@Param("id") Integer id, @Param("password") String password);

    List<User> findList(@Param("keyword") String keyword);

    int countAll();
}
