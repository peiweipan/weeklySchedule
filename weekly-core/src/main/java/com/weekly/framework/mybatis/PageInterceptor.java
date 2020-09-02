package com.weekly.framework.mybatis;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version V1.0
 * @author: csz
 * @Title
 * @Package: com.weekly.framework.mybatis
 * @Description: 删除分页工具的线程变量
 * @date: 2020/07/28
 */
@Component
public class PageInterceptor implements HandlerInterceptor, Ordered {
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        PageHelper.remove();
    }
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
