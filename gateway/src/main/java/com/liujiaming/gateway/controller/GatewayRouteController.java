package com.liujiaming.gateway.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liujiaming.core.common.Result;
import com.liujiaming.gateway.service.GatewayRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/gateway")
public class GatewayRouteController {

    @Autowired
    private GatewayRouteService gatewayRouteService;

    @Value("${upgradeFile.url}")
    private String fileUrl;

    @RequestMapping("/")
    public Result index(){
        return Result.ok();
    }


    /**
     * 重载配置
     * @return
     */
    @RequestMapping("/reloadConfig")
    public Result reloadConfig(){
        gatewayRouteService.loadConfig();
        return Result.ok();
    }


    /**
     * 查询升级
     * @return
     */
    @RequestMapping("/queryUpgradeFile")
    public Result<JSONObject> queryUpgradeFile(){
        if (FileUtil.exist(fileUrl)) {
            FileReader fileReader = new FileReader(fileUrl);
            String str = fileReader.readString();
            if (StrUtil.isNotEmpty(str)) {
                JSONObject json = JSON.parseObject(str);
                return Result.ok(json);
            }
        }
        return Result.ok(new JSONObject());
    }

}
