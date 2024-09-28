package com.kakarote.core.common.log;

import com.kakarote.core.common.SubModelType;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * @author hmb
 */
@Retention(RetentionPolicy.RUNTIME)  //注解将在运行时可通过反射机制读取
@Target({ElementType.TYPE})  // 用于类、接口（包括注解类型）或枚举
@Documented  //标识自定义注解会在生成的 Java 文档（Javadoc）中包含说明
public @interface SysLog {
    /**
     * 子模块名称
     */
    SubModelType subModel() default SubModelType.NULL;


    /**
     * 指定与该注解相关的日志处理类。如果没有指定，则默认为 void.class，表示没有特定的日志类
     */
    Class logClass() default void.class;
}
