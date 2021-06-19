package com.tdee.tdeecalc.utils;

import java.math.BigDecimal;

public class Utils {
    public static double converDouble(double value) {
        BigDecimal b = new BigDecimal(value);
        double ret = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return ret;
    }
}
