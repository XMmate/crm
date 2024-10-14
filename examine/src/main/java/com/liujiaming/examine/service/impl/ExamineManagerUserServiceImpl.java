package com.liujiaming.examine.service.impl;

import com.liujiaming.core.feign.admin.service.AdminService;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.core.utils.UserUtil;
import com.liujiaming.examine.entity.PO.ExamineManagerUser;
import com.liujiaming.examine.mapper.ExamineManagerUserMapper;
import com.liujiaming.examine.service.IExamineManagerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 审批管理员设置表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-11-13
 */
@Service
public class ExamineManagerUserServiceImpl extends BaseServiceImpl<ExamineManagerUserMapper, ExamineManagerUser> implements IExamineManagerUserService {

    @Autowired
    private AdminService adminService;

    /**
     * 通过审批ID查询审批管理员
     *
     * @param examineId 审批ID
     * @return data
     */
    @Override
    public List<Long> queryExamineUserByPage(Long examineId) {
        List<ExamineManagerUser> managerUsers = lambdaQuery().eq(ExamineManagerUser::getExamineId, examineId).orderByAsc(ExamineManagerUser::getSort).list();
        return managerUsers.stream().map(ExamineManagerUser::getUserId).collect(Collectors.toList());
    }


    @Override
    public List<Long> queryExamineUser(Long examineId) {
        List<ExamineManagerUser> managerUsers = lambdaQuery().eq(ExamineManagerUser::getExamineId, examineId).orderByAsc(ExamineManagerUser::getSort).list();
        List<Long> userIds = managerUsers.stream().map(ExamineManagerUser::getUserId).collect(Collectors.toList());
        List<Long> userIdList = adminService.queryNormalUserByIds(userIds).getData();
        //流程管理员被禁用时 转交给超管
        if (userIdList.size() == 0) {
            userIdList.add(UserUtil.getSuperUser());
        }
        return userIdList;
    }
}
