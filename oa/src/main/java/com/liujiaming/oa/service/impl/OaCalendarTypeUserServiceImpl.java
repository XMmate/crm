package com.liujiaming.oa.service.impl;

import com.liujiaming.core.common.Result;
import com.liujiaming.core.feign.admin.service.AdminService;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.oa.entity.PO.OaCalendarTypeUser;
import com.liujiaming.oa.mapper.OaCalendarTypeUserMapper;
import com.liujiaming.oa.service.IOaCalendarTypeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户关联日程类型 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
@Service
public class OaCalendarTypeUserServiceImpl extends BaseServiceImpl<OaCalendarTypeUserMapper, OaCalendarTypeUser> implements IOaCalendarTypeUserService {

    @Autowired
    private AdminService adminService;

    @Override
    public void saveSysCalendarType(Integer typeId) {
        Result<List<Long>> listResult = adminService.queryUserList(1);
        List<Long> userIds = listResult.getData();
        List<OaCalendarTypeUser> oaCalendarTypeUsers = new ArrayList<>();
        userIds.forEach(userId->{
            OaCalendarTypeUser oaCalendarTypeUser = new OaCalendarTypeUser();
            oaCalendarTypeUser.setTypeId(typeId);
            oaCalendarTypeUser.setUserId(userId);
            oaCalendarTypeUsers.add(oaCalendarTypeUser);
        });
        saveBatch(oaCalendarTypeUsers);
    }
}
