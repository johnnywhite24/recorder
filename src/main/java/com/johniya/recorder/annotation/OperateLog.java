package com.johniya.recorder.annotation;

import com.johniya.recorder.OperateTargetSource;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperateLog {

    Class<? extends OperateTargetSource> source();

    String target() default "";

    String operate() default "";
}
