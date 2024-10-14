package com.liujiaming.oa.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.oa.entity.PO.OaCalendarTypeUser;

/**
 * <p>
 * 用户关联日程类型 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface IOaCalendarTypeUserService extends BaseService<OaCalendarTypeUser> {

    void saveSysCalendarType(Integer typeId);
}
