package com.liujiaming.admin.service;

import com.liujiaming.admin.entity.PO.AdminUserRole;
import com.liujiaming.core.servlet.BaseService;

import java.util.List;

/**
 * <p>
 * 用户角色对应关系表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-04-27
 */
public interface IAdminUserRoleService extends BaseService<AdminUserRole> {

    /**
     * 通过userID删除该用户的所有
     * @param userId 用户ID
     * @param isRemove 是否删除原有角色
     * @param roleId 角色列表
     */
    public void saveByUserId(Long userId, boolean isRemove, List<String> roleId);
}
