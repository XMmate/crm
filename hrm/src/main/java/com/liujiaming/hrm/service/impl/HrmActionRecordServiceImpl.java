package com.liujiaming.hrm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import com.alibaba.fastjson.JSON;
import com.liujiaming.core.common.Result;
import com.liujiaming.core.entity.UserInfo;
import com.liujiaming.core.feign.admin.service.AdminService;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.core.utils.TransferUtil;
import com.liujiaming.hrm.constant.HrmActionBehaviorEnum;
import com.liujiaming.hrm.constant.HrmActionTypeEnum;
import com.liujiaming.hrm.entity.BO.QueryRecordListBO;
import com.liujiaming.hrm.entity.PO.HrmActionRecord;
import com.liujiaming.hrm.entity.VO.HrmModelFiledVO;
import com.liujiaming.hrm.entity.VO.QueryRecordListVO;
import com.liujiaming.hrm.mapper.HrmActionRecordMapper;
import com.liujiaming.hrm.service.IHrmActionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * hrm员工操作记录表 服务实现类
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
@Service
public class HrmActionRecordServiceImpl extends BaseServiceImpl<HrmActionRecordMapper, HrmActionRecord> implements IHrmActionRecordService {


    @Autowired
    private  AdminService adminService;

    @Override
    public boolean saveRecord(HrmActionTypeEnum actionTypeEnum, HrmActionBehaviorEnum behaviorEnum, List<String> content, Integer typeId) {
        if (CollUtil.isEmpty(content)){
            return true;
        }
        HrmActionRecord hrmActionRecord = new HrmActionRecord();
        hrmActionRecord.setIpAddress("100.75.29.25");
        hrmActionRecord.setType(actionTypeEnum.getValue());
        hrmActionRecord.setBehavior(behaviorEnum.getValue());
        hrmActionRecord.setTypeId(typeId);
        hrmActionRecord.setContent(JSON.toJSONString(content));
        return save(hrmActionRecord);
    }

    @Override
    public List<QueryRecordListVO> queryRecordList(QueryRecordListBO queryRecordListBO) {
        List<HrmActionRecord> list = lambdaQuery().eq(HrmActionRecord::getType, queryRecordListBO.getType())
                .eq(HrmActionRecord::getTypeId, queryRecordListBO.getTypeId())
                .orderByDesc(HrmActionRecord::getCreateTime).list();
        List<QueryRecordListVO> recordListVOS = TransferUtil.transferList(list, QueryRecordListVO.class);
        recordListVOS.forEach(record->{
            Result<UserInfo> userInfoResult = adminService.getUserInfo(record.getCreateUserId());
            UserInfo userInfo = userInfoResult.getData();
            record.setRealname(userInfo.getRealname());
            record.setImg(userInfo.getImg());
        });
        return recordListVOS;
    }

    @Override
    public List<HrmModelFiledVO> queryFieldValue(Dict kv) {
        return baseMapper.queryFieldValue(kv);
    }
}
