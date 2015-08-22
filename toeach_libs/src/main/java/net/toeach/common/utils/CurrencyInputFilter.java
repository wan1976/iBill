package net.toeach.common.utils;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * EditText过滤器，过滤小数点后两位的字符
 */
public class CurrencyInputFilter implements InputFilter {
    /**
     * 输入框小数的位数
     */
    private static final int DECIMAL_DIGITS = 2;

    /**
     * 过滤字符
     *
     * @param source 当前输入的字符
     * @param start
     * @param end
     * @param dest   之前输入的字符串
     * @param dstart
     * @param dend
     * @return
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        // 删除等特殊字符，直接返回
        if ("".equals(source.toString())) {
            return "";
        }
//        LogUtils.d("source:" + source + ", dest:" + dest);

        String s = dest.toString();
        int dotIndex = s.indexOf(".");
//        LogUtils.d("position of dot:" + dotIndex);
        if (dotIndex > -1) {
            String dotValue = s.substring(dotIndex + 1);
//            LogUtils.d("dotValue:" + dotValue);
            if (dotValue.length() >= DECIMAL_DIGITS) {
                return "";
            }
        }
        return null;// 原样返回
    }
}
