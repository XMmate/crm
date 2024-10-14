package com.liujiaming.work.service.impl;

import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.work.entity.PO.WorkUser;
import com.liujiaming.work.mapper.WorkUserMapper;
import com.liujiaming.work.service.IWorkUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目成员表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
@Service
public class WorkUserServiceImpl extends BaseServiceImpl<WorkUserMapper, WorkUser> implements IWorkUserService {

    /**
     * 查询角色ID
     *
     * @param userId 用户ID
     * @param workId 项目ID
     * @return data
     */
    @Override
    public Integer getRoleId(Long userId, Integer workId) {
        WorkUser workUser = lambdaQuery().eq(WorkUser::getUserId, userId).eq(WorkUser::getWorkId, workId).last(" limit 1").one();
        return workUser != null ? workUser.getRoleId() : null;
    }
}
