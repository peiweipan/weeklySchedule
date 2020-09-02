package com.weekly.framework.mybatis;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.NamedThreadLocal;

import java.util.Collections;
import java.util.Optional;

/**
 * @version V1.0
 * @author: csz
 * @Title
 * @Package: com.bookchart.mybatis
 * @Description:
 * @date: 2020/07/28
 */
public class PageHelper {

//    只能使用一次
    private static final ThreadLocal<Page> pageThreadLocal = new NamedThreadLocal<>("page");
//    存储分页结果
    private static final ThreadLocal<Page> pageInfoThreadLocal = new NamedThreadLocal<>("pageInfo");

    public static void startPage(Integer pageNum, Integer pageSize, String orderBy) {
        Page page = new Page(pageNum, pageSize);
        if (StringUtils.isNotBlank(orderBy)) {
            page.setOrders(
                Collections.singletonList(OrderItem.desc(orderBy))
            );
        }
        pageInfoThreadLocal.set(page);
        pageThreadLocal.set(page);
    }

    public static Page getPageInfo() {
        return Optional.ofNullable(pageInfoThreadLocal.get()).orElse(new Page());
    }

    public static Page getPage() {
        Page page = pageThreadLocal.get();
//        使用后删除
        pageThreadLocal.remove();
        return page;
    }

    public static void remove() {
        pageThreadLocal.remove();
        pageInfoThreadLocal.remove();
    }

}
