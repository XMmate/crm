package com.liujiaming.hrm.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.liujiaming.core.common.Result;
import com.liujiaming.core.common.SubModelType;
import com.liujiaming.core.common.SystemCodeEnum;
import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.SysLog;
import com.liujiaming.core.common.log.SysLogHandler;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.entity.PageEntity;
import com.liujiaming.core.exception.CrmException;
import com.liujiaming.hrm.common.log.HrmConfigLog;
import com.liujiaming.hrm.constant.ConfigType;
import com.liujiaming.hrm.entity.BO.AddEmployeeFieldBO;
import com.liujiaming.hrm.entity.BO.AddInsuranceSchemeBO;
import com.liujiaming.hrm.entity.BO.DeleteRecruitChannelBO;
import com.liujiaming.hrm.entity.BO.SetAchievementTableBO;
import com.liujiaming.hrm.entity.PO.*;
import com.liujiaming.hrm.entity.VO.AchievementTableVO;
import com.liujiaming.hrm.entity.VO.FiledListVO;
import com.liujiaming.hrm.entity.VO.InsuranceSchemeListVO;
import com.liujiaming.hrm.entity.VO.InsuranceSchemeVO;
import com.liujiaming.hrm.service.*;
import com.liujiaming.hrm.entity.PO.*;
import com.liujiaming.hrm.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hrmConfig")
@Api(tags = "人力资源后台配置接口（业务参数设置模块那里）")
@SysLog(logClass = HrmConfigLog.class)
public class HrmConfigController {

    @Autowired
    private IHrmConfigService configService;

    @Autowired
    private IHrmEmployeeFieldService employeeFieldService;

    @Autowired
    private IHrmInsuranceSchemeService insuranceSchemeService;

    @Autowired
    private IHrmAchievementTableService achievementTableService;

    @Autowired
    private IHrmRecruitChannelService recruitChannelService;

    @Autowired
    private IHrmEmployeeService employeeService;

    @Autowired
    private IHrmRecruitCandidateService recruitCandidateService;


    /**
     * 保存招聘渠道（启用/停用/增加都是这个接口）
     * @param channelList
     * @return
     */
    @PostMapping("/saveRecruitChannel")
    @ApiOperation("保存招聘渠道")
    @SysLogHandler(applicationName = "admin",subModel = SubModelType.ADMIN_HUMAN_RESOURCE_MANAGEMENT,behavior = BehaviorEnum.UPDATE,object = "保存招聘渠道",detail = "保存招聘渠道")
    public Result saveRecruitChannel(@RequestBody List<HrmRecruitChannel> channelList) {
        recruitChannelService.saveOrUpdateBatch(channelList);
        return Result.ok();
    }

    /**（业务参数设置模块那里）
     * 查询招聘渠道配置列表
     * @return
     */
    @PostMapping("/queryRecruitChannelList")
    @ApiOperation("查询招聘渠道配置列表")
    public Result<List<HrmRecruitChannel>> queryRecruitChannelList() {
        List<HrmRecruitChannel> list = recruitChannelService.list();
        return Result.ok(list);
    }

    @PostMapping("/deleteRecruitChannel")
    @ApiOperation("删除招聘渠道")
    public Result deleteRecruitChannel(@RequestBody DeleteRecruitChannelBO deleteRecruitChannelBO) {
        if (deleteRecruitChannelBO.getDeleteChannelId().equals(deleteRecruitChannelBO.getChangeChannelId())){
            throw new CrmException(SystemCodeEnum.DeletedChannel_And_ChangedChannel_NotSame);
        }
        HrmRecruitChannel hrmRecruitChannel = recruitChannelService.getById(deleteRecruitChannelBO.getChangeChannelId());
        if (ObjectUtils.isEmpty(hrmRecruitChannel)){
            throw new CrmException(422,"更换渠道未空");
        }
        System.out.println("getDeleteChannelId()"+deleteRecruitChannelBO.getDeleteChannelId());
        System.out.println("getChangeChannelId()"+deleteRecruitChannelBO.getChangeChannelId());
        employeeService.lambdaUpdate()
                .set(HrmEmployee::getChannelId, deleteRecruitChannelBO.getChangeChannelId())
                .eq(HrmEmployee::getChannelId, deleteRecruitChannelBO.getDeleteChannelId())
                .update();
        recruitCandidateService.lambdaUpdate()
                .set(HrmRecruitCandidate::getChannelId, deleteRecruitChannelBO.getChangeChannelId())
                .eq(HrmRecruitCandidate::getChannelId, deleteRecruitChannelBO.getDeleteChannelId())
                .update();
        recruitChannelService.removeById(deleteRecruitChannelBO.getDeleteChannelId());
        return Result.ok();
    }

