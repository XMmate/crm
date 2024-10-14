package com.liujiaming.hrm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.PO.HrmFieldExtend;

import java.util.List;

/**
 * <p>
 * 自定义字段表 服务类
 * </p>
 *
 * @author guomenghao
 * @since 2021-05-07
 */
public interface IHrmFieldExtendService extends BaseService<HrmFieldExtend> {
    /**
     * 查询自定义字段扩展表
     * */
    List<HrmFieldExtend> queryHrmFieldExtend(Integer parentFieldId);
    /**
     * 保存或修改自定义字段扩展表
     * */
    boolean saveOrUpdateHrmFieldExtend(List<HrmFieldExtend> hrmFieldExtendList, Integer parentFieldId, boolean isUpdate);

    /**
     * 删除或添加自定义字段扩展表
     * */
    boolean deleteHrmFieldExtend(Integer parentFieldId);
}
