package com.xiaozhang.springboot.service;

import com.xiaozhang.springboot.domain.SysExam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 考试表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-24
 */
public interface SysExamService extends IService<SysExam> {

     List<SysExam> getList();
}
