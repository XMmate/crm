package com.liujiaming.hrm.mapper;

import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.BO.QueryRecruitPostPageListBO;
import com.liujiaming.hrm.entity.PO.HrmRecruitPost;
import com.liujiaming.hrm.entity.VO.RecruitPostVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 招聘职位表 Mapper 接口
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
public interface HrmRecruitPostMapper extends BaseMapper<HrmRecruitPost> {

    RecruitPostVO queryById(Integer postId);


    BasePage<RecruitPostVO> queryRecruitPostPageList(BasePage<RecruitPostVO> parse,@Param("data") QueryRecruitPostPageListBO queryRecruitPostPageListBO,
                                                     @Param("deptIds") Collection<Integer> deptIds);

    List<Integer> queryPostStatusList(@Param("deptIds") Collection<Integer> deptIds);

    void setRecruitPost(HrmRecruitPost recruitPost);
}
