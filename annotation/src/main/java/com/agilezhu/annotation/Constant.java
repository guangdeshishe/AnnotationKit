package com.agilezhu.annotation;

/**
 * 相关常量定义
 *
 * @author zhujie
 * @date 2019-09-14
 * @time 06:23
 */
public class Constant {
    /**
     * 生成类所在包名
     */
    public static final String GENERATE_PACKAGE = "com.agilezhu.annotationkit.generate";
    /**
     * 生成类名固定前缀
     */
    public static final String GENERATE_CLASS_NAME_HEAD = "BindProxy$";
    /**
     * 生成类完整类名开头：包名+类名固定前缀
     */
    public static final String GENERATE_CLASS_FULL_NAME_HEAD = GENERATE_PACKAGE + "." + GENERATE_CLASS_NAME_HEAD;
}
