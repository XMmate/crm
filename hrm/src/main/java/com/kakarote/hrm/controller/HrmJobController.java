package com.kakarote.hrm.controller;

import com.kakarote.hrm.cron.EmployeeChangeCron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hrmJob")
public class HrmJobController {

    @Autowired
    private EmployeeChangeCron employeeChangeCron;


    /**
     * 员工变动记录
     */
    @PostMapping("/employeeChangeRecords")
    public void employeeChangeRecords(){
        //调岗晋升转正
        employeeChangeCron.employeeChangeRecords();
        //离职更变
        employeeChangeCron.employeeQuit();
    }
}
