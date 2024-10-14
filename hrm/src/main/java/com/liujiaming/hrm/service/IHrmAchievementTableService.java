package com.liujiaming.hrm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.BO.SetAchievementTableBO;
import com.liujiaming.hrm.entity.PO.HrmAchievementTable;
import com.liujiaming.hrm.entity.VO.AchievementTableVO;

import java.util.List;

/**
 * <p>
 * 绩效考核表模板 服务类
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
public interface IHrmAchievementTableService extends BaseService<HrmAchievementTable> {

    /**
     * 添加或修改考核模板考核模板
     * @param setAchievementTableBO
     * @return
     */
    HrmAchievementTable setAchievementTable(SetAchievementTableBO setAchievementTableBO);

    /**
     * 根据类型查询考核模板
     * @param tableId
     * @return
     */
    AchievementTableVO queryAchievementTableById(Integer tableId);

    /**
     * 查询考核模板列表
     * @return
     */
    List<HrmAchievementTable> queryAchievementTableList();

}
