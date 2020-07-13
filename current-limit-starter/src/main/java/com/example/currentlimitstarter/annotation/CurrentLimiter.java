package com.example.currentlimitstarter.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface CurrentLimiter {

    long QPS() default 1;

    long timeOut() default 1;

    boolean failFast() default true;

}
