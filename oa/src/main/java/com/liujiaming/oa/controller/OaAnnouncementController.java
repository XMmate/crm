package com.liujiaming.oa.controller;


import com.liujiaming.core.common.R;
import com.liujiaming.core.common.Result;
import com.liujiaming.core.entity.BasePage;
import com.liujiaming.core.entity.PageEntity;
import com.liujiaming.oa.entity.PO.OaAnnouncement;
import com.liujiaming.oa.service.IOaAnnouncementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 公告表 前端控制器
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
@RestController
@RequestMapping("/oaAnnouncement")
@Api(tags = "oa公告")
public class OaAnnouncementController {

    @Autowired
    private IOaAnnouncementService oaAnnouncementService;

    @PostMapping("/addAnnouncement")
    @ApiOperation("添加公告")
    public Result addAnnouncement(@RequestBody OaAnnouncement oaAnnouncement){
        oaAnnouncementService.addOaAnnouncement(oaAnnouncement);
        return R.ok();
    }

    @PostMapping("/setAnnouncement")
    @ApiOperation("编辑公告")
    public Result setAnnouncement(@RequestBody OaAnnouncement oaAnnouncement){
        oaAnnouncementService.setOaAnnouncement(oaAnnouncement);
        return R.ok();
    }

    @PostMapping("/delete/{announcementId}")
    @ApiOperation("删除公告")
    public Result delete(@PathVariable @NotNull Integer announcementId){
        oaAnnouncementService.delete(announcementId);
        return R.ok();
    }

    @PostMapping("/queryById/{announcementId}")
    @ApiOperation("查看公告详细信息")
    public Result queryById(@PathVariable @NotNull Integer announcementId){
        return R.ok(oaAnnouncementService.queryById(announcementId));
    }

    @PostMapping("/queryList")
    @ApiOperation("查询公告列表")
    public Result<BasePage<OaAnnouncement>> queryList(PageEntity entity,Integer type){
        BasePage<OaAnnouncement> oaAnnouncementBasePage = oaAnnouncementService.queryList(entity, type);
        return R.ok(oaAnnouncementBasePage);
    }
}

