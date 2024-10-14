package com.liujiaming.hrm.common.log;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.hrm.entity.BO.AddDeptBO;
import com.liujiaming.hrm.entity.PO.HrmDept;
import com.liujiaming.hrm.service.IHrmDeptService;

import java.util.List;

public class HrmDeptLog {

    private SysLogUtil sysLogUtil = ApplicationContextHolder.getBean(SysLogUtil.class);

    private IHrmDeptService deptService = ApplicationContextHolder.getBean(IHrmDeptService.class);

    public Content setDept(AddDeptBO addDeptBO){
        HrmDept oldDept = deptService.getById(addDeptBO.getDeptId());
        List<String> list = sysLogUtil.updateRecord(BeanUtil.beanToMap(oldDept), BeanUtil.beanToMap(addDeptBO), "dept");
        return new Content(addDeptBO.getName(), CollUtil.join(list,","), BehaviorEnum.UPDATE);
    }

    public Content deleteDeptById(String deptId){
        HrmDept dept = deptService.getById(deptId);
        return new Content(dept.getName(), "删除了部门:"+dept.getName());
    }
}
