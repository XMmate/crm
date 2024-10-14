package com.liujiaming.admin.service.impl;

import com.liujiaming.admin.entity.PO.LoginLog;
import com.liujiaming.admin.mapper.AdminLoginLogMapper;
import com.liujiaming.admin.service.IAdminLoginLogService;
import com.liujiaming.core.servlet.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统登录日志表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-04-27
 */
@Service
public class AdminLoginLogServiceImpl extends BaseServiceImpl<AdminLoginLogMapper, LoginLog> implements IAdminLoginLogService {

}
