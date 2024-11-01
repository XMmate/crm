package com.liujiaming.hrm.mapper;

import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.hrm.entity.PO.HrmEmployeeField;
import com.liujiaming.hrm.entity.VO.EmployeeArchivesFieldVO;
import com.liujiaming.hrm.entity.VO.EmployeeHeadFieldVO;
import com.liujiaming.hrm.entity.VO.FiledListVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 自定义字段表 Mapper 接口
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
public interface HrmEmployeeFieldMapper extends BaseMapper<HrmEmployeeField> {

    /**
     * 查询表头展示id
     * @return
     */
    @Select("select field_id from `wk_hrm_employee_field` where is_head_field = 1 order by label_group,sorting")
    List<Integer> queryHeadFieldId();

    List<EmployeeHeadFieldVO> queryListHeads(Long userId);

    List<FiledListVO> queryFields();

    List<EmployeeArchivesFieldVO> queryEmployeeArchivesField();

}
