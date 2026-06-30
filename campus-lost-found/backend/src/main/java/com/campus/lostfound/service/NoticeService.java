package com.campus.lostfound.service;

import com.campus.lostfound.entity.Notice;
import com.campus.lostfound.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    public List<Notice> findAll() {
        return noticeMapper.findAll();
    }

    public Notice findById(Integer id) {
        return noticeMapper.findById(id);
    }

    public boolean save(Notice n) {
        if (n.getId() == null) {
            return noticeMapper.insert(n) > 0;
        } else {
            return noticeMapper.update(n) > 0;
        }
    }

    public boolean delete(Integer id) {
        return noticeMapper.delete(id) > 0;
    }
}
