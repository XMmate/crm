package com.liujiaming.crm.service;

import cn.hutool.core.lang.Dict;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.constant.CrmEnum;
import com.liujiaming.crm.entity.PO.CrmActionRecord;
import com.liujiaming.crm.entity.VO.CrmActionRecordVO;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;

import java.util.List;

/**
 * <p>
 * 字段操作记录表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-25
 */
public interface ICrmActionRecordService extends BaseService<CrmActionRecord> {

    /**
     * 删除字段记录类型
     *
     * @param crmEnum 类型
     * @param ids     ids
     */
    public void deleteActionRecord(CrmEnum crmEnum, List<Integer> ids);

    /**
     * 查询自定义欢迎语
     *
     * @return data
     */
    public List<String> queryRecordOptions();

    /**
     * 查询字段操作记录列表
     *
     * @param actionId 类型
     * @param types    type
     * @return data
     */
    public List<CrmActionRecordVO> queryRecordList(Integer actionId, Integer types);

    List<CrmModelFiledVO> queryFieldValue(Dict kv);
}
