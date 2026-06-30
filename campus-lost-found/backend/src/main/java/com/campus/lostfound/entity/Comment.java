package com.campus.lostfound.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 留言
 */
@Data
public class Comment {
    private Integer id;
    private Integer goodsId;
    private String content;
    private Integer userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    // 关联字段
    private String nickname;
    private String username;
}
