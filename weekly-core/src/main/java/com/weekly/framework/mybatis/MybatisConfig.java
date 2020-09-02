package com.weekly.framework.mybatis;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @version V1.0
 * @author: csz
 * @Title
 * @Package: com.weekly.framework.mybatis
 * @Description:
 * @date: 2020/07/28
 */
@Component
public class MybatisConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new CompatiblePaginationInterceptor();
    }
}
