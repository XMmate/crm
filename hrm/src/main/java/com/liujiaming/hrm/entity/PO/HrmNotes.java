package com.liujiaming.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 备忘
 * </p>
 *
 * @author liujiaming
 * @since 2024-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_notes")
@ApiModel(value="HrmNotes对象", description="备忘录")
public class HrmNotes implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "notes_id", type = IdType.AUTO)
    private Integer notesId;

    @ApiModelProperty(value = "备忘录内容")
    private String content;

    @ApiModelProperty(value = "提醒时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reminderTime;

    @ApiModelProperty(value = "员工ID")
    private Integer employeeId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "创建用户ID")
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;




}
