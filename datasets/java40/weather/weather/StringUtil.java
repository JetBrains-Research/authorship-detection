package com.weico.core.utils;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by qihigh on 12/12/13.
 * Modified by ____.
 */
public class StringUtil {
    private static String EMPTY = "";
    private static final String ALGORITHM = "AES/ECB/NoPadding";

    public static String join(String[] elements, String separator) {
        if (elements == null) {
            return EMPTY;
        }
        return join(Arrays.asList(elements), separator);
    }

    public static String join(Iterable<?> elements, String separator) {
        if (elements == null) {
            return EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        for (Object o : elements) {
            if (sb.length() > 0) {
                if (separator != null) {
                    sb.append(separator);
                }
            }
            sb.append(o);
        }
        return sb.toString();
    }

    /**
     * 转编码
     *
     * @param source
     * @param srcCharsetName
     * @param newCharsetName
     * @return
     */
    public static String convertCharset(String source, String srcCharsetName, String newCharsetName) {
        if (srcCharsetName.equals(newCharsetName)) {
            return source;
        }
        try {
            return new String(source.getBytes(srcCharsetName), newCharsetName);
        } catch (UnsupportedEncodingException unex) {
            throw new IllegalArgumentException(unex);
        }
    }

    /**
     * 切割字符串到某个字符串为止
     *
     * @param string
     * @param substring
     * @return
     */
    public static String cutToIndexOf(String string, String substring) {
        int i = string.indexOf(substring);
        if (i != -1) {
            string = string.substring(0, i);
        }
        return string;
    }

    /**
     * 从某个字符串开始切字符串
     *
     * @param string
     * @param substring
     * @return
     */
    public static String cutFromIndexOf(String string, String substring) {
        int i = string.indexOf(substring);
        if (i != -1) {
            string = string.substring(i);
        }
        return string;
    }

    public static String reverse(String s) {
        StringBuilder result = new StringBuilder(s.length());
        for (int i = s.length() - 1; i >= 0; i--) {
            result.append(s.charAt(i));
        }
        return result.toString();
    }

    public static boolean isEmpty(String string) {
        return ((string == null) || (string.length() == 0));
    }

    public static boolean isAnyEmpty(String... strings) {
        for (String string : strings) {
            if (isEmpty(string)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAllEmpty(String... strings) {
        for (String string : strings) {
            if (!isEmpty(string)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 数字过长的简单处理
     *
     * @param number 要显示的数字
     * @return
     */
    public static String formatNumber(int number) {
        String str = String.valueOf(number);
        if (number > 100000) {
            str = str.substring(0, str.length() - 4);
            str = str + "万";
            return str;
        }
        return str;
    }

    /**
     * Html-encode the string.
     *
     * @param s the string to be encoded
     * @return the encoded string
     */
    public static String htmlEncode(String s) {
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;"); //$NON-NLS-1$
                    break;
                case '>':
                    sb.append("&gt;"); //$NON-NLS-1$
                    break;
                case '&':
                    sb.append("&amp;"); //$NON-NLS-1$
                    break;
                //'#' 会作为正则匹配条件，故不做Encode
//                case '\'':
                //http://www.w3.org/TR/xhtml1
                // The named character reference &apos; (the apostrophe, U+0027) was introduced in
                // XML 1.0 but does not appear in HTML. Authors should therefore use &#39; instead
                // of &apos; to work as expected in HTML 4 user agents.
//                    sb.append("&#39;"); //$NON-NLS-1$
//                    break;
                case '"':
                    sb.append("&quot;"); //$NON-NLS-1$
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /*
     * 数字保留一位小数
     */
    public static String formatScore(double number) {
        return BigDecimal.valueOf(number).setScale(1, RoundingMode.HALF_UP).toString();
    }

    /*
     * 数字保留一位小数
     */
    public static String formatScore(String number) {
        return formatScore(Double.valueOf(number));
    }

    public static String md5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /////////////////////////////////////// android ///////////////////////////////////////


    /**
     * 通过String 取得id 转换成国际化后的 StringArray
     *
     * @param aStrings
     * @param context
     * @return
     */
    public static String[] localizedStringArray(String aStrings, Context context) {
        String packageName = context.getPackageName();
        int resId = context.getResources().getIdentifier(aStrings, "array", packageName);
        if (resId == 0) {
            return null;
        } else {
            return context.getResources().getStringArray(resId);
        }
    }


}