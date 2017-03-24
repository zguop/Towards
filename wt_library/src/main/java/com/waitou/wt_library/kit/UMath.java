package com.waitou.wt_library.kit;

import java.math.BigDecimal;

/**
 * Created by waitou on 17/3/24.
 */

public class UMath {
    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2);
    }

    /**
     * 提供精确的加法运算。 String
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static String strAdd(String v1, String v2, int scale) {
        if (scale < 0) {
            scale = 0;
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal sub(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2);
    }

    /**
     * 提供精确的减法运算。String
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static String strSub(String v1, String v2, int scale) {
        if (scale < 0) {
            scale = 0;
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }
}
