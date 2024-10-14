package com.liujiaming.oa.service.impl;

import com.liujiaming.core.feign.admin.entity.SimpleUser;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.core.utils.TagUtil;
import com.liujiaming.core.utils.UserCacheUtil;
import com.liujiaming.oa.entity.PO.OaLogRule;
import com.liujiaming.oa.mapper.OaLogRuleMapper;
import com.liujiaming.oa.service.IOaLogRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
@Service
public class OaLogRuleServiceImpl extends BaseServiceImpl<OaLogRuleMapper, OaLogRule> implements IOaLogRuleService {


    /*
    查询日志提交规则设置列表
     */
    @Override
    public List<OaLogRule> queryOaLogRuleList() {
        List<OaLogRule> list = list();
        for (OaLogRule oaLogRule : list) {
            String memberUserId = oaLogRule.getMemberUserId();
            List<SimpleUser> data = UserCacheUtil.getSimpleUsers(TagUtil.toLongSet(memberUserId));
            oaLogRule.setMemberUser(data);
        }
        return list;
    }


    /**
     * 设置日志提交规则设置列表
     * @param ruleList
     */
    @Override
    public void setOaLogRule(List<OaLogRule> ruleList) {
        updateBatchById(ruleList);
    }
}
