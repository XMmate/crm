package com.liujiaming.oa.mapper;

import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.oa.entity.PO.OaExamineField;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 自定义字段表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-06-22
 */
public interface OaExamineFieldMapper extends BaseMapper<OaExamineField> {

    void deleteByChooseId(@Param("ids") List<Integer> arr,@Param("categoryId") Integer categoryId);

    void deleteByFieldValue(@Param("ids") List<Integer> arr,@Param("categoryId") Integer categoryId);
}
