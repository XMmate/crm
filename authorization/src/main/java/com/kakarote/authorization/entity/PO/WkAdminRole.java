package com.kakarote.authorization.entity.PO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author author
 * @since 2024-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_admin_role")
public class WkAdminRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    /**
     * 名称
     */
    private String roleName;

    /**
     * 0、自定义角色1、管理角色 2、客户管理角色 3、人事角色 4、财务角色 5、项目角色 8、项目自定义角色
     */
    private Integer roleType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 1 启用 0 禁用
     */
    private Integer status;

    /**
     * 数据权限 1、本人，2、本人及下属，3、本部门，4、本部门及下属部门，5、全部 
     */
    private Integer dataType;

    /**
     * 0 隐藏 1 不隐藏
     */
    private Integer isHidden;

    /**
     * 1 系统项目管理员角色 2 项目管理角色 3 项目编辑角色 4 项目只读角色
     */
    private Integer label;


}
