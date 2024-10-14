package com.liujiaming.authorization.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liujiaming.authorization.entity.PO.WkAdminAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-09-23
 */
@Mapper
public interface WkAdminAuthMapper extends BaseMapper<WkAdminAuth> {
    List<String> queryAuthNamesByUserId(@Param("userId") Long userId);

    String test(int i);
}
