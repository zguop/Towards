package com.to.aboomy.theme_lib.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * auth aboom
 * date 2019-08-23
 */

@Retention(RUNTIME)
@Target({TYPE})
public @interface Skinable {
}
