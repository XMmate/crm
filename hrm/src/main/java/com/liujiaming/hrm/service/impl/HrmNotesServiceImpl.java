package com.liujiaming.hrm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liujiaming.core.common.SystemCodeEnum;
import com.liujiaming.core.entity.UserInfo;
import com.liujiaming.core.exception.CrmException;
import com.liujiaming.core.servlet.BaseServiceImpl;
import com.liujiaming.core.utils.UserUtil;
import com.liujiaming.hrm.common.EmployeeHolder;
import com.liujiaming.hrm.entity.BO.QueryNotesStatusBO;
import com.liujiaming.hrm.entity.PO.HrmNotes;
import com.liujiaming.hrm.mapper.HrmNotesMapper;
import com.liujiaming.hrm.service.IHrmNotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 备忘 服务实现类
 * </p>
 *
 * @author liujiaming
 * @since 2024-07-13
 */
@Service
public class HrmNotesServiceImpl extends BaseServiceImpl<HrmNotesMapper, HrmNotes> implements IHrmNotesService {

    @Autowired
    private HrmNotesMapper notesMapper;


    /**
     * 根据ID删除备忘录（需要校验用户）
     * @param notesId
     * @return
     */
    @Override
    public boolean removeById(String notesId) {
        UserInfo user = UserUtil.getUser();
        Long userId = user.getUserId();
        LambdaQueryWrapper<HrmNotes> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HrmNotes::getNotesId,notesId).eq(HrmNotes::getEmployeeId,userId);
        // 调用 remove 方法删除满足条件的记录
        boolean remove = remove(queryWrapper);
        if (!remove){
           throw  new CrmException(SystemCodeEnum.No_Permission_To_Operate_This_Data);
        }
        return true;
    }

    @Override
    public void addNotes(HrmNotes notes) {
        notes.setEmployeeId(EmployeeHolder.getEmployeeId());
        save(notes);
    }

    @Override
    public List<HrmNotes> queryNoteListByTime(Date time, Collection<Integer> employeeIds) {
        return notesMapper.queryNoteListByTime(time,employeeIds);
    }

    @Override
    public Set<String> queryNoteStatusList(QueryNotesStatusBO queryNotesStatusBO, Collection<Integer> employeeIds) {
        return notesMapper.queryNoteStatusList(queryNotesStatusBO,employeeIds);
    }
}
