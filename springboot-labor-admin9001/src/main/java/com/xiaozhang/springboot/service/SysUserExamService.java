package com.xiaozhang.springboot.service;

import com.xiaozhang.springboot.domain.SysUserExam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhang.springboot.domain.postAnswer;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-24
 */
public interface SysUserExamService extends IService<SysUserExam> {

    Double getScore(SysUserExam sysUserExam);
}
