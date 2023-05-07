package com.xiaozhang.springboot.service.impl;

import com.xiaozhang.springboot.domain.SysStudyData;
import com.xiaozhang.springboot.mapper.SysStudyDataMapper;
import com.xiaozhang.springboot.service.SysStudyDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-07
 */
@Service
public class SysStudyDataServiceImpl extends ServiceImpl<SysStudyDataMapper, SysStudyData> implements SysStudyDataService {

    @Autowired(required = false)
    SysStudyDataMapper sysStudyDataMapper;

    @Override
    public Boolean deleteById(String id) {
        return sysStudyDataMapper.deleteById(id) > 0;
    }
}
