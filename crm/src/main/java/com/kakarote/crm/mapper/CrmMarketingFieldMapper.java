package com.kakarote.crm.mapper;

import com.kakarote.core.servlet.BaseMapper;
import com.kakarote.crm.entity.PO.CrmMarketingField;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liujiaming
 * @date 2024/12/2
 */
public interface CrmMarketingFieldMapper extends BaseMapper<CrmMarketingField> {

    void deleteByChooseId(@Param("ids") List<Integer> arr, @Param("formId") Integer formId);
}
