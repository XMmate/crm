package com.liujiaming.crm.common;

import cn.hutool.core.util.StrUtil;
import com.liujiaming.core.common.Const;
import com.liujiaming.core.common.cache.CrmCacheKey;
import com.liujiaming.core.feign.admin.service.AdminService;
import com.liujiaming.core.redis.service.Redis;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.core.utils.BaseUtil;
import com.liujiaming.core.utils.UserUtil;
import com.liujiaming.crm.constant.CrmAuthEnum;
import com.liujiaming.crm.constant.CrmTypeEnum;
import com.liujiaming.crm.entity.PO.CrmCustomer;
import com.liujiaming.crm.entity.PO.CrmCustomerPool;
import com.liujiaming.crm.mapper.CrmAuthMapper;
import com.liujiaming.crm.service.ICrmCustomerPoolService;
import com.liujiaming.crm.service.ICrmCustomerService;

import java.util.*;

/**
 * 权限相关封装
 *
 * @author liujiaming
 */
public class AuthUtil {

    public static boolean isCrmAuth(CrmTypeEnum crmTypeEnum, Integer id,CrmAuthEnum crmAuthEnum) {
        String conditions = crmTypeEnum.getPrimaryKey(false) + " = " + id + getCrmAuthSql(crmTypeEnum, 1,crmAuthEnum);
        Integer integer = ApplicationContextHolder.getBean(CrmAuthMapper.class).queryAuthNum("wk_crm_" + crmTypeEnum.getTableName(), conditions);
        return integer == 0;
    }

    /**
     * 团队成员是否有操作权限
     */
    public static boolean isRwAuth(Integer id, CrmTypeEnum crmTypeEnum,CrmAuthEnum crmAuthEnum) {
        String conditions = crmTypeEnum.getPrimaryKey(false) + " = " + id + getCrmAuthSql(crmTypeEnum, 0,crmAuthEnum);
        Integer integer = ApplicationContextHolder.getBean(CrmAuthMapper.class).queryAuthNum("wk_crm_" + crmTypeEnum.getTableName(), conditions);
        return integer == 0;
    }

    /**
     * 是否具有转移客户负责人权限
     */
    public static boolean isChangeOwnerUserAuth(Integer id, CrmTypeEnum crmTypeEnum,CrmAuthEnum crmAuthEnum) {
        String conditions = crmTypeEnum.getPrimaryKey(false) + " = " + id + getCrmAuthSql(crmTypeEnum, 3,crmAuthEnum);
        Integer integer = ApplicationContextHolder.getBean(CrmAuthMapper.class).queryAuthNum("wk_crm_" + crmTypeEnum.getTableName(), conditions);
        return integer == 0;
    }

    /**
     * 客户接口判断公海不需要拦截
     *
     * @param customerId 客户ID
     * @return data
     */
    public static boolean isPoolAuth(Integer customerId,CrmAuthEnum crmAuthEnum) {
        CrmCustomer customer = ApplicationContextHolder.getBean(ICrmCustomerService.class).getById(customerId);
        if (customer == null || customer.getOwnerUserId() == null) {
            return false;
        } else {
            return AuthUtil.isCrmAuth(CrmTypeEnum.CUSTOMER, customerId,crmAuthEnum);
        }
    }

    /**
     * 根据当前用户的本人及下属userId取交集
     *
     * @param userIds user列表
     * @return data
     */
    public static List<Long> filterUserId(List<Long> userIds) {
        if (UserUtil.isAdmin()) {
            return userIds;
        }
        Long userId = UserUtil.getUserId();
        List<Long> subUserIdList = ApplicationContextHolder.getBean(AdminService.class).queryChildUserId(userId).getData();
        subUserIdList.add(userId);
        subUserIdList.retainAll(userIds);
        return subUserIdList;
    }

    /**
     * 根据当前用户的部门及下属部门取交集
     *
     * @param deptIds dept列表
     * @return data
     */
    public static List<Integer> filterDeptId(List<Integer> deptIds) {
        if (UserUtil.isAdmin()) {
            return deptIds;
        }
        Integer deptId = UserUtil.getUser().getDeptId();
        List<Integer> subDeptIdList = ApplicationContextHolder.getBean(AdminService.class).queryChildDeptId(deptId).getData();
        subDeptIdList.add(deptId);
        subDeptIdList.retainAll(deptIds);
        return subDeptIdList;
    }


