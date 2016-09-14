package com.xxl.hex.handler.annotation;

import java.lang.annotation.*;

/**
 * Created by xuxueli on 16/9/14.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface HexHandlerMapping {
    // mapping value
    String value();

    Class requestClass();

}
