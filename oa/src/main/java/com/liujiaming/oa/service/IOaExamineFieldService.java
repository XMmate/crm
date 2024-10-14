package com.liujiaming.oa.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.oa.entity.BO.ExamineFieldBO;
import com.liujiaming.oa.entity.PO.OaExamineField;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 自定义字段表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-06-22
 */
public interface IOaExamineFieldService extends BaseService<OaExamineField> {

    List<OaExamineField> queryField(Integer id);

    List<List<OaExamineField>> queryFormPositionField(Integer id);

    Boolean updateFieldCategoryId(Long newCategoryId,Long oldCategoryId);

    /**
     * 根据batchId查询values
     * @param batchId batchId
     * @return valuesMap
     */
    public Map<Integer,String> queryFieldData(String batchId);

    void transferFieldList(List<OaExamineField> recordList, Integer isDetail);

    void saveField(ExamineFieldBO examineFieldBO);

    void saveDefaultField(Long categoryId);
}
