package com.liujiaming.crm.service;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.crm.constant.CrmEnum;
import com.liujiaming.crm.entity.BO.CrmSceneConfigBO;
import com.liujiaming.crm.entity.PO.CrmScene;
import com.liujiaming.crm.entity.VO.CrmModelFiledVO;

import java.util.List;

/**
 * <p>
 * 场景 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-06-06
 */
public interface ICrmSceneService extends BaseService<CrmScene> {

    /**
     * 查询场景
     *
     * @param crmEnum 类型
     * @return data
     */
    public List<CrmScene> queryScene(CrmEnum crmEnum);

    /**
     * 查询场景处字段
     * @param label label
     * @return data
     */
    public List<CrmModelFiledVO> queryField(Integer label);

    /**
     * 新增场景
     * @param crmScene data
     */
    public void addScene(CrmScene crmScene);

    /**
     * 修改场景
     * @param crmScene data
     */
    public void updateScene(CrmScene crmScene);

    /**
     * 保存默认场景
     * @param sceneId sceneId
     */
    public void setDefaultScene(Integer sceneId);

    /**
     * 删除场景
     * @param sceneId sceneId
     */
    public void deleteScene(Integer sceneId);

    /**
     * 查询场景设置
     * @param type type
     * @return data
     */
    public JSONObject querySceneConfig(Integer type);

    /**
     * 设置场景
     */
    public void sceneConfig(CrmSceneConfigBO config);
}
