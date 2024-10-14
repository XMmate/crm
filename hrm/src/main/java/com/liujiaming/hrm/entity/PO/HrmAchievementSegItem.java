package com.liujiaming.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 考核项选项
 * </p>
 *
 * @author huangmingbo
 * @since 2024-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_achievement_seg_item")
@ApiModel(value="HrmAchievementSegItem对象", description="子考核项内容")
public class HrmAchievementSegItem implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "item_id", type = IdType.AUTO)
    @ApiModelProperty(value = "考核子项id")
    private Integer itemId;

    @ApiModelProperty(value = "考核项id")
    private Integer segId;

    @ApiModelProperty(value = "选项名称")
    private String itemName;

    @ApiModelProperty(value = "排序")
    private Integer sort;




}
