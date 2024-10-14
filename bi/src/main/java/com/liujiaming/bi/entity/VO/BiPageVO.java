package com.liujiaming.bi.entity.VO;

import com.liujiaming.core.entity.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author admin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BiPageVO<T> extends BasePage<T> {

    private Object duration;

    private Object money;
}
