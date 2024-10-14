package com.liujiaming.hrm.service;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.PO.HrmConfig;

import java.util.List;

/**
 * <p>
 * 人力资源配置表 服务类
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-13
 */
public interface IHrmConfigService extends BaseService<HrmConfig> {

    void addOrUpdate(HrmConfig hrmConfig);

    List<HrmConfig> queryListByType(Integer type);
}
