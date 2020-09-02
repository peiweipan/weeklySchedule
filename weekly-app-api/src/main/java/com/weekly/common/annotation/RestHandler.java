package com.weekly.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;


/**
 * @version V1.0
 * @author: csz
 * @Title
 * @Package: com.bookchart.common.annotation
 * @Description:
 * @date: 2020/05/07
 */
@Target({TYPE, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RestHandler {
}
