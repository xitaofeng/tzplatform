package com.tzplatform.utils.aoplog;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccountAopLog {
    //描述
    String description() default "";

    //菜单名称
    String menuname() default "";
}
