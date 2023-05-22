package com.example.system.utils.annotation;

import java.lang.annotation.*;

/**
 * 我注释
 *
 * @author laughlook
 * @date 2023/03/24
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAnnotation {
    String value() default "";
}
