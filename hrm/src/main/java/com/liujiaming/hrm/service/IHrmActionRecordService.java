package com.liujiaming.hrm.service;

import cn.hutool.core.lang.Dict;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.constant.HrmActionBehaviorEnum;
import com.liujiaming.hrm.constant.HrmActionTypeEnum;
import com.liujiaming.hrm.entity.BO.QueryRecordListBO;
import com.liujiaming.hrm.entity.PO.HrmActionRecord;
import com.liujiaming.hrm.entity.VO.HrmModelFiledVO;
import com.liujiaming.hrm.entity.VO.QueryRecordListVO;

import java.util.List;

/**
 * <p>
 * hrm员工操作记录表 服务类
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
public interface IHrmActionRecordService extends BaseService<HrmActionRecord> {
    /**
     * 保存操作记录
     * @param actionTypeEnum  操作对象类型
     * @param behaviorEnum  行为类型
     * @param content   内容
     * @param typeId    类型id
     * @return
     */
    boolean saveRecord(HrmActionTypeEnum actionTypeEnum, HrmActionBehaviorEnum behaviorEnum, List<String> content, Integer typeId);


    /**
     * 查询操作记录列表
     * @param queryRecordListBO
     * @return
     */
    List<QueryRecordListVO> queryRecordList(QueryRecordListBO queryRecordListBO);

    /**
     * 通过字典值查询hrm需要的自定义字段
     * @param kv 字典值
     * @return
     */
    List<HrmModelFiledVO> queryFieldValue(Dict kv);
}
