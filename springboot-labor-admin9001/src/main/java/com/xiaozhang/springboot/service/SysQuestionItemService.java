package com.xiaozhang.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaozhang.springboot.domain.SysQuestionItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 题目选项/内容表 服务类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
public interface SysQuestionItemService extends IService<SysQuestionItem> {

    /**
     * 获取问题选项
     *
     * @param questionId
     * @return
     */
    List<SysQuestionItem> getQuestionItem(String questionId);

    /**
     * 通过问题id删除子选项
     *
     * @param questionId
     * @return
     */
    boolean removeByQuestionId(String questionId);
}