    /**
     * 保存淘汰招聘原因
     * 企业后台那里设置供淘汰的时候选择
     * @param data
     * @return
     */
    @PostMapping("/saveRecruitEliminate")
    @ApiOperation("保存淘汰招聘原因")
    @SysLogHandler(applicationName = "admin",subModel = SubModelType.ADMIN_HUMAN_RESOURCE_MANAGEMENT,behavior = BehaviorEnum.UPDATE,object = "保存淘汰原因",detail = "保存淘汰原因")
    public Result saveRecruitEliminate(@RequestBody List<String> data) {
        configService.lambdaUpdate().eq(HrmConfig::getType, ConfigType.ELIMINATION_REASONS.getValue()).remove();
        List<HrmConfig> collect = data.stream().map(value -> {
            HrmConfig hrmConfig = new HrmConfig();
            hrmConfig.setType(ConfigType.ELIMINATION_REASONS.getValue());
            hrmConfig.setValue(value);
            return hrmConfig;
        }).collect(Collectors.toList());
        configService.saveBatch(collect);
        return Result.ok();
    }

    /**（企业后台）
     * 查询淘汰原因配置列表
     * @return
     */
    @PostMapping("/queryRecruitEliminateList")
    @ApiOperation("查询淘汰原因配置列表")
    public Result<List<String>> queryRecruitEliminateList() {
        List<String> list = configService.lambdaQuery().eq(HrmConfig::getType, ConfigType.ELIMINATION_REASONS.getValue()).list()
                .stream().map(HrmConfig::getValue).collect(Collectors.toList());
        return Result.ok(list);
    }


    /**
     * --------------自定义字段---------------
     */
    @PostMapping("/queryFields")
    @ApiOperation("查询后台配置自定义字段列表")
    public Result<List<FiledListVO>> queryFields() {
        List<FiledListVO> fieldList = employeeFieldService.queryFields();
        return Result.ok(fieldList);
    }


    @PostMapping("/queryFieldByLabel/{labelGroup}")
    @ApiOperation("查询后台配置自定义字段列表")
    public Result<List<List<HrmEmployeeField>>> queryFieldByLabel(@ApiParam("1 个人信息 2 通讯信息 7 联系人信息 11 岗位信息") @PathVariable("labelGroup") Integer labelGroup) {
       List< List<HrmEmployeeField>> data = employeeFieldService.queryFieldByLabel(labelGroup);
        return Result.ok(data);
    }

    @PostMapping("/saveField")
    @ApiOperation("保存后台自定义字段")
    @SysLogHandler(applicationName = "admin",subModel = SubModelType.ADMIN_HUMAN_RESOURCE_MANAGEMENT,behavior = BehaviorEnum.UPDATE)
    public Result saveField(@RequestBody AddEmployeeFieldBO addEmployeeFieldBO) {
        employeeFieldService.saveField(addEmployeeFieldBO);
        return Result.ok();
    }

    /**
     * --------------社保方案---------------
     */
    @PostMapping("/addInsuranceScheme")
    @ApiOperation("添加社保方案")
    @SysLogHandler(applicationName = "admin",subModel = SubModelType.ADMIN_HUMAN_RESOURCE_MANAGEMENT,behavior = BehaviorEnum.SAVE,object = "#addInsuranceSchemeBO.schemeName",detail = "'添加社保方案:'+#addInsuranceSchemeBO.schemeName")
    public Result addInsuranceScheme(@Valid @RequestBody AddInsuranceSchemeBO addInsuranceSchemeBO) {
        insuranceSchemeService.setInsuranceScheme(addInsuranceSchemeBO);
        return Result.ok();
    }

