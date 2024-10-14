package com.liujiaming.admin.service;

import com.liujiaming.admin.entity.PO.AdminRoleMenu;
import com.liujiaming.core.servlet.BaseService;

import java.util.List;

/**
 * <p>
 * 角色菜单对应关系表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-04-27
 */
public interface IAdminRoleMenuService extends BaseService<AdminRoleMenu> {
    public void saveRoleMenu(Integer roleId, List<Integer> menuIdList);
}
