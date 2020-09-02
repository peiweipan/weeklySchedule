package com.weekly.common.advice;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weekly.common.annotation.IgnoreRestHandler;
import com.weekly.common.annotation.RestHandler;
import com.weekly.common.pojo.vo.ResultBean;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version V1.0
 * @author: csz
 * @Title
 * @Package: com.bookchart.common.advice
 * @Description:
 * @date: 2020/05/07
 */
@RestControllerAdvice
public class ResultHandler implements ResponseBodyAdvice {

    private final static Map<Integer, Boolean> cacheMethod = new ConcurrentHashMap<>();

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {

        if (returnType == null || returnType.getMethod() == null) {
            return false;
        }

        Method method = returnType.getMethod();

        Boolean isNeedWrapper = null;

//        查询判断缓存
        if ((isNeedWrapper = cacheMethod.get(method.hashCode())) != null) {
            return isNeedWrapper;
        }

//        忽略包装注解
        if (method.getAnnotation(IgnoreRestHandler.class) != null) {
            isNeedWrapper = false;
        } else {
//          controller对象注解
            isNeedWrapper = method.getDeclaringClass().getAnnotation(RestHandler.class) != null;
        }

//        缓存结果
        cacheMethod.put(method.hashCode(), isNeedWrapper);

        return isNeedWrapper;

    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof ResultBean) {
            return body;
        }

        if (body instanceof String) {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return JSONObject.toJSONString(ResultBean.success(body));
        }

        // Mybatis-plus 的分页
        if (body instanceof IPage<?>) {
            IPage<?> page = (IPage<?>) body;
            return ResultBean.success(page.getRecords(), page.getTotal());
        }

        // ElasticSearchRepository 的分页
        if (body instanceof Page<?>) {
            Page<?> page = (Page<?>) body;
            return ResultBean.success(page.toList(), page.getTotalElements());
        }

        return ResultBean.success(body);
    }
}
