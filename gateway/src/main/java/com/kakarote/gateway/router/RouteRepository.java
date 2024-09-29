package com.kakarote.gateway.router;

import com.kakarote.gateway.service.GatewayRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author liujiaming
 * 路由配置工厂  提供动态路由功能
 * 实现了 RouteDefinitionRepository 接口，负责管理 Spring Cloud Gateway 的路由定义
 */
@Component
public class RouteRepository implements RouteDefinitionRepository {

    @Autowired
    private GatewayRouteService gatewayRouteService;


    /**
     *  从自定义数据源获取路由定义
     * @return
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(gatewayRouteService.getRouteDefinitions());
    }

    /**
     * 保存提交的路由信息
     * @param route
     * @return
     */
    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(routeDefinition -> {
            gatewayRouteService.saveRouter(routeDefinition);
            return Mono.empty();
        });
    }


    /**
     * 删除路由信息
     * @param routeId
     * @return
     */
    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            gatewayRouteService.removeRouter(id);
            return Mono.empty();
        });
    }
}
