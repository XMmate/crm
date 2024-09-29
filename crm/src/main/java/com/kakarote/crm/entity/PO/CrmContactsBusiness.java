package com.kakarote.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.elasticsearch.common.recycler.Recycler;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * <p>
 * 商机联系人关联表
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_contacts_business")
@ApiModel(value="CrmContactsBusiness对象", description="商机联系人关联表")
public class CrmContactsBusiness implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
   @ApiModelProperty(value = "商机id")
    private Integer businessId;
    @ApiModelProperty(value = "联系人id")
    private Integer contactsId;



}
