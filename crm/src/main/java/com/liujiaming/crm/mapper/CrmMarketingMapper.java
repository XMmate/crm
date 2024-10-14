package com.liujiaming.crm.mapper;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.crm.entity.PO.CrmMarketing;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 营销表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-08-12
 */
public interface CrmMarketingMapper extends BaseMapper<CrmMarketing> {

    BasePage<CrmMarketing> queryPageList(BasePage<Object> parse, @Param("userIds") List<Long> userIds,
                                         @Param("deptIds") List<Integer> deptIds,
                                         @Param("crmType") Integer crmType,
                                         @Param("search") String search,@Param("isAdmin") boolean isAdmin,
                                         @Param("timeType") Integer timeType,@Param("status") Integer status);

    BasePage<JSONObject> census(BasePage<Object> parse,@Param("marketingId") Integer marketingId,@Param("userIds") List<Long> userIds,@Param("status") Integer status);
}
