package com.liujiaming.crm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.constant.CrmTypeEnum;
import com.liujiaming.crm.entity.BO.CrmMemberSaveBO;
import com.liujiaming.crm.entity.PO.CrmTeamMembers;
import com.liujiaming.crm.entity.VO.CrmMembersSelectVO;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * crm团队成员表
 * 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2021-04-07
 */
public interface ICrmTeamMembersService extends BaseService<CrmTeamMembers> {
    /**
     * 获取团队成员
     *
     * @param crmTypeEnum     对应类型
     * @param typeId      对应类型ID
     * @param ownerUserId 负责人ID
     * @return data
     */
    public List<CrmMembersSelectVO> getMembers(CrmTypeEnum crmTypeEnum, Integer typeId, Long ownerUserId);

    /**
     * 添加团队成员
     *
     * @param crmTypeEnum         对应类型
     * @param crmMemberSaveBO data
     */
    public void addMember(CrmTypeEnum crmTypeEnum, CrmMemberSaveBO crmMemberSaveBO);

    /**
     * 删除团队成员
     *
     * @param crmTypeEnum         对应类型
     * @param crmMemberSaveBO data
     */
    public void deleteMember(CrmTypeEnum crmTypeEnum, CrmMemberSaveBO crmMemberSaveBO);

    /**
     * 退出团队
     *
     * @param crmTypeEnum 对应类型
     * @param typeId  对应类型ID
     */
    public void exitTeam(CrmTypeEnum crmTypeEnum, Integer typeId);

    /**
     * 添加单条团队成员数据
     *
     * @param crmTypeEnum 对应类型
     * @param typeId  对应类型ID
     * @param userId  用户ID
     * @param power   读写类型
     */
    public void addSingleMember(CrmTypeEnum crmTypeEnum, Integer typeId, Long userId, Integer power, Date expiresTime, String name);


    /**
     * 查询团队成员数量
     * @param crmTypeEnum 对应类型
     * @param typeId  对应类型ID
     * @param ownerUserId  用户ID
     */
    public Integer queryMemberCount(CrmTypeEnum crmTypeEnum, Integer typeId, Long ownerUserId);

    /**
     * 删除过期的团队成员数据
     */
    public void removeOverdueTeamMembers();
}
