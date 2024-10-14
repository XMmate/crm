package com.liujiaming.work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.core.utils.UserUtil;
import com.liujiaming.work.entity.PO.WorkCollect;
import com.liujiaming.work.mapper.WorkCollectMapper;
import com.liujiaming.work.service.IWorkCollectService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目收藏表 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-05-15
 */
@Service
public class WorkCollectServiceImpl extends BaseServiceImpl<WorkCollectMapper, WorkCollect> implements IWorkCollectService {


    /**
     * 收藏和取消收藏
     * @param workId
     */
    @Override
    public void collect(Integer workId) {
        Long userId = UserUtil.getUserId();
        Integer count = count(new QueryWrapper<WorkCollect>().eq("user_id",userId).eq("work_id",workId));
        if (count == 0) {
            WorkCollect workCollect = new WorkCollect();
            workCollect.setWorkId(workId);
            workCollect.setUserId(userId);
            save(workCollect);
        } else {
            remove(new QueryWrapper<WorkCollect>().eq("user_id",userId).eq("work_id",workId));
        }
    }
}
