package com.liujiaming.admin.common.log;

import com.liujiaming.admin.entity.PO.AdminRole;
import com.liujiaming.admin.service.IAdminRoleService;
import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.servlet.ApplicationContextHolder;

public class AdminRoleLog {
    private IAdminRoleService adminRoleService = ApplicationContextHolder.getBean(IAdminRoleService.class);

    public Content delete(Integer roleId) {
        AdminRole adminRole = adminRoleService.getById(roleId);
        return new Content(adminRole.getRoleName(),"删除了角色:"+adminRole.getRoleName(), BehaviorEnum.DELETE);
    }

    public Content copy(Integer roleId) {
        AdminRole adminRole = adminRoleService.getById(roleId);
        return new Content(adminRole.getRoleName(),"复制了角色:"+adminRole.getRoleName(), BehaviorEnum.COPY);
    }

    public Content deleteWorkRole(Integer roleId) {
        AdminRole adminRole = adminRoleService.getById(roleId);
        return new Content(adminRole.getRoleName(),"删除了项目角色:"+adminRole.getRoleName(), BehaviorEnum.DELETE);
    }
}
