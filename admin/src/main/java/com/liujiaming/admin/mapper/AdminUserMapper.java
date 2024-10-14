package com.liujiaming.admin.mapper;

import com.liujiaming.admin.entity.BO.AdminUserBO;
import com.liujiaming.admin.entity.BO.UserBookBO;
import com.liujiaming.admin.entity.PO.AdminUser;
import com.liujiaming.admin.entity.VO.AdminUserVO;
import com.liujiaming.admin.entity.VO.HrmSimpleUserVO;
import com.liujiaming.admin.entity.VO.UserBookVO;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.entity.UserInfo;
import com.liujiaming.core.servlet.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-04-27
 */
public interface AdminUserMapper extends BaseMapper<AdminUser> {
    /**
     * 根据用户名查询
     *
     * @param username username
     * @return info
     */
    List<Map<String,Object>> findByUsername(@Param("username") String username);

    /**
     * 查询用户列表
     * @param adminUserBO data
     * @param page 分页参数
     * @return data
     */
    BasePage<AdminUserVO> queryUserList(BasePage<AdminUserVO> page, @Param("data") AdminUserBO adminUserBO);


    Integer countUserByLabel(@Param("label") Integer label,@Param("status") Integer status);
    /**
     * 查询通讯录
     * @param page
     * @param userBookBO
     * @return
     */
    BasePage<UserBookVO> queryListName(BasePage<UserBookVO> page, @Param("data")UserBookBO userBookBO);

    List<HrmSimpleUserVO> querySimpleUserByDeptId(Integer deptId);

    List<HrmSimpleUserVO> querySimpleUserByDeptIdAndExamine(Integer deptId);

    UserInfo queryLoginUserInfo(@Param("userId") Long userId);
}
