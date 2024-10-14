package com.liujiaming.oa.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.liujiaming.core.common.Const;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.entity.PageEntity;
import com.liujiaming.core.entity.UserInfo;
import com.liujiaming.core.exception.CrmException;
import com.liujiaming.core.feign.admin.entity.AdminMessageBO;
import com.liujiaming.core.feign.admin.entity.AdminMessageEnum;
import com.liujiaming.core.feign.admin.entity.SimpleDept;
import com.liujiaming.core.feign.admin.entity.SimpleUser;
import com.liujiaming.core.feign.admin.service.AdminMessageService;
import com.liujiaming.core.feign.admin.service.AdminService;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.core.utils.SeparatorUtil;
import com.liujiaming.core.utils.TagUtil;
import com.liujiaming.core.utils.UserCacheUtil;
import com.liujiaming.core.utils.UserUtil;
import com.liujiaming.oa.common.OaCodeEnum;
import com.liujiaming.oa.entity.PO.OaAnnouncement;
import com.liujiaming.oa.mapper.OaAnnouncementMapper;
import com.liujiaming.oa.service.IOaAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 公告表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
@Service
public class OaAnnouncementServiceImpl extends BaseServiceImpl<OaAnnouncementMapper, OaAnnouncement> implements IOaAnnouncementService {

    @Autowired
    private AdminService adminService;


    /**
     * 添加公告 发送通知
     * @param oaAnnouncement
     */
    @Override
    public void addOaAnnouncement(OaAnnouncement oaAnnouncement) {
        oaAnnouncement.setDeptIds(SeparatorUtil.fromString(oaAnnouncement.getDeptIds()));
        oaAnnouncement.setOwnerUserIds(SeparatorUtil.fromString(oaAnnouncement.getOwnerUserIds()));
        oaAnnouncement.setCreateTime(DateUtil.date());
        oaAnnouncement.setUpdateTime(DateUtil.date());
        save(oaAnnouncement);
        System.out.println("标记打印oaAnnouncement.getOwnerUserIds()"+oaAnnouncement.getOwnerUserIds());
       //ids算法有问题
        List<Long> ids = new ArrayList<>();
        if (StrUtil.isAllEmpty(oaAnnouncement.getOwnerUserIds(), oaAnnouncement.getDeptIds())) {
            ids.addAll(adminService.queryUserList(2).getData());
        } else {

            System.out.println("标记打印ids"+StrUtil.splitTrim(oaAnnouncement.getOwnerUserIds(), Const.SEPARATOR).stream().map(Long::valueOf).collect(Collectors.toList()));
            ids.addAll(StrUtil.splitTrim(oaAnnouncement.getOwnerUserIds(), Const.SEPARATOR).stream().map(Long::valueOf).collect(Collectors.toList()));
            List<Integer> deptIds = StrUtil.splitTrim(oaAnnouncement.getDeptIds(), Const.SEPARATOR).stream().map(Integer::valueOf).collect(Collectors.toList());
            if (deptIds.size() > 0) {
                System.out.println("deptIds"+deptIds);
                System.out.println();
                ids.addAll(adminService.queryUserByDeptIds(deptIds).getData());
                System.out.println(adminService.queryUserByDeptIds(deptIds).getData());
                System.out.println(ids);
            }
            ids.add(UserUtil.getUserId());
        }
        if (ids.size() > 0) {
            AdminMessageBO adminMessageBO = new AdminMessageBO();
            adminMessageBO.setUserId(0L);
            adminMessageBO.setMessageType(AdminMessageEnum.OA_NOTICE_MESSAGE.getType());
            adminMessageBO.setTitle(oaAnnouncement.getTitle());
            adminMessageBO.setTypeId(oaAnnouncement.getAnnouncementId());
            adminMessageBO.setIds(ids);
            ApplicationContextHolder.getBean(AdminMessageService.class).sendMessage(adminMessageBO);
        }
    }

    @Override
    public void setOaAnnouncement(OaAnnouncement oaAnnouncement) {
        oaAnnouncement.setUpdateTime(DateUtil.date());
        updateById(oaAnnouncement);
    }

    @Override
    public void delete(Integer announcementId) {
        removeById(announcementId);
    }


    /**
     * 查看公告内容
     * @param announcementId
     * @return
     */
    @Override
    public OaAnnouncement queryById(Integer announcementId) {
        OaAnnouncement announcement = getById(announcementId);
        if (announcement == null){
            throw new CrmException(OaCodeEnum.ANNOUNCEMENT_ALREADY_DELETE);
        }
        if (UserUtil.isAdmin()){
            List<SimpleDept> deptList = adminService.queryDeptByIds(TagUtil.toSet(announcement.getDeptIds())).getData();
            announcement.setDeptList(deptList);
            List<SimpleUser> userList = UserCacheUtil.getSimpleUsers(TagUtil.toLongSet(announcement.getOwnerUserIds()));
            announcement.setOwnerUserList(userList);
        }
        return announcement;
    }

    /**
     * 查询公共列表
     *
     * @return data
     */
    @Override
    public BasePage<OaAnnouncement> queryList(PageEntity entity, Integer type) {
        UserInfo userInfo = UserUtil.getUser();
        return getBaseMapper().queryList(entity.parse(), type, userInfo.getUserId(), userInfo.getDeptId());
    }
}
