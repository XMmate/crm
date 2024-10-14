package com.liujiaming.work.controller;


import com.liujiaming.core.common.R;
import com.liujiaming.core.common.Result;
import com.liujiaming.core.common.SystemCodeEnum;
import com.liujiaming.core.exception.CrmException;
import com.liujiaming.core.utils.TagUtil;
import com.liujiaming.core.utils.UserUtil;
import com.liujiaming.work.common.WorkAuthUtil;
import com.liujiaming.work.common.WorkCodeEnum;
import com.liujiaming.work.entity.PO.Work;
import com.liujiaming.work.entity.PO.WorkTask;
import com.liujiaming.work.entity.PO.WorkTaskComment;
import com.liujiaming.work.service.IWorkService;
import com.liujiaming.work.service.IWorkTaskCommentService;
import com.liujiaming.work.service.IWorkTaskService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 任务评论表 前端控制器
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-18
 */
@RestController
@RequestMapping("/workTaskComment")
public class WorkTaskCommentController {

    @Autowired
    private WorkAuthUtil workAuthUtil;

    @Autowired
    private IWorkTaskService workTaskService;

    @Autowired
    private IWorkService workService;

    @Autowired
    private IWorkTaskCommentService workTaskCommentService;


    /**
     *
     * @param typeId typeId是任务i
     * @param type   1：任务评论  2：日志评论
     * @return
     */
    @ApiOperation("查询评论列表")
    @PostMapping("/queryCommentList")
    public Result<List<WorkTaskComment>> queryCommentList(@ApiParam("类型ID") @RequestParam("typeId") Integer typeId, @ApiParam("类型") @RequestParam("type") Integer type) {
        if (!UserUtil.isAdmin() && !UserUtil.getUser().getRoles().contains(workAuthUtil.getWorkAdminRole())) {
            if (Objects.equals(1, type)) {
                WorkTask task = workTaskService.getById(typeId);
                boolean auth = true;
                if (Objects.equals(0, task.getWorkId())) {
                    auth = workAuthUtil.isOaAuth(1, typeId);
                } else {
                    Work work = workService.getById(task.getWorkId());
                    if (TagUtil.toLongSet(work.getOwnerUserId()).contains(UserUtil.getUserId()) || work.getIsOpen() == 1) {
                        auth = false;
                    }
                }
                if (auth) {
                    return R.error(SystemCodeEnum.SYSTEM_NO_AUTH);
                }
            }
        }
        List<WorkTaskComment> taskComments = workTaskCommentService.queryCommentList(typeId, type);
        return R.ok(taskComments);
    }

    /**
     * @param comment 评论对象
     * @author hmb
     * 添加评论或者修改
     */
    @PostMapping("/setComment")
    @ApiOperation("添加评论或者修改")
    public Result setComment(@RequestBody WorkTaskComment comment) {
        if (comment.getType() == 1) {
            if (!workAuthUtil.isTaskAuth(comment.getTypeId())) {
                throw new CrmException(WorkCodeEnum.WORK_AUTH_ERROR);
            }
        }
        workTaskCommentService.setComment(comment);
        return R.ok(comment);
    }

    @PostMapping("/deleteComment")
    @ApiOperation("删除评论")
    public Result deleteComment(@RequestParam("commentId") Integer commentId) {
        WorkTaskComment comment = workTaskCommentService.getById(commentId);
        if (comment != null) {
            if (comment.getType() == 1) {
                if (!workAuthUtil.isTaskAuth(comment.getTypeId())) {
                    throw new CrmException(WorkCodeEnum.WORK_AUTH_ERROR);
                }
            }
            workTaskCommentService.removeById(commentId);
            workTaskCommentService.lambdaUpdate().eq(WorkTaskComment::getMainId,commentId).remove();
        }
        return R.ok();
    }
}

