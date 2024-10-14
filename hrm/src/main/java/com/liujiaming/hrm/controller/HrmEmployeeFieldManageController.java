package com.liujiaming.hrm.controller;


import com.liujiaming.core.common.Result;
import com.liujiaming.hrm.entity.BO.QueryEmployFieldManageBO;
import com.liujiaming.hrm.entity.VO.EmployeeFieldManageVO;
import com.liujiaming.hrm.service.IHrmEmployeeFieldManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 自定义字段管理表 前端控制器
 * </p>
 *
 * @author guomenghao
 * @since 2021-04-14
 */
@RestController
@RequestMapping("/hrmEmployeeFieldManage")
@Api(tags = "员工管理-管理员工自定义字段")
public class HrmEmployeeFieldManageController {
    @Autowired
    private IHrmEmployeeFieldManageService employeeFieldManageService;

    @PostMapping("/queryEmployeeManageField")
    @ApiOperation("查询管理可设置入职员工（个人信息，岗位信息）字段列表")
    public Result<List<EmployeeFieldManageVO>> queryEmployeeManageField(QueryEmployFieldManageBO queryEmployFieldManageBO){

        List<EmployeeFieldManageVO> manageFields = employeeFieldManageService.queryEmployeeManageField(queryEmployFieldManageBO);
        return Result.ok(manageFields);
    }

    /**
     * 新建在职员工个人信息/岗位信息字段设置
     * 设置这些入职的时候需要填写的字段
     * @param manageFields
     * @return
     */
    @PostMapping("/setEmployeeManageField")
    @ApiOperation("新建在职员工个人信息/岗位信息字段设置")
    public Result setEmployeeManageField(@RequestBody List<EmployeeFieldManageVO> manageFields){
        employeeFieldManageService.setEmployeeManageField(manageFields);
        return Result.ok();
    }

}

