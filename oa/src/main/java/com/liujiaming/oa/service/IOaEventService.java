package com.liujiaming.oa.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.oa.entity.BO.*;
import com.liujiaming.oa.entity.BO.*;
import com.liujiaming.oa.entity.PO.OaEvent;
import com.liujiaming.oa.entity.VO.QueryEventByIdVO;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 日程表 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
public interface IOaEventService extends BaseService<OaEvent> {

    void saveEvent(SetEventBO setEventBO);

    void updateEvent(SetEventBO setEventBO);

    void delete(DeleteEventBO deleteEventBO);

    List<OaEventDTO> queryList(QueryEventListBO queryEventListBO);

    Set<String> queryListStatus(QueryEventListBO queryEventListBO);

    QueryEventByIdVO queryById(QueryEventByIdBO queryEventByIdBO);

    void eventNotice(List<OaEvent> oaEventList);
}
