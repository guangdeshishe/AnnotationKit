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

        private val cacheConstructor = HashMap<Class<*>, Constructor<*>>()

        fun bind(activity: Activity) {
            var constructor = cacheConstructor[activity.javaClass]
            if (constructor == null) {
                synchronized(cacheConstructor) {
                    if (constructor == null) {
                        try {
                            val fullClassName =
                                GENERATE_CLASS_FULL_NAME_HEAD + activity::class.java.simpleName

                            val clazz = Class.forName(fullClassName)
                            constructor = clazz.getConstructor(activity.javaClass)
                        } catch (e: Throwable) {
                            e.printStackTrace()
                        }
                    }
                }
            }
            constructor?.newInstance(activity)


        }
    }

}