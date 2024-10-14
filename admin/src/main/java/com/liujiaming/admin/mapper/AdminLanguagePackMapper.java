package com.liujiaming.admin.mapper;

import com.liujiaming.admin.entity.PO.AdminLanguagePack;
import com.liujiaming.admin.entity.VO.AdminLanguagePackVO;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;

/**
 * <p>
 * 语言包表 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2024-12-02
 */
public interface AdminLanguagePackMapper extends BaseMapper<AdminLanguagePack> {

    /**
     * 查询用户列表
     * @param page 分页参数
     * @return data
     */
    BasePage<AdminLanguagePackVO> queryLanguagePackList(BasePage<AdminLanguagePackVO> page);
}
