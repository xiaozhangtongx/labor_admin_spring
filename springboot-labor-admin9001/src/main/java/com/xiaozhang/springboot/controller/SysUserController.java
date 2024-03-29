package com.xiaozhang.springboot.controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhang.springboot.common.lang.Const;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.SysUser;
import com.xiaozhang.springboot.domain.SysUserRole;
import com.xiaozhang.springboot.service.SysUserRoleService;
import com.xiaozhang.springboot.service.SysUserService;
import com.xiaozhang.springboot.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author xiaozhangtx
 * @since 2023-03-19
 */
@RestController
@Api(tags = "用户接口")
@RequestMapping("/sys-user")
@Slf4j
@Transactional
public class SysUserController {
    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @Autowired
    PageUtils pageUtil;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @GetMapping("/userInfo")
    @ApiOperation("登录获取用户信息,需要token")
    public Result getUserInfoByPhoneNum(Principal principal) {

        SysUser sysUser = sysUserService.getInfoByPhoneNum(principal.getName());
        sysUser.setLastLogin(new Date());
        sysUserService.updateById(sysUser);
        sysUser.setPassword(null);

        return Result.success(200, "用户信息获取成功", sysUser, "");
    }

    @GetMapping("/code")
    @ApiOperation("获取验证码")
    public Result getRandomValidateCode() throws IOException {

        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
        String code = captcha.getCode();
        BufferedImage image = (BufferedImage) captcha.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";

        String base64Img = str + encoder.encode(outputStream.toByteArray());

        return Result.success(200, "验证码获取成功",
                MapUtil.builder()
                        .put("code", code)
                        .put("captchaImg", base64Img)
                        .build(), ""
        );
    }

    @GetMapping("/list")
    @ApiOperation("获取用户列表,需要token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "请求页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "请求页大小", required = false, dataType = "Integer", paramType = "query")
    })
    public Result list(@RequestParam String username) {

        Page<SysUser> pageData = sysUserService.page(pageUtil.getPage(), new QueryWrapper<SysUser>()
                .like("username", username));

        pageData.getRecords().forEach(user -> {
            user.setRoles(sysUserService.getUserRoles(user.getId()));
            user.setPassword(null);
        });

        return Result.success(200, "用户列表获取成功", pageData, "");
    }

    @GetMapping("/info/{id}")
    @ApiOperation("通过id获取用户,需要token")
    public Result getUserInfoById(@PathVariable("id") String id) {

        SysUser sysUser = sysUserService.getInfoById(id);

        return Result.success(200, "查询成功", sysUser, "");
    }

    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("添加用户,需要token")
    public Result add(@Validated @RequestBody SysUser sysUser) {

        SysUser user = sysUserService.getInfoByPhoneNum(sysUser.getPhoneNum());

        if (ObjectUtil.isNotNull(user)) {
            return Result.fail("该手机号已经被注册了");
        } else {
            sysUser.setCreateTime(new Date());
            String password = passwordEncoder.encode(sysUser.getPassword());
            sysUser.setPassword(password);
            sysUser.setAvatar(sysUser.getAvatar().equals("") ? Const.DEFULT_AVATOR : sysUser.getAvatar());

            sysUserService.save(sysUser);
            sysUser.setPassword(null);

            return Result.success(200, "添加成功", sysUser, "");
        }
    }

    @PutMapping("/update/info")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("修改用户信息,需要token")
    public Result update(@RequestBody SysUser sysUser) {

        SysUser userInfoById = sysUserService.getById(sysUser.getId());

        userInfoById.setUpdateTime(new Date());
        userInfoById.setAvatar(sysUser.getAvatar().equals("") ? Const.DEFULT_AVATOR : sysUser.getAvatar());
        userInfoById.setPhoneNum(sysUser.getPhoneNum());

        boolean flag = sysUserService.updateById(userInfoById);

        return flag ? Result.success(200, "修改成功", sysUser, "") : Result.fail("修改失败");
    }

    @PostMapping("/role/{userId}")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("分配角色,需要token")
    public Result rolePerm(@PathVariable("userId") String userId, @RequestBody String[] roleIds) {

        List<SysUserRole> userRoles = new ArrayList<>();

        Arrays.stream(roleIds).forEach(r -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(r);
            sysUserRole.setUserId(userId);

            userRoles.add(sysUserRole);
        });

        sysUserRoleService.remove(new QueryWrapper<SysUserRole>().eq("user_id", userId));
        boolean b = sysUserRoleService.saveBatch(userRoles);

        return b ? Result.success("修改成功") : Result.fail("修改失败");
    }

    @PutMapping("/updatePass")
    @ApiOperation("更新密码,需要token")
    public Result updatePass(@RequestBody SysUser sysUser, Principal principal) {

        SysUser user = sysUserService.getByPhoneNum(principal.getName());

        if (passwordEncoder.matches(sysUser.getPassword(), user.getPassword())) {
            return Result.fail("新密码不能和原密码相同");
        }

        boolean matches = passwordEncoder.matches(sysUser.getOldPassword(), user.getPassword());
        if (!matches) {
            return Result.fail("旧密码不正确");
        }

        user.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        user.setUpdateTime(new Date());
        sysUserService.updateById(user);

        return Result.success("密码修改成功");
    }

    @PutMapping("/repass")
    @ApiOperation("重置密码,需要token")
    public Result repass(@RequestBody SysUser sysUser) {

        SysUser user = sysUserService.getById(sysUser.getId());

        user.setPassword(passwordEncoder.encode(Const.DEFULT_PASSWORD));
        user.setUpdateTime(new Date());

        sysUserService.updateById(user);

        return Result.success("密码重置成功");
    }

    @PostMapping("/delete")
    @ApiOperation("批量删除用户,需要token")
    public Result delete(@RequestBody String[] ids) {

        Boolean flag = sysUserService.deleteByIds(Arrays.asList(ids));

        return flag ? Result.success("删除成功") : Result.fail("删除失败");
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("注销用户,需要token")
    public Result delete(@PathVariable String id) {

        Boolean flag = sysUserService.deleteByIds(Arrays.asList(id));

        return flag ? Result.success("删除成功") : Result.fail("删除失败");
    }

}
