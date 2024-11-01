package com.liujiaming.examine.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.examine.entity.PO.ExamineManagerUser;

import java.util.List;

/**
 * <p>
 * 审批管理员设置表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-11-13
 */
public interface IExamineManagerUserService extends BaseService<ExamineManagerUser> {

    /**
     * 通过审批ID查询审批管理员
     * @param examineId 审批ID
     * @return data
     */
    public List<Long> queryExamineUserByPage(Long examineId);


    public List<Long> queryExamineUser(Long examineId);
}
