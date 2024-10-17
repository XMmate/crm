package com.liujiaming.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liujiaming.admin.common.AdminConst;
import com.liujiaming.admin.entity.PO.AdminUserConfig;
import com.liujiaming.admin.mapper.AdminUserConfigMapper;
import com.liujiaming.admin.service.IAdminUserConfigService;
import com.liujiaming.core.common.Const;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.core.utils.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户配置表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-04-27
 */
@Service
public class AdminUserConfigServiceImpl extends BaseServiceImpl<AdminUserConfigMapper, AdminUserConfig> implements IAdminUserConfigService {

    /**
     * 根据名称查询用户配置信息
     *
     * @param name
     * @return data
     */
    @Override
    public AdminUserConfig queryUserConfigByName(String name) {
        QueryWrapper<AdminUserConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name).eq("user_id", UserUtil.getUserId()).last(" limit 1");
        return getOne(queryWrapper);
    }

    /**
     * 根据名称查询用户配置信息列表
     * userId获取当前登录人
     *
     * @param name 名称
     * @return data
     */
    @Override
    public List<AdminUserConfig> queryUserConfigListByName(String name) {
        QueryWrapper<AdminUserConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name).eq("user_id", UserUtil.getUserId());
        return list(queryWrapper);
    }

    /**
     * 根据名称删除用户配置信息
     * userId获取当前登录人
     *
     * @param name 名称
     */
    @Override
    public void deleteUserConfigByName(String name) {
        QueryWrapper<AdminUserConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name).eq("user_id", UserUtil.getUserId());
        remove(queryWrapper);
    }

    /**
     * 用户注册的时候初始化用户配置
     *
     * @param userId 新增的用户ID
     */
    @Override
    public void initUserConfig(Long userId) {
        //保存用户跟进记录常用语
        List<AdminUserConfig> adminUserConfigList = new ArrayList<>();
        adminUserConfigList.add(new AdminUserConfig(null, userId, 1, "ActivityPhrase", "电话无人接听", "跟进记录常用语"));
        adminUserConfigList.add(new AdminUserConfig(null, userId, 1, "ActivityPhrase", "客户无意向", "跟进记录常用语"));
        adminUserConfigList.add(new AdminUserConfig(null, userId, 1, "ActivityPhrase", "客户意向度适中，后续继续跟进", "跟进记录常用语"));
        adminUserConfigList.add(new AdminUserConfig(null, userId, 1, "ActivityPhrase", "客户意向度较强，成交几率较大", "跟进记录常用语"));
        saveBatch(adminUserConfigList, Const.BATCH_SAVE_SIZE);
    }

    /**
     * 根据名称和内容查询配置用户
     */
    @Override
    public List<AdminUserConfig> queryUserConfigByNameAndValue(String name, String value) {
        QueryWrapper<AdminUserConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name).eq("value", value);
        return list(queryWrapper);
    }


    /**
     * 设置跟进类型常用语
     * @param stringList
     */
    @Override
    @Transactional
    public void setActivityPhrase(List<String> stringList) {
        String name = "ActivityPhrase";
        Long userId = UserUtil.getUserId();
        String description = "跟进记录常用语";
        deleteUserConfigByName(name);
        List<AdminUserConfig> adminUserConfigList = new ArrayList<>(stringList.size());
        stringList.forEach(str -> {
            AdminUserConfig userConfig = new AdminUserConfig();
            userConfig.setStatus(1);
            userConfig.setName(name);
            userConfig.setValue(str);
            userConfig.setUserId(userId);
            userConfig.setDescription(description);
            adminUserConfigList.add(userConfig);
        });
      saveBatch(adminUserConfigList, AdminConst.BATCH_SAVE_SIZE);
    }

}
