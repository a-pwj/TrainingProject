package com.training.project.utils;

import java.text.DecimalFormat;

public class ToolText {


    /**
     * 保留一位小数, 4位前进一位  例如：1000 -> 0.1万
     *
     * @param num
     * @param unit
     * @return
     */
    public static String formatOnePointCloseNoEnter(long num, boolean unit) {
        DecimalFormat df = new DecimalFormat("0.0");//保留一位，不足补0
        if (num >= 10000000) {
            return unit ?df.format(ToolMath.doubleDivNoEnter(num, 100000000, 1)) + "亿" :
                    df.format(ToolMath.doubleDivNoEnter(num, 100000000, 1));
        }
        if (num >= 1000) {
            return unit ? df.format(ToolMath.doubleDivNoEnter(num, 10000, 1)) + "万" :
                    df.format(ToolMath.doubleDivNoEnter(num, 10000, 1));
        }
        return String.valueOf(num);
    }
}
