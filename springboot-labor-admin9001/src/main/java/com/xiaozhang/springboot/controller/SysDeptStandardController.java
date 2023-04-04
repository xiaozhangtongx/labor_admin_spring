package com.xiaozhang.springboot.controller;


import cn.hutool.core.util.ObjectUtil;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysDeptStandard;
import com.xiaozhang.springboot.service.SysDeptStandardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 部门标准表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-04-04
 */
@RestController
@RequestMapping("/sys-dept")
@Api(tags = "部门打卡标准接口")
@Slf4j
public class SysDeptStandardController {

    @Autowired
    SysDeptStandardService sysDeptStandardService;

    @GetMapping("/standard")
    @ApiOperation("部门打开标准，需要token")
    public Result getDeptStandard(@RequestParam String deptId) {

        SysDeptStandard sysDeptStandard = sysDeptStandardService.getRuleById(deptId);

        if (ObjectUtil.isNull(sysDeptStandard)) {
            return Result.fail("标准获取失败，无相关部门");
        } else {
            return Result.success(200, "打卡标准获取成功", sysDeptStandard, "");
        }
    }
}
