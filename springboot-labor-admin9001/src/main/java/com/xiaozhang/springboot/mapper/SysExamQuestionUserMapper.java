package com.xiaozhang.springboot.mapper;

import com.xiaozhang.springboot.domain.SysExamQuestionUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户答题表 Mapper 接口
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
@Mapper
public interface SysExamQuestionUserMapper extends BaseMapper<SysExamQuestionUser> {

}
