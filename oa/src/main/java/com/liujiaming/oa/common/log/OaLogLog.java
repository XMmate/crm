package com.liujiaming.oa.common.log;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.common.log.BehaviorEnum;
import com.liujiaming.core.common.log.Content;
import com.liujiaming.core.servlet.ApplicationContextHolder;
import com.liujiaming.oa.entity.PO.OaLog;
import com.liujiaming.oa.service.IOaLogService;

import java.util.List;

public class OaLogLog {

    private IOaLogService oaLogService = ApplicationContextHolder.getBean(IOaLogService.class);
    private SysLogUtil sysLogUtil = ApplicationContextHolder.getBean(SysLogUtil.class);




    public Content addOrUpdate(JSONObject jsonObject) {
        OaLog oaLog = jsonObject.toJavaObject(OaLog.class);
        if (oaLog.getLogId() != null) {
            OaLog oldLog = oaLogService.getById(oaLog.getLogId());
            List<String> list = sysLogUtil.searchChange(BeanUtil.beanToMap(oldLog), BeanUtil.beanToMap(oaLog), "log");
            return new Content("","修改了日志:"+ StrUtil.join("",list), BehaviorEnum.SAVE);
        } else {
            return new Content("","新增了日志", BehaviorEnum.SAVE);
        }
    }
}
