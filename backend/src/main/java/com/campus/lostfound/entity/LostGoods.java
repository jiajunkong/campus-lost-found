package com.campus.lostfound.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 失物信息（核心实体）
 */
@Data
public class LostGoods {
    private Integer id;
    private String title;
    private String image;          // 物品图片
    private Integer categoryId;
    private String place;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lostTime;
    private String description;
    private String type;           // lost / found
    private Integer userId;
    private Integer auditStatus;   // 0待审核 1通过 2驳回 3下架
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    // 关联字段（连表查询返回）
    private String categoryName;
    private String username;
    private String nickname;
}
