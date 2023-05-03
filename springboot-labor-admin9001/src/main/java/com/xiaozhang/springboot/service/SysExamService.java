package com.xiaozhang.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.domain.SysExam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * <p>
 * 考试表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
public interface SysExamService extends IService<SysExam> {

    /**
     * 获取试卷数据
     *
     * @param id
     * @return
     */
    SysExam getInfoById(String id);

    /**
     * 更新试卷状态
     *
     * @param now
     */
    void updateExamPaperList(Date now);

    /**
     * 删除试卷
     *
     * @param id
     * @return
     */
    Boolean deleteById(String id);

    /**
     * 创建试卷
     *
     * @param sysExam
     * @return
     */
    boolean addExam(SysExam sysExam);

    /**
     * 更新试卷
     *
     * @param sysExam
     * @return
     */
    boolean editExam(SysExam sysExam);
}
