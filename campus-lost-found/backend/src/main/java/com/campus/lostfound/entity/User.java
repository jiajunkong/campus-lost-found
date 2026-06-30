package com.campus.lostfound.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 用户实体
 */
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String role;        // user / admin
    private Integer status;     // 1正常 0禁用
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
