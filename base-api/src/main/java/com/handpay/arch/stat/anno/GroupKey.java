package com.handpay.arch.stat.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 统计分组(类似于sql中group by条件)
 * @author sxjiang
 *
 */
@Target(ElementType.FIELD)  
@Retention(RetentionPolicy.RUNTIME)
public @interface GroupKey {

    int order() default 0;
}
