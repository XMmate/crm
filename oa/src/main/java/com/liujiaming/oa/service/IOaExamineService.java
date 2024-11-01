package com.liujiaming.oa.service;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.feign.examine.entity.ExamineConditionDataBO;
import com.liujiaming.core.feign.examine.entity.ExamineInfoVo;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.oa.entity.BO.AuditExamineBO;
import com.liujiaming.oa.entity.BO.ExamineExportBO;
import com.liujiaming.oa.entity.BO.ExaminePageBO;
import com.liujiaming.oa.entity.BO.GetExamineFieldBO;
import com.liujiaming.oa.entity.PO.OaExamine;
import com.liujiaming.oa.entity.PO.OaExamineCategory;
import com.liujiaming.oa.entity.PO.OaExamineField;
import com.liujiaming.oa.entity.VO.ExamineVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 审批表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface IOaExamineService extends BaseService<OaExamine> {

    BasePage<ExamineVO> myInitiate(ExaminePageBO examinePageBO);

    BasePage<ExamineVO> myOaExamine(ExaminePageBO examinePageBO);

    List<OaExamineField> getField(GetExamineFieldBO getExamineFieldBO);

    List<List<OaExamineField>> getFormPositionField(GetExamineFieldBO getExamineFieldBO);

    void setOaExamine(JSONObject jsonObject);

    void oaExamine(AuditExamineBO auditExamineBO);

    ExamineVO queryOaExamineInfo(String examineId);

    JSONObject queryExamineRecordList(String recordId);

    List<JSONObject> queryExamineLogList(Integer recordId);

    void deleteOaExamine(Integer examineId);

    OaExamineCategory queryExaminStep(String categoryId);

    List<Map<String, Object>> export(ExamineExportBO examineExportBO, ExamineInfoVo examineInfoVo , List<OaExamineField> fieldList);

    public List<ExamineVO> transfer(List<ExamineVO> recordList);

    Map<String, Object> getDataMapForNewExamine(ExamineConditionDataBO examineConditionDataBO);

    Boolean updateCheckStatusByNewExamine(ExamineConditionDataBO examineConditionDataBO);

    ExamineVO getOaExamineById(Integer oaExamineId);
}