    /**
     * 拼客户管理数据权限sql
     *
     * @param crmTypeEnum 类型
     * @param readOnly 团队成员参数 0 要求读写权限 1 属于团队成员即可 3 禁止团队成员访问
     * @return sql
     */
    public static String getCrmAuthSql(CrmTypeEnum crmTypeEnum, String alias, Integer readOnly,CrmAuthEnum crmAuthEnum) {
        if (UserUtil.isAdmin() || crmTypeEnum.equals(CrmTypeEnum.PRODUCT) || crmTypeEnum.equals(CrmTypeEnum.CUSTOMER_POOL)) {
            return "";
        }
        StringBuilder conditions = new StringBuilder();
        List<Long> longs = queryAuthUserList(crmTypeEnum, crmAuthEnum);
        if (longs != null && longs.size() > 0) {
            if (crmTypeEnum.equals(CrmTypeEnum.MARKETING)) {
                conditions.append(" and (");
                longs.forEach(id -> conditions.append(" {alias}owner_user_id like CONCAT('%,','").append(id).append("',',%') or ").append("  {alias}relation_user_id like CONCAT('%,','").append(id).append("',',%') or"));
                conditions.delete(conditions.length() - 2, conditions.length());
            } else {
                conditions.append(" and ({alias}owner_user_id in (").append(StrUtil.join(",", longs)).append(")");
                /* 坚持对应团队负责人权限 */
                boolean contains = Arrays.asList(CrmTypeEnum.CUSTOMER, CrmTypeEnum.CONTACTS, CrmTypeEnum.BUSINESS, CrmTypeEnum.RECEIVABLES, CrmTypeEnum.CONTRACT).contains(crmTypeEnum);
                if (contains && CrmAuthEnum.DELETE != crmAuthEnum && !Objects.equals(3,readOnly)) {
                    conditions.append("or {alias}").append(crmTypeEnum.getPrimaryKey(false));
                    conditions.append(" in (");
                    conditions.append("SELECT type_id FROM wk_crm_team_members where type = '")
                            .append(crmTypeEnum.getType())
                            .append("' and user_id = '").append(UserUtil.getUserId()).append("'");
                    if(Objects.equals(0,readOnly)){
                        conditions.append(" and power ='2'");
                    }
                    conditions.append(")");
                }
            }
            conditions.append(")");
        }
        Map<String, String> map = new HashMap<>();
        if (StrUtil.isEmpty(alias)) {
            map.put("alias", "");
        } else {
            map.put("alias", alias + ".");
        }
        return StrUtil.format(conditions.toString(), map);
    }

    public static String getCrmAuthSql(CrmTypeEnum crmTypeEnum, Integer readOnly,CrmAuthEnum crmAuthEnum) {
        return getCrmAuthSql(crmTypeEnum, "", readOnly,crmAuthEnum);
    }

    /**
     * 根据数据权限查询权限范围内员工
     */
    public static List<Long> getUserIdByAuth(Integer menuId) {
        return ApplicationContextHolder.getBean(AdminService.class).queryUserByAuth(UserUtil.getUserId(), menuId).getData();
    }

    /**
     * 公海管理权限判断
     */
    public static boolean isPoolAdmin(Integer poolId) {
        if (!UserUtil.isAdmin()) {
            CrmCustomerPool customerPool = ApplicationContextHolder.getBean(ICrmCustomerPoolService.class).getById(poolId);
            return customerPool == null || !StrUtil.splitTrim(customerPool.getAdminUserId(), Const.SEPARATOR).contains(UserUtil.getUserId().toString());
        }
        return false;
    }


    /**
     * 查询当前用户可查询的用户列表
     * @param crmTypeEnum     crm类型
     * @param crmAuthEnum 数据操作权限类型
     * @return 用户列表
     */
    @SuppressWarnings("unchecked")
    public static List<Long> queryAuthUserList(CrmTypeEnum crmTypeEnum, CrmAuthEnum crmAuthEnum) {
        Long userId = UserUtil.getUserId();
        Redis redis = BaseUtil.getRedis();
        String key = CrmCacheKey.CRM_AUTH_USER_CACHE_KEY + userId.toString();
        Integer menuId = crmAuthEnum.getMenuId(crmTypeEnum);
        Map<Object,Object> map = redis.getRedisMap(key);
        if (map != null && map.containsKey(menuId)) {
            return (List<Long>) map.get(menuId);
        }
        List<Long> userIds = ApplicationContextHolder.getBean(AdminService.class).queryUserByAuth(userId, menuId).getData();
        redis.put(key, menuId, userIds);
        //手动设置过期时间
        redis.expire(key,30 * 60);
        return userIds;
    }

    /**
     * 查询当前用户可查询的用户列表与数据的交集
     * @param crmTypeEnum     crm类型
     * @param crmAuthEnum 数据操作权限类型
     * @return 用户列表
     */
    public static List<Long> filterUserIdList(CrmTypeEnum crmTypeEnum, CrmAuthEnum crmAuthEnum,List<Long> allUserIdList) {
        List<Long> authUserList = queryAuthUserList(crmTypeEnum, crmAuthEnum);
        authUserList.retainAll(allUserIdList);
        return authUserList;
    }

    public static boolean isReadFollowRecord(Integer crmType) {
        int menuId;
        switch (crmType) {
            case 1:
                menuId = 20;
                break;
            case 2:
                menuId = 29;
                break;
            case 3:
                menuId = 43;
                break;
            case 5:
                menuId = 49;
                break;
            case 6:
                menuId = 56;
                break;
            default:
                return false;
        }
        int followRecordReadMenuId = 441;
        return ApplicationContextHolder.getBean(CrmAuthMapper.class).queryReadFollowRecord(menuId, followRecordReadMenuId, UserUtil.getUserId()) > 0;
    }
}
