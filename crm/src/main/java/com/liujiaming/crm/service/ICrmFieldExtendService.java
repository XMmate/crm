package com.liujiaming.crm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.entity.PO.CrmFieldExtend;

import java.util.List;


/**
 * <p>
 * 自定义字段表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2021-03-04
 */
public interface ICrmFieldExtendService extends BaseService<CrmFieldExtend> {

    /**
     * 查询自定义字段扩展表
     * */
    List<CrmFieldExtend> queryCrmFieldExtend(Integer parentFieldId);


    /**
     * 保存或修改自定义字段扩展表
     * */
    boolean saveOrUpdateCrmFieldExtend(List<CrmFieldExtend> crmFieldExtendList, Integer parentFieldId,boolean isUpdate);


    /**
     * 删除或添加自定义字段扩展表
     * */
    boolean deleteCrmFieldExtend(Integer parentFieldId);




}
