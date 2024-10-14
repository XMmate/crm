package com.liujiaming.hrm.service.impl;

import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.core.utils.RecursionUtil;
import com.liujiaming.hrm.entity.PO.HrmRecruitPostType;
import com.liujiaming.hrm.mapper.HrmRecruitPostTypeMapper;
import com.liujiaming.hrm.service.IHrmRecruitPostTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 职位类型 服务实现类
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
@Service
public class HrmRecruitPostTypeServiceImpl extends BaseServiceImpl<HrmRecruitPostTypeMapper, HrmRecruitPostType> implements IHrmRecruitPostTypeService {

    @Override
    public List<HrmRecruitPostType> queryPostType() {
        List<HrmRecruitPostType> list = list();
        List<HrmRecruitPostType> childListTree = RecursionUtil.getChildListTree(list, "parentId", 0, "id", "children", HrmRecruitPostType.class);
        return childListTree;
    }
}
