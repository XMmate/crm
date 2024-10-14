package com.liujiaming.oa.service;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.feign.admin.entity.SimpleUser;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.oa.entity.BO.LogBO;
import com.liujiaming.oa.entity.PO.OaLog;
import com.liujiaming.oa.entity.VO.OaBusinessNumVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工作日志表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface IOaLogService extends BaseService<OaLog> {
    /**
     * 分页查询日志列表
     * @param bo bo
     * @return data
     */
    public BasePage<JSONObject> queryList(LogBO bo);

    /**
     * 随机获取一条日志欢迎语
     * @return data
     */
    public String getLogWelcomeSpeech();

    /**
     * 查询日志统计信息
     * @return data
     */
    public JSONObject queryLogBulletin();

    /**
     * 查询日志完成情况统计
     */
    public JSONObject queryCompleteStats(Integer type);

    public BasePage<JSONObject> queryCompleteOaLogList(LogBO bo);

    public BasePage<SimpleUser> queryIncompleteOaLogList(LogBO bo);

    public void saveAndUpdate(JSONObject object);

    public void deleteById(Integer logId);

    public BasePage<JSONObject> queryLogBulletinByType(LogBO bo);

    public List<JSONObject> queryLogRecordCount(Integer logId, Integer today);

    JSONObject queryById(Integer logId);

    public List<Map<String, Object>> export(LogBO logBO);

    /**
     * app某个查询数量功能，没有名称
     */
    public OaBusinessNumVO queryOaBusinessNum();


}
