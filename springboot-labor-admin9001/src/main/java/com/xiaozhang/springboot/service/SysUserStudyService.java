package com.xiaozhang.springboot.service;

import cn.hutool.core.date.DateTime;
import com.xiaozhang.springboot.domain.SysStudyData;
import com.xiaozhang.springboot.domain.SysUserStudy;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户学习情况表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-28
 */
public interface SysUserStudyService extends IService<SysUserStudy> {

    List<SysStudyData> getCount(SysUserStudy sysUserStudy, DateTime start, DateTime end);
}