    @PostMapping("/setInsuranceScheme")
    @ApiOperation("修改社保方案")
    @SysLogHandler(applicationName = "admin",subModel = SubModelType.ADMIN_HUMAN_RESOURCE_MANAGEMENT,behavior = BehaviorEnum.UPDATE,object = "#addInsuranceSchemeBO.schemeName",detail = "'添加社保方案:'+#addInsuranceSchemeBO.schemeName")
    public Result setInsuranceScheme(@Valid @RequestBody AddInsuranceSchemeBO addInsuranceSchemeBO) {
        insuranceSchemeService.setInsuranceScheme(addInsuranceSchemeBO);
        return Result.ok();
    }


    @PostMapping("/deleteInsuranceScheme/{schemeId}")
    @ApiOperation("修改社保方案")
    @SysLogHandler(applicationName = "admin",subModel = SubModelType.ADMIN_HUMAN_RESOURCE_MANAGEMENT,behavior = BehaviorEnum.DELETE)
    public Result deleteInsuranceScheme(@PathVariable("schemeId") Integer schemeId) {
        insuranceSchemeService.deleteInsuranceScheme(schemeId);
        return Result.ok();
    }

    @PostMapping("/queryInsuranceSchemePageList")
    @ApiOperation("查询社保方案列表")
    public Result<BasePage<InsuranceSchemeListVO>> queryInsuranceSchemePageList(@RequestBody PageEntity pageEntity) {
        BasePage<InsuranceSchemeListVO> page = insuranceSchemeService.queryInsuranceSchemePageList(pageEntity);
        return Result.ok(page);
    }

    @PostMapping("/queryInsuranceSchemeById/{schemeId}")
    @ApiOperation("查询参保方案详情")
    public Result<InsuranceSchemeVO> queryInsuranceSchemeById(@PathVariable("schemeId") Integer schemeId) {
        InsuranceSchemeVO insuranceSchemeVO = insuranceSchemeService.queryInsuranceSchemeById(schemeId);
        return Result.ok(insuranceSchemeVO);
    }

    /**
     * --------------考核模板---------------
     */

    /**
     * 查询考核模板类型列表（KPI）（OKR）
     * @return
     */
    @PostMapping("/queryAchievementTableList")
    @ApiOperation("查询考核模板类型列表")
    public Result<List<HrmAchievementTable>> queryAchievementTableList() {
        List<HrmAchievementTable> list = achievementTableService.queryAchievementTableList();
        return Result.ok(list);

    }


    /**
     * 添加或修改考核模板内容
     * @param setAchievementTableBO
     * @return
     */
    @PostMapping("/setAchievementTable")
    @ApiOperation("添加或修改考核模板内容")
    @SysLogHandler(applicationName = "admin",subModel = SubModelType.ADMIN_HUMAN_RESOURCE_MANAGEMENT,behavior = BehaviorEnum.UPDATE,object = "#setAchievementTableBO.tableName",detail = "'修改考核模板:'+#setAchievementTableBO.tableName")
    public Result<HrmAchievementTable> setAchievementTable(@Valid @RequestBody SetAchievementTableBO setAchievementTableBO) {
        HrmAchievementTable achievementTable = achievementTableService.setAchievementTable(setAchievementTableBO);
        return Result.ok(achievementTable);
    }



    /**
     * 根据id查询考核模板
     * table=49 OKR模板  table=48 KPI模板
     * @param tableId
     * @return
     */
    @PostMapping("/queryAchievementTableById/{tableId}")
    @ApiOperation("根据id查询考核模板")
    public Result<AchievementTableVO> queryAchievementTableById(@PathVariable Integer tableId) {
        AchievementTableVO achievementTableVO = achievementTableService.queryAchievementTableById(tableId);
        return Result.ok(achievementTableVO);
    }



}
