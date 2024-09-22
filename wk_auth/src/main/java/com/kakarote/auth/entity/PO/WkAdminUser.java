package com.kakarote.auth.entity.PO;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kakarote.core.entity.UserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author author
 * @since 2024-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_admin_user")

public class WkAdminUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 安全符
     */
    private String salt;

    /**
     * 头像
     */
    private String img;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 员工编号
     */
    private String num;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 0 未选择 1 男 2 女 
     */
    private Integer sex;

    /**
     * 部门
     */
    private Integer deptId;

    /**
     * 岗位
     */
    private String post;

    /**
     * 状态,0禁用,1正常,2未激活
     */
    private Integer status;

    /**
     * 直属上级ID
     */
    private Long parentId;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP 注意兼容IPV6
     */
    private String lastLoginIp;

    private Long oldUserId;

    /**
     * 是否删除 0 未删除 1 已删除
     */
    private Integer isDel;


    public String toJSON() {
        return JSON.toJSONString(this);
    }

    public UserInfo toUserInfo() {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(this,userInfo);
        return userInfo;
    }
}
