package com.kakarote.oa.service;

import com.kakarote.core.servlet.BaseService;
import com.kakarote.oa.entity.PO.OaLogRule;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *

 */
public interface IOaLogRuleService extends BaseService<OaLogRule> {
    /**
     * 查询日志提交规则设置列表
     * @return
     */
    List<OaLogRule> queryOaLogRuleList();

    /**
     * 设置日志提交规则设置列表
     * @param ruleList
     */

    void setOaLogRule(List<OaLogRule> ruleList);
}
