package com.handpay.arch.stat.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 展示键名
 * @author sxjiang
 *
 */
@Target(ElementType.FIELD)  
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueKey {

	int order() default 10;
}
