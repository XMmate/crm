package com.liujiaming.crm.mapper;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.crm.common.CrmModel;
import com.liujiaming.crm.entity.PO.CrmContacts;
import com.liujiaming.crm.entity.VO.CrmInfoNumVO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 联系人表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
public interface CrmContactsMapper extends BaseMapper<CrmContacts> {

    /**
     * 通过id查询联系人数据
     *
     * @param id id
     * @param userId 用户ID
     * @return data
     */
    public CrmModel queryById(@Param("id") Integer id, @Param("userId") Long userId);

    /**
     * 查询详情页数量
     * @param map map
     */
    public CrmInfoNumVO queryNum(Map<String,Object> map);

    BasePage<Map<String, Object>> queryBusiness(BasePage<Object> parse,@Param("contactsId") Integer contactsId);
}
