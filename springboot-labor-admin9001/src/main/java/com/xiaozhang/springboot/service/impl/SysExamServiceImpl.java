package com.xiaozhang.springboot.service.impl;

import com.xiaozhang.springboot.domain.SysExam;
import com.xiaozhang.springboot.mapper.SysExamMapper;
import com.xiaozhang.springboot.service.SysExamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 考试表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-24
 */
@Service
public class SysExamServiceImpl extends ServiceImpl<SysExamMapper, SysExam> implements SysExamService {

    @Autowired
    SysExamMapper examMapper;

    @Override
    public List<SysExam> getList() {
        return examMapper.selectMyList();
    }
}
