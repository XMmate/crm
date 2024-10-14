package com.liujiaming.bi.service;

import com.liujiaming.bi.entity.BO.AchievementBO;
import com.liujiaming.bi.entity.PO.CrmAchievement;
import com.liujiaming.core.servlet.BaseService;

import java.util.List;

/**
 * <p>
 * 业绩目标 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-07-22
 */
public interface ICrmAchievementService extends BaseService<CrmAchievement> {

    /**
     * 查询业绩目标
     * @param achievementBO bo
     * @return data
     */
    public List<CrmAchievement> queryAchievementList(AchievementBO achievementBO);

    /**
     * 保存业绩目标
     * @param achievement achievement
     */
    public void addAchievement(CrmAchievement achievement);


    /**
     * 验证业绩目标数据
     * @date 2024/11/19 14:39
     * @param crmAchievements
     * @return void
     **/
    public void verifyCrmAchievementData(List<CrmAchievement> crmAchievements);

}
