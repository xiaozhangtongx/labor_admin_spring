package com.xiaozhang.springboot.service.impl;

import com.xiaozhang.springboot.domain.SysLog;
import com.xiaozhang.springboot.mapper.SysLogMapper;
import com.xiaozhang.springboot.service.SysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-16
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

}
