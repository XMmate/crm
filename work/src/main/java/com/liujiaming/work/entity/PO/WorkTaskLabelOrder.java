package com.liujiaming.work.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 项目标签排序表
 * </p>
 *
 * @author liujiaming
 * @since 2024-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_work_task_label_order")
@ApiModel(value="WorkTaskLabelOrder对象", description="项目标签排序表")
public class WorkTaskLabelOrder implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "order_id", type = IdType.AUTO)
    private Integer orderId;

    private Integer labelId;
    /**
     * 标签是每个用户独有的，每个用户都可以任务打标签
     */
    private Long userId;

    private Integer orderNum;

}
