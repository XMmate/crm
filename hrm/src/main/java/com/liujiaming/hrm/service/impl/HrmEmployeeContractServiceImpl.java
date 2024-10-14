package com.liujiaming.hrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.liujiaming.core.common.Result;
import com.liujiaming.core.feign.admin.service.AdminFileService;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.core.servlet.upload.FileEntity;
import com.liujiaming.core.utils.TransferUtil;
import com.liujiaming.hrm.constant.HrmActionBehaviorEnum;
import com.liujiaming.hrm.constant.LabelGroupEnum;
import com.liujiaming.hrm.entity.PO.HrmEmployeeContract;
import com.liujiaming.hrm.entity.VO.ContractInformationVO;
import com.liujiaming.hrm.mapper.HrmEmployeeContractMapper;
import com.liujiaming.hrm.service.IHrmEmployeeContractService;
import com.liujiaming.hrm.service.actionrecord.impl.EmployeeActionRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 员工合同 服务实现类
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
@Service
public class HrmEmployeeContractServiceImpl extends BaseServiceImpl<HrmEmployeeContractMapper, HrmEmployeeContract> implements IHrmEmployeeContractService {

    @Autowired
    private AdminFileService adminFileService;

    @Resource
    private EmployeeActionRecordServiceImpl employeeActionRecordService;

    @Override
    public List<ContractInformationVO> contractInformation(Integer employeeId) {
        List<HrmEmployeeContract> contractList = lambdaQuery().eq(HrmEmployeeContract::getEmployeeId, employeeId).orderByAsc(HrmEmployeeContract::getSort).list();
        List<ContractInformationVO> contractInformationVOList = TransferUtil.transferList(contractList, ContractInformationVO.class);
        contractInformationVOList.forEach(contractInformationVO -> {
            Result<List<FileEntity>> listResult = adminFileService.queryFileList(contractInformationVO.getBatchId());
            contractInformationVO.setFileList(listResult.getData());
        });
        return contractInformationVOList;
    }

    @Override
    public void addOrUpdateContract(HrmEmployeeContract employeeContract) {
        if (employeeContract.getContractId() == null) {
            employeeActionRecordService.addOrDeleteRecord(HrmActionBehaviorEnum.ADD, LabelGroupEnum.CONTRACT, employeeContract.getEmployeeId());
        } else {
            HrmEmployeeContract old = getById(employeeContract.getContractId());
            employeeActionRecordService.entityUpdateRecord(LabelGroupEnum.CONTRACT, BeanUtil.beanToMap(old), BeanUtil.beanToMap(employeeContract), employeeContract.getEmployeeId());
        }
        saveOrUpdate(employeeContract);
    }

    @Override
    public void deleteContract(Integer contractId) {
        HrmEmployeeContract contract = getById(contractId);
        employeeActionRecordService.addOrDeleteRecord(HrmActionBehaviorEnum.DELETE, LabelGroupEnum.CONTRACT, contract.getEmployeeId());
        removeById(contractId);
    }

    @Override
    public List<Integer> queryToExpireContractCount() {
        return getBaseMapper().queryToExpireContractCount();
    }
}
