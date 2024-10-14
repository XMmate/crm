package com.liujiaming.hrm.service;

import com.liujiaming.core.servlet.BaseService;
import com.liujiaming.hrm.entity.BO.QueryNotesStatusBO;
import com.liujiaming.hrm.entity.PO.HrmNotes;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 备忘 服务类
 * </p>
 *
 * @author liujiaming
 * @since 2024-07-13
 */
public interface IHrmNotesService extends BaseService<HrmNotes> {

    boolean removeById(String notesId);

    /**
     * 添加备忘
     * @param notes
     */
    void addNotes(HrmNotes notes);

    /**
     * 查询备忘录列表
     * @param time
     * @param employeeIds
     * @return
     */
    List<HrmNotes> queryNoteListByTime(Date time, Collection<Integer> employeeIds);

    /**
     * 查询备忘录状态
     * @param queryNotesStatusBO
     * @param employeeIds
     * @return
     */
    Set<String> queryNoteStatusList(QueryNotesStatusBO queryNotesStatusBO, Collection<Integer> employeeIds);
}
