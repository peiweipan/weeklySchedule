package com.weekly.auth.filter;

import com.weekly.auth.UserUtils;
import com.weekly.auth.annotation.IgnoreSecurity;
import com.weekly.auth.helper.UserHelper;
import com.weekly.common.constant.errcode.AccessErrorCode;
import com.weekly.common.exception.TokenException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author chzz
 * @version V1.0
 * @Title:
 * @Package com.mountain.common.filter
 * @Description: TODO 系统－token拦截器
 * @date 2019/5/10
 */
public class TokenFilter implements HandlerInterceptor {

    @Autowired
    UserHelper userHelper;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        // 如果不是映射到方法直接通过
        if (!(o instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) o;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(IgnoreSecurity.class)) {
            return true;
        }

        String token = httpServletRequest.getHeader("Authorization");
        if (StringUtils.isBlank(token)){
            throw new TokenException(AccessErrorCode.TOKEN_EMPTY.code(), "TOKEN不能为空");
        }

        if (!userHelper.hasKey(token)) {
            throw new TokenException(AccessErrorCode.TOKEN_NOLOGIN.code(), "该TOKEN不存在");
        }

        UserUtils.setUserBo(userHelper.getUserInfo(token));

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        UserUtils.remove();
    }
}
