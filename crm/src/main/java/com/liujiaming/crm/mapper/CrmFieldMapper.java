package com.liujiaming.crm.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.liujiaming.core.servlet.BaseMapper;
import com.liujiaming.crm.entity.BO.CrmFieldDataBO;
import com.liujiaming.crm.entity.PO.CrmField;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 自定义字段表 Mapper 接口
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-19
 */
public interface CrmFieldMapper extends BaseMapper<CrmField> {

    /**
     * 验证固定字段是否存在
     * @param tableName 表名
     * @param fieldName 字段
     * @param value 值
     * @param batchId batchId
     * @param label 类型
     * @return num
     */
    public Integer verifyFixedField(@Param("tableName") String tableName, @Param("fieldName") String fieldName,
                                @Param("value") String value, @Param("batchId") String batchId,@Param("label") Integer label);

    /**
     * 验证自定义字段是否存在
     * @param tableName 表名
     * @param fieldId 字段Id
     * @param value 值
     * @param batchId batchId
     * @return num
     */
    public Integer verifyField(@Param("tableName") String tableName, @Param("fieldId") Integer fieldId,
                                    @Param("value") String value, @Param("batchId") String batchId);

    /**
     * 更新自定义字段
     * @return 更新条数
     */
    @SqlParser(filter = true)
    public Integer dataCheck(@Param("name")String name,@Param("label")Integer label,@Param("type")Integer type);

    @SqlParser(filter = true)
    public List<Map<String,Object>> initData(Map<String,Object> map);

    @SqlParser(filter = true)
    public List<CrmFieldDataBO> initFieldData(@Param("lastId") Integer lastId, @Param("primaryKey") String primaryKey, @Param("tableName") String tableName);

    Integer queryCustomerFieldDuplicateByFixed(@Param("name") String name,@Param("value") Object value);

}
