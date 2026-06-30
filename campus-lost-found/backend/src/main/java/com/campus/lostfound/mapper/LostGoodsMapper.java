package com.campus.lostfound.mapper;

import com.campus.lostfound.entity.LostGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LostGoodsMapper {

    /**
     * 多条件分页查询
     */
    List<LostGoods> findList(@Param("type") String type,
                             @Param("categoryId") Integer categoryId,
                             @Param("keyword") String keyword,
                             @Param("auditStatus") Integer auditStatus,
                             @Param("userId") Integer userId);

    LostGoods findById(@Param("id") Integer id);

    int insert(LostGoods lostGoods);

    int update(LostGoods lostGoods);

    int updateAuditStatus(@Param("id") Integer id, @Param("auditStatus") Integer auditStatus);

    int delete(@Param("id") Integer id);

    /**
     * 统计：按类型统计数量
     */
    List<Map<String, Object>> countByType();

    /**
     * 统计：按分类统计数量
     */
    List<Map<String, Object>> countByCategory();

    int countAll();

    int countByAuditStatus(@Param("auditStatus") Integer auditStatus);
}
