package com.liujiaming.admin.common.log;

import com.liujiaming.admin.service.IAdminDeptService;
import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.servlet.ApplicationContextHolder;

public class AdminDeptLog {
    private IAdminDeptService adminDeptService = ApplicationContextHolder.getBean(IAdminDeptService.class);

    public Content deleteDept(Integer deptId) {
        String deptName = adminDeptService.getNameByDeptId(deptId);
        return new Content(deptName,"删除了部门:"+deptName, BehaviorEnum.DELETE);
    }
}
