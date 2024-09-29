package com.kakarote.crm.entity.BO;

import com.kakarote.core.feign.examine.entity.ExamineRecordSaveBO;
import com.kakarote.crm.entity.PO.CrmContractProduct;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liujiaming
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@ApiModel("crm合同保存对象")
public class CrmContractSaveBO extends CrmModelSaveBO {
    @ApiModelProperty("合同关联产品列表")
    private List<CrmContractProduct> product;

    @ApiModelProperty("联系人ID")
    private Integer contactsId;

    @ApiModelProperty("审核人ID")
    private Long checkUserId;

    @ApiModelProperty("审批数据")
    private ExamineRecordSaveBO examineFlowData;

    public @NotNull ExamineRecordSaveBO getExamineFlowData() {
        if(examineFlowData != null) {
            return examineFlowData;
        }
        return new ExamineRecordSaveBO();
    }
}
