package com.liujiaming.core.utils;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.liujiaming.core.common.Const;
import com.liujiaming.core.entity.UserInfo;
import com.liujiaming.core.feign.admin.entity.SimpleUser;
import com.liujiaming.core.feign.admin.service.AdminService;
import com.liujiaming.core.redis.service.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liujiaming
 * 用户缓存相关方法
 */
@Component
public class UserCacheUtil {
    static UserCacheUtil ME;

    @PostConstruct
    public void init() {
        ME = this;
    }

    @CreateCache(name = Const.ADMIN_USER_NAME_CACHE_NAME, expire = 3, timeUnit = TimeUnit.DAYS)
    private Cache<Long, SimpleUser> userCache;

    @CreateCache(name = Const.ADMIN_DEPT_NAME_CACHE_NAME, expire = 3, timeUnit = TimeUnit.DAYS)
    private Cache<Integer, String> deptCache;

    @Autowired
    private AdminService adminService;

    @Autowired
    Redis redis;

    /**
     * 根据用户ID获取用户名列表，使用，号合并
     *
     * @param userIds userIds
     * @return data
     */
    public static <T> String getUserNameList(List<T> userIds) {
        List<String> stringList = new ArrayList<>();
        for (T obj : userIds) {
            String name;
            if (obj instanceof Long) {
                name = getUserName((Long) obj);
            } else if (obj instanceof String) {
                name = getUserName(Long.valueOf((String) obj));
            } else {
                name = "";
            }
            if (!"".equals(name)) {
                stringList.add(name);
            }
        }
        return stringList.size() > 0 ? String.join(Const.SEPARATOR, stringList) : "";
    }

    /**
     * 根据用户ID获取用户名
     *
     * @param userId 用户ID
     * @return data
     */
    public static UserInfo getUserInfo(Long userId) {
        return ME.adminService.getUserInfo(userId).getData();
    }

    /**
     * 根据用户ID获取用户名
     *
     * @param userId 用户ID
     * @return data
     */
    public static String getUserName(Long userId) {
        if (userId == null) {
            return "";
        }
        return getSimpleUser(userId).getRealname();
    }

    public static SimpleUser getSimpleUser(Long userId) {
        if(userId == null){
            return new SimpleUser();
        }
        SimpleUser simpleUser = ME.userCache.get(userId);
        if (simpleUser == null) {
            simpleUser = ME.adminService.queryUserById(userId).getData();
            if(simpleUser != null) {
                ME.userCache.put(userId, simpleUser);
            } else {
                simpleUser = new SimpleUser();
            }
        }
        return simpleUser;
    }

    public static List<SimpleUser> getSimpleUsers(Collection<Long> ids) {
        if(ids == null || ids.isEmpty()){
            return Collections.emptyList();
        }
        List<SimpleUser> simpleUserList = new ArrayList<>(ids.size());
        for (Long userId : ids) {
            SimpleUser simpleUser = ME.userCache.get(userId);
            if (simpleUser == null) {
                simpleUser = ME.adminService.queryUserById(userId).getData();
                if(simpleUser != null) {
                    ME.userCache.put(userId, simpleUser);
                } else {
                    continue;
                }
            }
            simpleUserList.add(simpleUser);
        }
        return simpleUserList;
    }

    /**
     * 根据部门ID获取部门名称，使用，号合并
     *
     * @param deptIds deptIds
     * @return data
     */
    public static <T> String getDeptNameList(List<T> deptIds) {
        List<String> stringList = new ArrayList<>();
        for (T obj : deptIds) {
            String name;
            if (obj instanceof Integer) {
                name = getDeptName((Integer) obj);
            } else if (obj instanceof String) {
                name = getDeptName(Integer.valueOf((String) obj));
            } else {
                name = "";
            }
            if (!"".equals(name)) {
                stringList.add(name);
            }
        }
        return stringList.size() > 0 ? String.join(Const.SEPARATOR, stringList) : "";
    }

    /**
     * 根据部门ID获取部门名称
     *
     * @param deptId 部门ID
     * @return data
     */
    public static String getDeptName(Integer deptId) {
        if (deptId == null) {
            return "";
        }
        String name = ME.deptCache.get(deptId);
        if (name == null) {
            name = ME.adminService.queryDeptName(deptId).getData();
            ME.deptCache.put(deptId, name);
        }
        return name;
    }

    /**
     * 查询该用户下级的用户
     *
     * @param userId 用户ID 0代表全部
     * @return data
     */
    public static List<Long> queryChildUserId(Long userId) {
        return ME.adminService.queryChildUserId(userId).getData();
    }

    /**
     * 查询部门下属部门
     *
     * @param deptId 上级ID
     * @return data
     */
    public static List<Integer> queryChildDeptId(Integer deptId) {
        return ME.adminService.queryChildDeptId(deptId).getData();
    }
}
