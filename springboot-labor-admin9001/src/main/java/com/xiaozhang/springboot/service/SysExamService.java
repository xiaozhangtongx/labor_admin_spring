package com.xiaozhang.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.domain.SysExam;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
