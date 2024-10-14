package com.liujiaming.oa.service;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.entity.PageEntity;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.oa.entity.BO.SetExamineCategoryBO;
import com.liujiaming.oa.entity.BO.UpdateCategoryStatus;
import com.liujiaming.oa.entity.PO.OaExamineCategory;
import com.liujiaming.oa.entity.PO.OaExamineSort;
import com.liujiaming.oa.entity.VO.OaExamineCategoryVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 审批类型表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface IOaExamineCategoryService extends BaseService<OaExamineCategory> {

    Map<String,Integer> setExamineCategory(SetExamineCategoryBO setExamineCategoryBO);

    BasePage<OaExamineCategoryVO> queryExamineCategoryList(PageEntity pageEntity);

    List<OaExamineCategory> queryAllExamineCategoryList();

    void saveOrUpdateOaExamineSort(List<OaExamineSort> oaExamineSortList);

    void deleteExamineCategory(Integer id);

    void updateStatus(UpdateCategoryStatus updateCategoryStatus);
}
