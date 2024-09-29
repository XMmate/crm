package com.kakarote.oa.service;

import com.kakarote.core.servlet.BaseService;
import com.kakarote.oa.entity.PO.OaCalendarTypeUser;

/**
 * <p>
 * 用户关联日历类型 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface IOaCalendarTypeUserService extends BaseService<OaCalendarTypeUser> {

    void saveSysCalendarType(Integer typeId);
}
