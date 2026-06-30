package com.campus.lostfound.mapper;

import com.campus.lostfound.entity.Notice;

import java.util.List;

public interface NoticeMapper {
    List<Notice> findAll();
    Notice findById(Integer id);
    int insert(Notice notice);
    int update(Notice notice);
    int delete(Integer id);
}
