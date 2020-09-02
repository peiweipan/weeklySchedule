package com.weekly.common.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weekly.common.form.PageParamsForm;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @version V1.0
 * @author: csz
 * @Title
 * @Package: com.keyan.common.util
 * @Description:
 * @date: 2020/08/07
 */
public class PaginationUtils {

    /**
     * 分页参数转换
     *
     * @param paramsVo 分页参数
     * @return
     */
    public static <T> Page<T> fromGrid(PageParamsForm paramsVo) {
        return preResolve(
            paramsVo,
            pageParamsForm -> new Page<>(paramsVo.getPage(), paramsVo.getSize())
        );
    }

    private static <T> T preResolve(PageParamsForm paramsVo, Function<PageParamsForm, T> mapFun) {

        //        限制每页条数
        int size = paramsVo.getSize() > 100 ? 20 : paramsVo.getSize();

        Integer page = paramsVo.getPage();

        if (page == null || page < 0) {
            page = 1;
        }

        paramsVo.setSize(size);
        paramsVo.setPage(page);

        return mapFun.apply(paramsVo);

    }

    public static <T> Page<T> setRecords(Page page, List<T> records) {
        return page.setRecords(records);
    }

    public static <T> Page<T> setRecords(Page page, Supplier<List<T>> recordsFun) {
        if (page == null || page.getRecords().isEmpty()) {
            return page;
        }
        return page.setRecords(recordsFun.get());
    }

    public static <T, R> Page<R> mapPage(Page<T> page, Function<T, R> mapFun) {
        return setRecords(
            page, page.getRecords().stream().map(mapFun).filter(Objects::nonNull).collect(Collectors.toList())
        );

    }

}
