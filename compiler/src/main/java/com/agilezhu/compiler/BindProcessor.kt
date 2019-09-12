package com.agilezhu.compiler

import com.agilezhu.annotation.BindView
import com.google.auto.service.AutoService
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements


/**
 * 用于处理Bind系列注解
 *
 * @author zhujie
 * @date 2019-09-12
 * @time 17:09
 */
@AutoService(Processor::class)
class BindProcessor : AbstractProcessor() {

    private lateinit var mFiler: Filer //文件相关的辅助类
    private lateinit var mElementUtils: Elements //元素相关的辅助类  许多元素
    private lateinit var mMessager: Messager//日志相关的辅助类

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        mFiler = processingEnv.filer
        mElementUtils = processingEnv.elementUtils
        mMessager = processingEnv.messager
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val set = LinkedHashSet<String>()
        set.add(BindView::class.qualifiedName!!)
        return set
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        val bindMethod = MethodSpec.methodBuilder("bind")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(Void::class.java)
            .build()
        val bindProxyClass = TypeSpec.classBuilder("BindProxy")
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addMethod(bindMethod)
            .build()
        val bindProxyFile = JavaFile
            .builder("com.agilezhu.annotationkit.proxy", bindProxyClass)
            .build()
        try {
            bindProxyFile.writeTo(mFiler)
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return false
    }
}