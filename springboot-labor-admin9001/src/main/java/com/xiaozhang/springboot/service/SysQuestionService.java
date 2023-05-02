package com.xiaozhang.springboot.service;

import com.xiaozhang.springboot.domain.SysQuestion;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 考试问题表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-01
 */
public interface SysQuestionService extends IService<SysQuestion> {

    /**
     * 根据id获取问题的信息
     *
     * @param id
     * @return
     */
    SysQuestion getInfoById(String id);

    /**
     * 根据id删除数据
     *
     * @param id
     * @return
     */
    Boolean deleteById(String id);

    /**
     * 修改问题
     *
     * @param sysQuestion
     * @return
     */
    boolean editQuestion(SysQuestion sysQuestion);

    /**
     * 添加问题
     *
     * @param sysQuestion
     * @return
     */
    boolean addQuestion(SysQuestion sysQuestion);
}
