package com.xiaozhang.springboot.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaozhang.springboot.domain.SysUploadUnsafe;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.mapper.SysUploadUnsafeMapper;
import com.xiaozhang.springboot.mapper.SysUserMapper;
import com.xiaozhang.springboot.service.SysUploadUnsafeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 不安全行为上传 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-21
 */
@Service
public class SysUploadUnsafeServiceImpl extends ServiceImpl<SysUploadUnsafeMapper, SysUploadUnsafe> implements SysUploadUnsafeService {

    @Autowired
    SysUploadUnsafeMapper sysUploadUnsafeMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public boolean saveUrls(SysUploadUnsafe sysUploadUnsafe, List<String> urls) {

        SysUser labor=sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("phone_num",sysUploadUnsafe.getPhoneNum()));

        sysUploadUnsafe.setLaborId(labor.getId());

        sysUploadUnsafe.setImgUrl(String.join(";",urls));

        sysUploadUnsafe.setTitle("xxx组今日安全公告");

        sysUploadUnsafe.setCreateTime(new Date());

        Integer result=sysUploadUnsafeMapper.insert(sysUploadUnsafe);

        return result==1?true:false;
    }
}
