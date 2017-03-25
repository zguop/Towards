package com.waitou.wt_library.kit;

/**
 * Created by waitou on 17/3/24.
 * 字符串工具
 */

public class UString {
    /**
     * 判断字符串是否为null或长度为0
     */
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串不为null
     */
    public static boolean isNotEmpty(String str) {
        return (str != null && !str.trim().equals(""));
    }

    /**
     * 字符串为null 使用nullStr
     */
    public static String checkNotNull(String str, String nullStr) {
        return isEmpty(str) ? nullStr : str;
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Long.parseLong(value);
            return !value.contains(".");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 给传入的链接添加参数返回
     *
     * @param url          url
     * @param paramName    key
     * @param paramContent value
     * @return url
     */
    public static String convertUrlWithParam(String url, String paramName, String paramContent) {
        StringBuilder sb = new StringBuilder();

        if (isNotEmpty(url) && isNotEmpty(paramName) && isNotEmpty(paramContent)) {
            if (url.contains("?")) { //www.maihaoche.com?pp=11  --> //www.maihaoche.com?user_id=1&pp=11
                int position = url.indexOf("?");
                sb.append(url.substring(0, position + 1));
                sb.append(paramName);
                sb.append("=");
                sb.append(paramContent);
                sb.append("&");
                sb.append(url.substring(position + 1, url.length()));
            } else {
                if (url.contains("#")) {//www.maihaoche.com#ss  -->www.maihaoche.com?user_id=1#ss
                    int position = url.indexOf("#");
                    sb.append(url.substring(0, position));
                    sb.append("?");
                    sb.append(paramName);
                    sb.append("=");
                    sb.append(paramContent);
                    sb.append(url.substring(position + 1, url.length()));
                } else { //www.maihaoche.com   -->  www.maihaoche.com?user_id=1
                    sb.append(url);
                    sb.append("?");
                    sb.append(paramName);
                    sb.append("=");
                    sb.append(paramContent);
                }
            }
        } else {
            sb.append(url);
        }
        return sb.toString();
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return (a == b) || (b != null) && (a.length() == b.length()) && a.regionMatches(true, 0, b, 0, b.length());
    }

    /**
     * null转为长度为0的字符串
     *
     * @param s 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static String null2Length0(String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);

    }
}
