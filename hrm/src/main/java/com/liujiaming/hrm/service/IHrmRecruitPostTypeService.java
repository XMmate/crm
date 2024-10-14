package com.liujiaming.hrm.service;

import com.alicp.jetcache.anno.Cached;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.PO.HrmRecruitPostType;

import java.util.List;

/**
 * <p>
 * 职位类型 服务类
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
public interface IHrmRecruitPostTypeService extends BaseService<HrmRecruitPostType> {

    /**
     * 查询职位类型列表
     * @return
     */
    @Cached(name = "queryPostType")
    List<HrmRecruitPostType> queryPostType();
}
