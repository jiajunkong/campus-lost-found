package com.campus.lostfound.service;

import com.campus.lostfound.entity.LostGoods;
import com.campus.lostfound.mapper.LostGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LostGoodsService {

    @Autowired
    private LostGoodsMapper lostGoodsMapper;

    public List<LostGoods> findList(String type, Integer categoryId, String keyword,
                                    Integer auditStatus, Integer userId) {
        return lostGoodsMapper.findList(type, categoryId, keyword, auditStatus, userId);
    }

    public LostGoods findById(Integer id) {
        return lostGoodsMapper.findById(id);
    }

    public boolean save(LostGoods g) {
        if (g.getId() == null) {
            // 新发布默认待审核
            g.setAuditStatus(0);
            return lostGoodsMapper.insert(g) > 0;
        } else {
            return lostGoodsMapper.update(g) > 0;
        }
    }

    public boolean updateAuditStatus(Integer id, Integer status) {
        return lostGoodsMapper.updateAuditStatus(id, status) > 0;
    }

    public boolean delete(Integer id) {
        return lostGoodsMapper.delete(id) > 0;
    }

    public List<Map<String, Object>> countByType() {
        return lostGoodsMapper.countByType();
    }

    public List<Map<String, Object>> countByCategory() {
        return lostGoodsMapper.countByCategory();
    }

    public int countAll() {
        return lostGoodsMapper.countAll();
    }

    public int countByAuditStatus(Integer auditStatus) {
        return lostGoodsMapper.countByAuditStatus(auditStatus);
    }
}
