package com.liujiaming.crm.mapper;

import cn.hutool.core.lang.Dict;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.crm.entity.PO.CrmActionRecord;
import com.liujiaming.crm.entity.VO.CrmActionRecordVO;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字段操作记录表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-25
 */
public interface CrmActionRecordMapper extends BaseMapper<CrmActionRecord> {

    /**
     * 查询字段操作记录列表
     * @param actionId 类型
     * @param types type
     * @return data
     */
    public List<CrmActionRecordVO> queryRecordList(@Param("actionId") Integer actionId, @Param("types") Integer types);

    List<CrmModelFiledVO> queryFieldValue(@Param("data") Dict kv);
}
