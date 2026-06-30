package com.campus.lostfound.mapper;

import com.campus.lostfound.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    List<Comment> findByGoodsId(@Param("goodsId") Integer goodsId);
    int insert(Comment comment);
    int delete(@Param("id") Integer id);
    int countAll();
}
