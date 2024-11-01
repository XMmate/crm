package com.liujiaming.admin.service.impl;

import com.liujiaming.admin.common.AdminCodeEnum;
import com.liujiaming.admin.entity.PO.AdminConfig;
import com.liujiaming.admin.entity.PO.AdminUser;
import com.liujiaming.admin.entity.PO.AdminUserHisTable;
import com.liujiaming.admin.mapper.AdminUserHisTableMapper;
import com.liujiaming.admin.service.IAdminConfigService;
import com.liujiaming.admin.service.IAdminUserHisTableService;
import com.liujiaming.admin.service.IAdminUserService;
import com.liujiaming.core.common.SystemCodeEnum;
import com.liujiaming.core.exception.CrmException;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.core.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 授权坐席 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-04-27
 */
@Service
public class AdminUserHisTableServiceImpl extends BaseServiceImpl<AdminUserHisTableMapper, AdminUserHisTable> implements IAdminUserHisTableService {

    @Autowired
    private IAdminConfigService adminConfigService;
    @Autowired
    private IAdminUserService adminUserService;

    /**
     * 员工坐席授权
     */
    @Override
    public boolean authorize(List<Long> userIds, Integer status,Integer hisUse){
        AdminConfig adminConfig = adminConfigService.lambdaQuery()
                .eq(AdminConfig::getName,"call").last(" limit 1").one();
        if(adminConfig == null || adminConfig.getStatus() != 1){
            throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
        }
        if (userIds.size() == 0 ){
            throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
        }
        AdminUserHisTable userHisTable;
        for (Long userId: userIds) {
            userHisTable = this.lambdaQuery()
                    .eq(AdminUserHisTable::getUserId,userId).last(" limit 1").one();
            if (userHisTable == null){
                userHisTable = new AdminUserHisTable();
            }
            if(hisUse!=null && hisUse == 2){
                userHisTable.setType(2);
            }else {
                userHisTable.setType(1);
            }
            userHisTable.setUserId(userId);
            userHisTable.setHisTable(status);
            if (userHisTable.getHisTableId() == null && status == 1){
                this.save(userHisTable);
            }else if (userHisTable.getHisTableId() != null){
                this.updateById(userHisTable);
            }
        }
        return true;
    }


    /**
     * 判断用户是否为坐席
     * @return
     */
    @Override
    public Integer checkAuth(){
        Long userId= UserUtil.getUserId();
        AdminUser adminUser = adminUserService.getById(userId);
        if(adminUser==null){
            throw new CrmException(AdminCodeEnum.ADMIN_USER_NOT_EXIST_ERROR);
        }
        AdminUserHisTable userHisTable = this.lambdaQuery()
                .eq(AdminUserHisTable::getUserId,userId).last(" limit 1").one();
        if(userHisTable == null){
            return null;
        }
        if(userHisTable.getHisTable() == 0){
            return null;
        }
        return userHisTable.getType();
    }
}
