package com.xiaozhang.springboot.service.impl;

import com.xiaozhang.springboot.domain.SysQuestion;
import com.xiaozhang.springboot.mapper.SysQuestionMapper;
import com.xiaozhang.springboot.service.SysQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 考试问题表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-24
 */
@Service
public class SysQuestionServiceImpl extends ServiceImpl<SysQuestionMapper, SysQuestion> implements SysQuestionService {

}
