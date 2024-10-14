package com.liujiaming.crm.service.impl;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.entity.PageEntity;
import com.liujiaming.core.exception.CrmException;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.core.utils.UserUtil;
import com.liujiaming.crm.constant.CrmCodeEnum;
import com.liujiaming.crm.entity.PO.CrmMarketingForm;
import com.liujiaming.crm.mapper.CrmMarketingFormMapper;
import com.liujiaming.crm.service.ICrmMarketingFormService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author liujiaming
 * @date 2024/12/2
 */
@Service
public class CrmMarketingFormServiceImpl extends BaseServiceImpl<CrmMarketingFormMapper, CrmMarketingForm> implements ICrmMarketingFormService {


    @Override
    public CrmMarketingForm saveOrUpdateCrmMarketingForm(CrmMarketingForm crmMarketingForm) {
        Integer id  = crmMarketingForm.getId();
        if (Objects.isNull(id)){
            crmMarketingForm.setUpdateUserId(UserUtil.getUserId());
            crmMarketingForm.setUpdateTime(new Date());
            this.save(crmMarketingForm);
            return crmMarketingForm;
        }else {
            CrmMarketingForm oldCrmMarketingForm = this.getById(id);
            if (Objects.isNull(oldCrmMarketingForm)){
                throw new CrmException(CrmCodeEnum.CRM_ACTIVITY_FORM_NONENTITY_ERROR);
            }
            oldCrmMarketingForm.setTitle(crmMarketingForm.getTitle());
            oldCrmMarketingForm.setUpdateUserId(UserUtil.getUserId());
            oldCrmMarketingForm.setUpdateTime(new Date());
            this.updateById(oldCrmMarketingForm);
            return oldCrmMarketingForm;
        }
    }

    @Override
    public BasePage<CrmMarketingForm> queryCrmMarketingFormList(PageEntity pageEntity) {
        return lambdaQuery().eq(CrmMarketingForm::getIsDeleted,0).orderByAsc(CrmMarketingForm::getCreateTime)
                .page(pageEntity.parse());
    }

    @Override
    public List<CrmMarketingForm> queryAllCrmMarketingFormList() {
        return lambdaQuery().eq(CrmMarketingForm::getIsDeleted,0).eq(CrmMarketingForm::getStatus,1).orderByAsc(CrmMarketingForm::getCreateTime).list();
    }

    @Override
    public void deleteCrmMarketingForm(Integer id) {
        lambdaUpdate().set(CrmMarketingForm::getIsDeleted, 1).set(CrmMarketingForm::getDeleteUserId, UserUtil.getUserId()).set(CrmMarketingForm::getDeleteTime, new Date())
                .eq(CrmMarketingForm::getId, id).update();
    }

    @Override
    public void updateStatus(CrmMarketingForm crmMarketingForm) {
        lambdaUpdate().set(CrmMarketingForm::getStatus,crmMarketingForm.getStatus()).eq(CrmMarketingForm::getId,crmMarketingForm.getId()).update();
    }

}
