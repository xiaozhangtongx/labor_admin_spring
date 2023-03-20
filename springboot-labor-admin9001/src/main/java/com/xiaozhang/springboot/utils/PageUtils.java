package com.xiaozhang.springboot.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: xiaozhangtx
 * @ClassName: Page
 * @Description: TODO 获取Page信息
 * @date: 2023/3/20 20:34
 * @Version: 1.0
 */
@Component
public class PageUtils {
    @Autowired
    HttpServletRequest req;

    public Page getPage() {
        int current = ServletRequestUtils.getIntParameter(req, "current", 1);
        int size = ServletRequestUtils.getIntParameter(req, "size", 10);

        return new Page(current, size);
    }
}
