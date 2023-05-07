package com.xiaozhang.springboot.service;

import com.xiaozhang.springboot.domain.SysStudyData;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-07
 */
public interface SysStudyDataService extends IService<SysStudyData> {

    /**
     * 删除学习资料
     *
     * @param id
     * @return
     */
    Boolean deleteById(String id);
}
