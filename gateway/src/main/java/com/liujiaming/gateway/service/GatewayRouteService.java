package com.liujiaming.gateway.service;


import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.gateway.entity.GatewayRoute;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.Collection;


/**
 * @author liujiaming
 * 网关的路由处理
 */
public interface GatewayRouteService extends BaseService<GatewayRoute> {

    /**
     * 删除路由
     * @param routerId 路由ID
     */
    public void removeRouter(String routerId);


    /**
     * 新增路由
     * @param routeDefinition 路由对象
     */
    public void saveRouter(RouteDefinition routeDefinition);

    /**
     * 获取路由列表
     */
    public Collection<RouteDefinition> getRouteDefinitions();

    /**
     * 从数据库重载配置
     */
    public void loadConfig();
}
