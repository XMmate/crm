package com.liujiaming.hrm.service.impl;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.hrm.entity.PO.HrmConfig;
import com.liujiaming.hrm.mapper.HrmConfigMapper;
import com.liujiaming.hrm.service.IHrmConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 人力资源配置表 服务实现类
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-13
 */
@Service
public class HrmConfigServiceImpl extends BaseServiceImpl<HrmConfigMapper, HrmConfig> implements IHrmConfigService {
    @Override
    public void addOrUpdate(HrmConfig hrmConfig) {
        saveOrUpdate(hrmConfig);
    }

    @Override
    public List<HrmConfig> queryListByType(Integer type) {
        return lambdaQuery().eq(HrmConfig::getType,type).orderByAsc(HrmConfig::getCreateTime).list();
    }
}
