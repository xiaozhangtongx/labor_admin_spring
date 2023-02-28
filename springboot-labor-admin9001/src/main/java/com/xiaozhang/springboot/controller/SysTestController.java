package com.xiaozhang.springboot.controller;

import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author: xiaozhangtx
 * @ClassName: SysTestController
 * @Description: TODO 测试
 * @date: 2023/2/28 10:51
 * @Version: 1.0
 */
@RestController
@Api(tags = "测试接口")
@RequestMapping("/test")
public class SysTestController {

    @GetMapping("/get")
    @ApiOperation("测试接口")
    public Result getTest() {
        return Result.success("Get测试成功");
    }

    @PostMapping("/post")
    @ApiOperation("测试接口")
    public Result postTest(@RequestBody SysUser sysUser) {
        return Result.success(sysUser.getUsername() + "post测试成功");
    }

    @PutMapping("/put")
    @ApiOperation("put测试接口")
    public Result putTest(@RequestBody SysUser sysUser) {
        return Result.success(sysUser.getUsername() + "put测试成功");
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("delete测试接口")
    public Result deleteTest(@PathVariable String id) {
        return Result.success(id + "delete测试成功");
    }
}
