package com.xiaozhang.springboot.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaozhang.springboot.domain.SysFlowOvertime;
import com.xiaozhang.springboot.domain.SysQuestion;
import com.xiaozhang.springboot.domain.SysQuestionItem;
import com.xiaozhang.springboot.mapper.SysQuestionItemMapper;
import com.xiaozhang.springboot.mapper.SysQuestionMapper;
import com.xiaozhang.springboot.service.SysQuestionItemService;
import com.xiaozhang.springboot.service.SysQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 考试问题表 服务实现类
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-01
 */
@Service
@Slf4j
public class SysQuestionServiceImpl extends ServiceImpl<SysQuestionMapper, SysQuestion> implements SysQuestionService {

    @Autowired
    SysQuestionItemService sysQuestionItemService;

    @Override
    public SysQuestion getInfoById(String id) {
        SysQuestion sysQuestion = getById(id);

        List<SysQuestionItem> sysQuestionItems = sysQuestionItemService.getQuestionItem(id);

        if (sysQuestionItems.size() != 0) {
            sysQuestion.setSysQuestionItemList(sysQuestionItems);
        }

        return sysQuestion;
    }

    @Override
    public Boolean deleteById(String id) {

        // 首先删除问题选项
        boolean question_id = sysQuestionItemService.removeByQuestionId(id);

        boolean b = removeById(id);

        return question_id && b;
    }

    @Override
    public boolean editQuestion(SysQuestion sysQuestion) {

        // 首先删除问题选项
        boolean question_id = sysQuestionItemService.removeByQuestionId(sysQuestion.getId());

        // 然后插入问题子选项
        boolean b = sysQuestionItemService.saveBatch(sysQuestion.getSysQuestionItemList());

        // 最后更新
        boolean b1 = updateById(sysQuestion);

        return question_id && b && b1;
    }

    @Override
    public boolean addQuestion(SysQuestion sysQuestion) {

        String id = IdUtil.simpleUUID();

        List<SysQuestionItem> sysQuestionItemList = new ArrayList();

        for (SysQuestionItem sysQuestionItem : sysQuestion.getSysQuestionItemList()) {
            sysQuestionItem.setQuestionId(id);
            sysQuestionItem.setCreateTime(new Date());
            sysQuestionItemList.add(sysQuestionItem);
        }


        boolean b = sysQuestionItemService.saveBatch(sysQuestionItemList);

        sysQuestion.setId(id);
        sysQuestion.setCreateTime(new Date());

        boolean save = save(sysQuestion);

        return b && save;
    }
}
