package com.handpay.arch.common;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 * Created by fczheng on 2016/11/14.
 */
public class Constants {
    public static final String SEPARATOR_COMMA = ",";

    public static final String SEPARATOR_VERTICAL = "|";

    public static final String BLANK_SPACE = " ";

    public static final String REDIS_GROUP_KEY_SET = "GroupKeySet";

    public static final double KPI_PRECISION = 0.00001d;

    public static final FastDateFormat FAST_SDF = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
}
