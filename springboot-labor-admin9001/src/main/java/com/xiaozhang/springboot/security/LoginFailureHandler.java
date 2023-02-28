package com.xiaozhang.springboot.security;

import cn.hutool.json.JSONUtil;
import com.xiaozhang.springboot.common.lang.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author: xiaozhangtx
 * @ClassName: SecurityConfig
 * @Description: TODO 登录失败处理
 * @date: 2023/2/27 19:57
 * @Version: 1.0
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        Result result = Result.fail("用户名或密码错误!");

        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));

        outputStream.flush();
        outputStream.close();
    }
}
