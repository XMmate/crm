package com.liujiaming.admin.common.log;

import com.liujiaming.admin.entity.BO.AdminUserStatusBO;
import com.liujiaming.admin.entity.PO.AdminUser;
import com.liujiaming.admin.service.IAdminUserService;
import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.core.utils.UserCacheUtil;

import java.util.ArrayList;
import java.util.List;

public class AdminUserLog {

    private IAdminUserService adminUserService = ApplicationContextHolder.getBean(IAdminUserService.class);

    public Content usernameEdit(Integer id, String username, String password) {
        AdminUser adminUser = adminUserService.getById(id);
        return new Content(adminUser.getRealname(),"重置账号密码:"+adminUser.getRealname());
    }

    public List<Content> setUserStatus(AdminUserStatusBO adminUserStatusBO) {
        List<Content> contentList = new ArrayList<>();
        String detail;
        if (adminUserStatusBO.getStatus() == 1){
            detail = "启用了";
        }else {
            detail = "禁用了";
        }
        for (Long id : adminUserStatusBO.getIds()) {
            String userName = UserCacheUtil.getUserName(id);
            contentList.add(new Content(userName,detail+userName, BehaviorEnum.UPDATE));
        }
        return contentList;
    }

    public List<Content> resetPassword(AdminUserStatusBO adminUserStatusBO) {
        List<Content> contentList = new ArrayList<>();
        for (Long id : adminUserStatusBO.getIds()) {
            String userName = UserCacheUtil.getUserName(id);
            contentList.add(new Content(userName,"重置了密码:"+userName, BehaviorEnum.UPDATE));
        }
        return contentList;
    }
}
