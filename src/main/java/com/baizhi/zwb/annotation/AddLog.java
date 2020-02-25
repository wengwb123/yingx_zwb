package com.baizhi.zwb.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@Inherited
public @interface AddLog {
    String value();

    String name() default "";
}
