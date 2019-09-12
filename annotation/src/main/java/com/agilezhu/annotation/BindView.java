package com.agilezhu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通过配置控件id来替换findViewById
 *
 * @author zhujie
 * @date 2019-09-12
 * @time 17:56
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface BindView {
    int viewId();
}
