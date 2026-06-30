package com.campus.lostfound.service;

import com.campus.lostfound.entity.Comment;
import com.campus.lostfound.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public List<Comment> findByGoodsId(Integer goodsId) {
        return commentMapper.findByGoodsId(goodsId);
    }

    public boolean add(Comment c) {
        return commentMapper.insert(c) > 0;
    }

    public boolean delete(Integer id) {
        return commentMapper.delete(id) > 0;
    }

    public int countAll() {
        return commentMapper.countAll();
    }
}
