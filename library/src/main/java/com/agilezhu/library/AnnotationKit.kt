package com.agilezhu.library

import android.app.Activity

/**
 * 用于绑定组件
 *
 * @author zhujie
 * @date 2019-09-13
 * @time 18:32
 */
class AnnotationKit {
    companion object {
        fun getInstance(): AnnotationKit {
            return InstanceHolder.INSTANCE
        }

        private class InstanceHolder {
            companion object {
                val INSTANCE = AnnotationKit()
            }
        }
    }

    fun bind(activity: Activity) {

    }


}