package com.agilezhu.library

import android.app.Activity
import com.agilezhu.annotation.Constant.GENERATE_CLASS_FULL_NAME_HEAD
import java.lang.reflect.Constructor

/**
 * 用于绑定组件
 *
 * @author zhujie
 * @date 2019-09-13
 * @time 18:32
 */
class AnnotationKit {
    companion object {
        /**
         * 缓存构造方法
         */
        private val mCacheConstructor = HashMap<Class<*>, Constructor<*>?>()

        fun bind(activity: Activity) {
            //从缓存中读取构造方法
            var constructor = mCacheConstructor[activity.javaClass]
            if (constructor == null) {
                synchronized(mCacheConstructor) {
                    if (constructor == null) {
                        try {
                            val fullClassName =
                                GENERATE_CLASS_FULL_NAME_HEAD + activity::class.java.simpleName
                            //通过反射获取生成的类
                            val clazz = Class.forName(fullClassName)
                            //获取构造方法
                            constructor = clazz.getConstructor(activity.javaClass)
                            //存入缓存
                            mCacheConstructor[activity.javaClass] = constructor
                        } catch (e: Throwable) {
                            e.printStackTrace()
                        }
                    }
                }
            }
            //反射调用构造方法
            constructor?.newInstance(activity)


        }
    }

}