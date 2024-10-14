package com.liujiaming.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.liujiaming.admin.entity.BO.AdminLanguagePackBO;
import com.liujiaming.admin.entity.PO.AdminLanguagePack;
import com.liujiaming.admin.entity.VO.AdminLanguagePackVO;
import com.liujiaming.core.common.Result;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.servlet.BaseService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 * 语言包表 服务类
 * </p>
 *
 * @author zmj
 * @since 2024-12-01
 */
public interface IAdminLanguagePackService extends BaseService<AdminLanguagePack> {

    /**
     * 查询语言包列表
     */
    public BasePage<AdminLanguagePackVO> queryLanguagePackList(AdminLanguagePackBO adminLanguagePackBO, Integer systemOrUser);

    /**
     * 新增语言包列表
     * @param adminLanguagePackBO adminUserBO
     */
    public Result addOrUpdateLanguagePack(MultipartFile file, AdminLanguagePackBO adminLanguagePackBO);

    /**
     * 删除语言包
     * @param id id
     */
    public void deleteLanguagePackById(Integer id);

    /**
     * 导出语言包
     * @param id id
     */
    public void exportLanguagePackById(Integer id, HttpServletResponse response);

    /**
     * 删除语言包
     * @param id id
     */
    public String queryLanguagePackContextById(Integer id);

    /**
     * 导出模板
     * @param response response
     */
    public void downloadExcel(HttpServletResponse response);

    /**
     * 导出模板
     * @param adminLanguagePackBO adminLanguagePackBO
     */
    public void updateLanguagePackNameById(AdminLanguagePackBO adminLanguagePackBO);

    /**
     * 修改默认语言包配置
     * @param id id
     */
    public void setDeflautLanguagePackSetting(Integer id, Integer systemOrUser);

    /**
     * 查询默认语言包配置
     */
    public JSONObject queryDeflautLanguagePackSetting(Integer systemOrUser);

}
