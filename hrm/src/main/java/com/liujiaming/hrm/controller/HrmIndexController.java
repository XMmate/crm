package com.liujiaming.hrm.controller;

import com.liujiaming.core.common.Result;
import com.liujiaming.hrm.entity.PO.HrmEmployee;
import com.liujiaming.hrm.service.IHrmEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Api(tags = "index")
@Slf4j
public class HrmIndexController {

    @Autowired
    private IHrmEmployeeService employeeService;


    @PostMapping("/hrm/queryEmployeeListByIds")
    @ApiOperation("通过员工和部门ids查询员工")
    public Result<Set<HrmEmployee>> queryEmployeeListByIds(@RequestBody List<Integer> employeeIds){
        List<HrmEmployee> employees = employeeService.query().select("employee_id", "employee_name", "mobile", "email", "sex", "post")
                    .eq("is_del",0)
                    .in("employee_id",employeeIds).list();
        Set<HrmEmployee> list = new HashSet<>(employees);
        return Result.ok(list);
    }


}
