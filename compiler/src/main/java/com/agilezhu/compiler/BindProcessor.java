package com.agilezhu.compiler;

import com.agilezhu.annotation.BindView;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import static com.agilezhu.annotation.Constant.GENERATE_CLASS_NAME_HEAD;
import static com.agilezhu.annotation.Constant.GENERATE_PACKAGE;

/**
 * 处理绑定类型的注解
 *
 * @author zhujie
 * @date 2019-09-13
 * @time 08:29
 */
@AutoService(Processor.class)
public class BindProcessor extends AbstractProcessor {
    private Filer mFiler; //文件相关的辅助类
    private Elements mElementUtils; //元素相关的辅助类  许多元素
    private Messager mMessager;//日志相关的辅助类

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> elements = roundEnvironment.getRootElements();
        for (Element element : elements) {
            if (!(element instanceof TypeElement)) {//判断是否class类
                continue;
            }
            //转换成class类型
            TypeElement typeElement = (TypeElement) element;
            String classSimpleName = element.getSimpleName().toString();//类名
            String targetClassName = GENERATE_CLASS_NAME_HEAD + element.getSimpleName();

            //创建方法(构造方法)
            MethodSpec.Builder bindMethodBuilder = MethodSpec.methodBuilder("<init>")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(typeElement.asType()), classSimpleName.toLowerCase());

            //获取当前类里所有元素
            List<? extends Element> members = mElementUtils.getAllMembers(typeElement);
            //当前类里所有添加了BindView注释的元素
            List<Element> annotationMembers = new ArrayList<>();
            for (Element member : members) {
                BindView bindViewAnnotation = member.getAnnotation(BindView.class);
                if (bindViewAnnotation != null) {
                    annotationMembers.add(member);
                    String paramName = classSimpleName.toLowerCase();
                    bindMethodBuilder.addStatement(
                            String.format(
                                    paramName + ".%s = (%s) " + paramName + ".findViewById(%s)"
                                    , member.getSimpleName()
                                    , ClassName.get(member.asType()).toString()
                                    , bindViewAnnotation.value()));
                }
            }
            if (annotationMembers.isEmpty()) {
                continue;
            }

            //创建类
            TypeSpec bindProxyClass = TypeSpec.classBuilder(targetClassName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(bindMethodBuilder.build())
                    .build();
            //创建java文件
            JavaFile bindProxyFile = JavaFile
                    .builder(GENERATE_PACKAGE, bindProxyClass)
                    .build();
            try {
                bindProxyFile.writeTo(mFiler);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
