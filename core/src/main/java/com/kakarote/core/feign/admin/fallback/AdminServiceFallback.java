package com.kakarote.core.feign.admin.fallback;

import com.alibaba.fastjson.JSONObject;
import com.kakarote.core.common.R;
import com.kakarote.core.common.Result;
import com.kakarote.core.entity.UserInfo;
import com.kakarote.core.feign.admin.entity.*;
import com.kakarote.core.feign.admin.service.AdminService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
@Component
public class AdminServiceFallback implements FallbackFactory<AdminService> {
    @Override
    public AdminService create(Throwable cause) {
        return new AdminService() {
            @Override
            public Result<UserInfo> getUserInfo(Long userId) {
                return R.error(503,"获取用户信息失败");
            }

            @Override
            public Result<String> queryDeptName(Integer deptId) {
                return R.error(503,"获取部门信息失败");
            }

            @Override
            public Result<List<Integer>> queryChildDeptId(Integer deptId) {
                return R.error(503,"查询子部门信息失败");
            }

            @Override
            public Result<List<Long>> queryChildUserId(Long userId) {
                return R.error(503,"查询子部门信息失败");
            }

            @Override
            public Result<List<Long>> queryUserList(Integer type) {
                return null;
            }

            @Override
            public Result<List<AdminConfig>> queryConfigByName(String name) {
                return null;
            }

            @Override
            public Result<AdminConfig> queryFirstConfigByName(String name) {
                return null;
            }

            @Override
            public Result<List<Long>> queryNormalUserByIds(Collection<Long> ids) {
                return null;
            }

            @Override
            public Result<SimpleUser> queryUserById(Long userId) {
                return null;
            }

            @Override
            public Result<List<SimpleDept>> queryDeptByIds(Collection<Integer> ids) {
                return null;
            }

            @Override
            public Result<List<Long>> queryUserByDeptIds(Collection<Integer> ids) {
                return null;
            }

            @Override
            public Result<Integer> queryDataType(Long userId, Integer menuId) {
                return null;
            }

            @Override
            public Result<List<Long>> queryUserByAuth(Long userId, Integer menuId) {
                return null;
            }

            @Override
            public Result<Integer> queryWorkRole(Integer label) {
                return null;
            }

            @Override
            public Result<List<Integer>> queryRoleByRoleType(Integer type) {
                return null;
            }

            @Override
            public Result<List<AdminRole>> queryRoleByRoleTypeAndUserId(Integer type) {
                return null;
            }

            @Override
            public Result updateAdminConfig(AdminConfig adminConfig) {
                return null;
            }

            @Override
            public Result<JSONObject> auth() {
                return null;
            }

            @Override
            public Result<Long> saveOrUpdateMessage(AdminMessage message) {
                return null;
            }

            @Override
            public Result<AdminMessage> getMessageById(Long messageId) {
                return null;
            }

            @Override
            public Result<AdminConfig> queryFirstConfigByNameAndValue(String name, String value) {
                return null;
            }

            @Override
            public Result<Integer> queryMenuId(String realm1, String realm2, String realm3) {
                return null;
            }

            @Override
            public Result<List<Long>> queryUserIdByRealName(List<String> realNames) {
                return null;
            }

            @Override
            public Result<UserInfo> queryLoginUserInfo(Long userId) {
                return null;
            }

            @Override
            public Result<Long> queryUserIdByUserName(String userName) {
                return null;
            }

            @Override
            public Result<List<UserInfo>> queryUserInfoList() {
                return null;
            }

            @Override
            public Result<List<Long>> queryUserIdByRoleId(Integer roleId) {
                return null;
            }
        };
    }
}
