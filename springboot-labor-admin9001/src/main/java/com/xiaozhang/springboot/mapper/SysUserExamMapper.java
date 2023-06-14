package com.xiaozhang.springboot.mapper;

import com.xiaozhang.springboot.domain.SysUserExam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-05-02
 */
@Mapper
public interface SysUserExamMapper extends BaseMapper<SysUserExam> {

    List<SysUserExam>  selectRecords(@Param("userId") String userId);
}
