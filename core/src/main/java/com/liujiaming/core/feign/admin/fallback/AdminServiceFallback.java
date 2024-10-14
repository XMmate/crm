package com.liujiaming.core.feign.admin.fallback;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.common.R;
import com.liujiaming.core.common.Result;
import com.liujiaming.core.entity.UserInfo;
import com.liujiaming.core.feign.admin.entity.*;
import com.liujiaming.core.feign.admin.entity.*;
import com.liujiaming.core.feign.admin.service.AdminService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Component
@Slf4j
public class AdminServiceFallback implements FallbackFactory<AdminService> {
    @Override
    public AdminService create(Throwable cause) {
        return new AdminService() {
            @Override
            public Result<UserInfo> getUserInfo(Long userId) {
                log.error(cause.getMessage());
                return R.error(503,"获取用户信息失败");
            }

            @Override
            public Result<String> queryDeptName(Integer deptId) {
                log.error(cause.getMessage());
                return R.error(503,"获取部门信息失败");
            }

            @Override
            public Result<List<Integer>> queryChildDeptId(Integer deptId) {
                log.error(cause.getMessage());
                return R.error(503,"查询子部门信息失败");
            }

            @Override
            public Result<List<Long>> queryChildUserId(Long userId) {
                log.error(cause.getMessage());
                return R.error(503,"查询子部门信息失败");
            }

            @Override
            public Result<List<Long>> queryUserList(Integer type) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<AdminConfig>> queryConfigByName(String name) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<AdminConfig> queryFirstConfigByName(String name) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<List<Long>> queryNormalUserByIds(Collection<Long> ids) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<SimpleUser> queryUserById(Long userId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<List<SimpleDept>> queryDeptByIds(Collection<Integer> ids) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<Long>> queryUserByDeptIds(Collection<Integer> ids) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<Integer> queryDataType(Long userId, Integer menuId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<List<Long>> queryUserByAuth(Long userId, Integer menuId) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<Integer> queryWorkRole(Integer label) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<List<Integer>> queryRoleByRoleType(Integer type) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<AdminRole>> queryRoleByRoleTypeAndUserId(Integer type) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result updateAdminConfig(AdminConfig adminConfig) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<JSONObject> auth() {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<Long> saveOrUpdateMessage(AdminMessage message) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<AdminMessage> getMessageById(Long messageId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<AdminConfig> queryFirstConfigByNameAndValue(String name, String value) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<Integer> queryMenuId(String realm1, String realm2, String realm3) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<List<Long>> queryUserIdByRealName(List<String> realNames) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<UserInfo> queryLoginUserInfo(Long userId) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<Long> queryUserIdByUserName(String userName) {
                log.error(cause.getMessage());
                return Result.ok();
            }

            @Override
            public Result<List<UserInfo>> queryUserInfoList() {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }

            @Override
            public Result<List<Long>> queryUserIdByRoleId(Integer roleId) {
                log.error(cause.getMessage());
                return Result.ok(new ArrayList<>());
            }
        };
    }
}
