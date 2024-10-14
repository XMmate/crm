package com.liujiaming.admin.service;

import com.liujiaming.admin.entity.BO.QuerySysLogBO;
import com.liujiaming.admin.entity.PO.LoginLog;
import com.liujiaming.admin.entity.PO.SysLog;
import com.liujiaming.core.entity.BasePage;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author hmb
 * @since 2024-11-25
 */
public interface ISysLogService {

    void saveSysLog(SysLog sysLog);

    void saveLoginLog(LoginLog loginLog);

    BasePage<SysLog> querySysLogPageList(QuerySysLogBO querySysLogBO);

    /**
     * 查询登录日志列表
     * @param querySysLogBO
     * @return
     */
    BasePage<LoginLog> queryLoginLogPageList(QuerySysLogBO querySysLogBO);
}
