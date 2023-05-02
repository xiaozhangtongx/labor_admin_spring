package com.xiaozhang.springboot.service;

import com.xiaozhang.springboot.domain.SysUserExam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
public interface SysUserExamService extends IService<SysUserExam> {

    /**
     * 用户答题提交
     *
     * @param sysUserExam
     * @return
     */
    SysUserExam addExamAnswer(SysUserExam sysUserExam);

    /**
     * 用户进入考场
     *
     * @param sysUserExam
     * @return
     */
    boolean enterExam(SysUserExam sysUserExam);
}
