package com.waitou.wt_library.kit;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by wanglei on 2016/11/28.
 */

public class Kits {

    public static class Util {

        public static boolean isNotEmptyList(List<?> list) {
            return list != null && list.size() > 0;
        }
    }

    public static class UMath {

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

    public static class UString {

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

    public static class Package {
        /**
         * 获取版本号
         */
        public static int getVersionCode(Context context) {
            PackageManager pManager = context.getPackageManager();
            PackageInfo packageInfo = null;
            try {
                packageInfo = pManager.getPackageInfo(context.getPackageName(), 0);

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return packageInfo.versionCode;
        }

        /**
         * 获取当前版本
         */
        public static String getVersionName(Context context) {
            PackageManager pManager = context.getPackageManager();
            PackageInfo packageInfo = null;
            try {
                packageInfo = pManager.getPackageInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return packageInfo.versionName;
        }

        /**
         * 安装App
         */
        public static boolean installNormal(Context context, String filePath) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            java.io.File file = new java.io.File(filePath);
            if (file == null || !file.exists() || !file.isFile() || file.length() <= 0) {
                return false;
            }

            i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }

        /**
         * 卸载App
         */
        public static boolean uninstallNormal(Context context, String packageName) {
            if (packageName == null || packageName.length() == 0) {
                return false;
            }

            Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse(new StringBuilder().append("package:")
                    .append(packageName).toString()));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }

        /**
         * 判断是否是系统App
         */
        public static boolean isSystemApplication(Context context, String packageName) {
            if (context == null) {
                return false;
            }
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null || packageName == null || packageName.length() == 0) {
                return false;
            }

            try {
                ApplicationInfo app = packageManager.getApplicationInfo(packageName, 0);
                return (app != null && (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return false;
        }

        /**
         * 判断某个包名是否运行在顶层
         */
        public static Boolean isTopActivity(Context context, String packageName) {
            if (context == null || TextUtils.isEmpty(packageName)) {
                return null;
            }

            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
            if (tasksInfo == null || tasksInfo.isEmpty()) {
                return null;
            }
            try {
                return packageName.equals(tasksInfo.get(0).topActivity.getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 获取Meta-Data
         */
        public static String getAppMetaData(Context context, String key) {
            if (context == null || TextUtils.isEmpty(key)) {
                return null;
            }
            String resultData = null;
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                    if (applicationInfo != null) {
                        if (applicationInfo.metaData != null) {
                            resultData = applicationInfo.metaData.getString(key);
                        }
                    }

                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            return resultData;
        }

        /**
         * 判断当前应用是否运行在后台
         */
        public static boolean isApplicationInBackground(Context context) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
            if (taskList != null && !taskList.isEmpty()) {
                ComponentName topActivity = taskList.get(0).topActivity;
                if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName())) {
                    return true;
                }
            }
            return false;
        }
    }


    public static class Dimens {

        public static float dip2px(Context context, float dp) {
            return dp * context.getResources().getDisplayMetrics().density;
        }

        public static int dip2pxInt(Context context, float dp) {
            return (int) (dip2px(context, dp) + 0.5f);
        }

        /**
         * 获取屏幕高度
         */
        public static int getDeviceHeight(Context context) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            return dm.heightPixels;
        }

        /**
         * 获取屏幕宽度
         */
        public static int getDeviceWidth(Context context) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            return dm.widthPixels;
        }
    }


    public static class Random {
        public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String NUMBERS             = "0123456789";
        public static final String LETTERS             = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String CAPITAL_LETTERS     = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String LOWER_CASE_LETTERS  = "abcdefghijklmnopqrstuvwxyz";

        public static String getRandomNumbersAndLetters(int length) {
            return getRandom(NUMBERS_AND_LETTERS, length);
        }

        public static String getRandomNumbers(int length) {
            return getRandom(NUMBERS, length);
        }

        public static String getRandomLetters(int length) {
            return getRandom(LETTERS, length);
        }

        public static String getRandomCapitalLetters(int length) {
            return getRandom(CAPITAL_LETTERS, length);
        }

        public static String getRandomLowerCaseLetters(int length) {
            return getRandom(LOWER_CASE_LETTERS, length);
        }

        public static String getRandom(String source, int length) {
            return TextUtils.isEmpty(source) ? null : getRandom(source.toCharArray(), length);
        }

        public static String getRandom(char[] sourceChar, int length) {
            if (sourceChar == null || sourceChar.length == 0 || length < 0) {
                return null;
            }

            StringBuilder str = new StringBuilder(length);
            java.util.Random random = new java.util.Random();
            for (int i = 0; i < length; i++) {
                str.append(sourceChar[random.nextInt(sourceChar.length)]);
            }
            return str.toString();
        }

        public static int getRandom(int max) {
            return getRandom(0, max);
        }

        public static int getRandom(int min, int max) {
            if (min > max) {
                return 0;
            }
            if (min == max) {
                return min;
            }
            return min + new java.util.Random().nextInt(max - min);
        }
    }

    public static class File {
        public final static String FILE_EXTENSION_SEPARATOR = ".";

        /**
         * read file
         */
        public static StringBuilder readFile(String filePath, String charsetName) {
            java.io.File file = new java.io.File(filePath);
            StringBuilder fileContent = new StringBuilder("");
            if (file == null || !file.isFile()) {
                return null;
            }

            BufferedReader reader = null;
            try {
                InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
                reader = new BufferedReader(is);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    if (!fileContent.toString().equals("")) {
                        fileContent.append("\r\n");
                    }
                    fileContent.append(line);
                }
                return fileContent;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(reader);
            }
        }

        /**
         * write file
         *
         * @param filePath
         * @param content
         * @param append   is append, if true, write to the end of file, else clear content of file and write into it
         * @return return false if content is empty, true otherwise
         * @throws RuntimeException if an error occurs while operator FileWriter
         */
        public static boolean writeFile(String filePath, String content, boolean append) {
            if (TextUtils.isEmpty(content)) {
                return false;
            }

            FileWriter fileWriter = null;
            try {
                makeDirs(filePath);
                fileWriter = new FileWriter(filePath, append);
                fileWriter.write(content);
                return true;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(fileWriter);
            }
        }

        /**
         * write file
         *
         * @param filePath
         * @param contentList
         * @param append      is append, if true, write to the end of file, else clear content of file and write into it
         * @return return false if contentList is empty, true otherwise
         * @throws RuntimeException if an error occurs while operator FileWriter
         */
        public static boolean writeFile(String filePath, List<String> contentList, boolean append) {
            if (contentList == null || contentList.isEmpty()) {
                return false;
            }

            FileWriter fileWriter = null;
            try {
                makeDirs(filePath);
                fileWriter = new FileWriter(filePath, append);
                int i = 0;
                for (String line : contentList) {
                    if (i++ > 0) {
                        fileWriter.write("\r\n");
                    }
                    fileWriter.write(line);
                }
                return true;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(fileWriter);
            }
        }

        /**
         * write file, the string will be written to the begin of the file
         *
         * @param filePath
         * @param content
         * @return
         */
        public static boolean writeFile(String filePath, String content) {
            return writeFile(filePath, content, false);
        }

        /**
         * write file, the string list will be written to the begin of the file
         *
         * @param filePath
         * @param contentList
         * @return
         */
        public static boolean writeFile(String filePath, List<String> contentList) {
            return writeFile(filePath, contentList, false);
        }

        /**
         * write file, the bytes will be written to the begin of the file
         *
         * @param filePath
         * @param stream
         * @return
         * @see {@link #writeFile(String, InputStream, boolean)}
         */
        public static boolean writeFile(String filePath, InputStream stream) {
            return writeFile(filePath, stream, false);
        }

        /**
         * write file
         *
         * @param stream the input stream
         * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
         * @return return true
         * @throws RuntimeException if an error occurs while operator FileOutputStream
         */
        public static boolean writeFile(String filePath, InputStream stream, boolean append) {
            return writeFile(filePath != null ? new java.io.File(filePath) : null, stream, append);
        }

        /**
         * write file, the bytes will be written to the begin of the file
         *
         * @param file
         * @param stream
         * @return
         * @see {@link #writeFile(java.io.File, InputStream, boolean)}
         */
        public static boolean writeFile(java.io.File file, InputStream stream) {
            return writeFile(file, stream, false);
        }

        /**
         * write file
         *
         * @param file   the file to be opened for writing.
         * @param stream the input stream
         * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
         * @return return true
         * @throws RuntimeException if an error occurs while operator FileOutputStream
         */
        public static boolean writeFile(java.io.File file, InputStream stream, boolean append) {
            OutputStream o = null;
            try {
                makeDirs(file.getAbsolutePath());
                o = new FileOutputStream(file, append);
                byte data[] = new byte[1024];
                int length = -1;
                while ((length = stream.read(data)) != -1) {
                    o.write(data, 0, length);
                }
                o.flush();
                return true;
            } catch (FileNotFoundException e) {
                throw new RuntimeException("FileNotFoundException occurred. ", e);
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(o);
                IO.close(stream);
            }
        }

        /**
         * move file
         *
         * @param sourceFilePath
         * @param destFilePath
         */
        public static void moveFile(String sourceFilePath, String destFilePath) {
            if (TextUtils.isEmpty(sourceFilePath) || TextUtils.isEmpty(destFilePath)) {
                throw new RuntimeException("Both sourceFilePath and destFilePath cannot be null.");
            }
            moveFile(new java.io.File(sourceFilePath), new java.io.File(destFilePath));
        }

        /**
         * move file
         *
         * @param srcFile
         * @param destFile
         */
        public static void moveFile(java.io.File srcFile, java.io.File destFile) {
            boolean rename = srcFile.renameTo(destFile);
            if (!rename) {
                copyFile(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
                deleteFile(srcFile.getAbsolutePath());
            }
        }

        /**
         * copy file
         *
         * @param sourceFilePath
         * @param destFilePath
         * @return
         * @throws RuntimeException if an error occurs while operator FileOutputStream
         */
        public static boolean copyFile(String sourceFilePath, String destFilePath) {
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(sourceFilePath);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("FileNotFoundException occurred. ", e);
            }
            return writeFile(destFilePath, inputStream);
        }

        /**
         * read file to string list, a element of list is a line
         *
         * @param filePath
         * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
         * @return if file not exist, return null, else return content of file
         * @throws RuntimeException if an error occurs while operator BufferedReader
         */
        public static List<String> readFileToList(String filePath, String charsetName) {
            java.io.File file = new java.io.File(filePath);
            List<String> fileContent = new ArrayList<String>();
            if (file == null || !file.isFile()) {
                return null;
            }

            BufferedReader reader = null;
            try {
                InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
                reader = new BufferedReader(is);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    fileContent.add(line);
                }
                return fileContent;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(reader);
            }
        }

        /**
         * get file name with path, not include suffix
         * <p/>
         * <pre>
         *      getFileNameWithoutExtension(null)               =   null
         *      getFileNameWithoutExtension("")                 =   ""
         *      getFileNameWithoutExtension("   ")              =   "   "
         *      getFileNameWithoutExtension("abc")              =   "abc"
         *      getFileNameWithoutExtension("a.mp3")            =   "a"
         *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
         *      getFileNameWithoutExtension("c:\\")              =   ""
         *      getFileNameWithoutExtension("c:\\a")             =   "a"
         *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
         *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
         *      getFileNameWithoutExtension("/home/admin")      =   "admin"
         *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
         * </pre>
         *
         * @param filePath
         * @return file name with path, not include suffix
         * @see
         */
        public static String getFileNameWithoutExtension(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            if (filePosi == -1) {
                return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
            }
            if (extenPosi == -1) {
                return filePath.substring(filePosi + 1);
            }
            return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
        }

        /**
         * get file name with path, include suffix
         * <p/>
         * <pre>
         *      getFileName(null)               =   null
         *      getFileName("")                 =   ""
         *      getFileName("   ")              =   "   "
         *      getFileName("a.mp3")            =   "a.mp3"
         *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
         *      getFileName("abc")              =   "abc"
         *      getFileName("c:\\")              =   ""
         *      getFileName("c:\\a")             =   "a"
         *      getFileName("c:\\a.b")           =   "a.b"
         *      getFileName("c:a.txt\\a")        =   "a"
         *      getFileName("/home/admin")      =   "admin"
         *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
         * </pre>
         *
         * @param filePath
         * @return file name with path, include suffix
         */
        public static String getFileName(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
        }

        /**
         * get folder name with path
         * <p/>
         * <pre>
         *      getFolderName(null)               =   null
         *      getFolderName("")                 =   ""
         *      getFolderName("   ")              =   ""
         *      getFolderName("a.mp3")            =   ""
         *      getFolderName("a.b.rmvb")         =   ""
         *      getFolderName("abc")              =   ""
         *      getFolderName("c:\\")              =   "c:"
         *      getFolderName("c:\\a")             =   "c:"
         *      getFolderName("c:\\a.b")           =   "c:"
         *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
         *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
         *      getFolderName("/home/admin")      =   "/home"
         *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
         * </pre>
         *
         * @param filePath
         * @return
         */
        public static String getFolderName(String filePath) {

            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
        }

        /**
         * get suffix of file with path
         * <p/>
         * <pre>
         *      getFileExtension(null)               =   ""
         *      getFileExtension("")                 =   ""
         *      getFileExtension("   ")              =   "   "
         *      getFileExtension("a.mp3")            =   "mp3"
         *      getFileExtension("a.b.rmvb")         =   "rmvb"
         *      getFileExtension("abc")              =   ""
         *      getFileExtension("c:\\")              =   ""
         *      getFileExtension("c:\\a")             =   ""
         *      getFileExtension("c:\\a.b")           =   "b"
         *      getFileExtension("c:a.txt\\a")        =   ""
         *      getFileExtension("/home/admin")      =   ""
         *      getFileExtension("/home/admin/a.txt/b")  =   ""
         *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
         * </pre>
         *
         * @param filePath
         * @return
         */
        public static String getFileExtension(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            if (extenPosi == -1) {
                return "";
            }
            return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
        }

        /**
         * Creates the directory named by the trailing filename of this file, including the complete directory path required
         * to create this directory. <br/>
         * <br/>
         * <ul>
         * <strong>Attentions:</strong>
         * <li>makeDirs("C:\\Users\\Trinea") can only create users folder</li>
         * <li>makeFolder("C:\\Users\\Trinea\\") can create Trinea folder</li>
         * </ul>
         *
         * @param filePath
         * @return true if the necessary directories have been created or the target directory already exists, false one of
         * the directories can not be created.
         * <ul>
         * <li>if {@link File#getFolderName(String)} return null, return false</li>
         * <li>if target directory already exists, return true</li>
         * </ul>
         */
        public static boolean makeDirs(String filePath) {
            String folderName = getFolderName(filePath);
            if (TextUtils.isEmpty(folderName)) {
                return false;
            }

            java.io.File folder = new java.io.File(folderName);
            return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
        }

        /**
         * @param filePath
         * @return
         * @see #makeDirs(String)
         */
        public static boolean makeFolders(String filePath) {
            return makeDirs(filePath);
        }

        /**
         * Indicates if this file represents a file on the underlying file system.
         *
         * @param filePath
         * @return
         */
        public static boolean isFileExist(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return false;
            }

            java.io.File file = new java.io.File(filePath);
            return (file.exists() && file.isFile());
        }

        /**
         * Indicates if this file represents a directory on the underlying file system.
         *
         * @param directoryPath
         * @return
         */
        public static boolean isFolderExist(String directoryPath) {
            if (TextUtils.isEmpty(directoryPath)) {
                return false;
            }

            java.io.File dire = new java.io.File(directoryPath);
            return (dire.exists() && dire.isDirectory());
        }

        /**
         * delete file or directory
         * <ul>
         * <li>if path is null or empty, return true</li>
         * <li>if path not exist, return true</li>
         * <li>if path exist, delete recursion. return true</li>
         * <ul>
         *
         * @param path
         * @return
         */
        public static boolean deleteFile(String path) {
            if (TextUtils.isEmpty(path)) {
                return true;
            }

            java.io.File file = new java.io.File(path);
            if (!file.exists()) {
                return true;
            }
            if (file.isFile()) {
                return file.delete();
            }
            if (!file.isDirectory()) {
                return false;
            }
            for (java.io.File f : file.listFiles()) {
                if (f.isFile()) {
                    f.delete();
                } else if (f.isDirectory()) {
                    deleteFile(f.getAbsolutePath());
                }
            }
            return file.delete();
        }

        /**
         * get file size
         * <ul>
         * <li>if path is null or empty, return -1</li>
         * <li>if path exist and it is a file, return file size, else return -1</li>
         * <ul>
         *
         * @param path
         * @return returns the length of this file in bytes. returns -1 if the file does not exist.
         */
        public static long getFileSize(String path) {
            if (TextUtils.isEmpty(path)) {
                return -1;
            }

            java.io.File file = new java.io.File(path);
            return (file.exists() && file.isFile() ? file.length() : -1);
        }

    }

    public static class IO {
        /**
         * 关闭流
         *
         * @param closeable
         */
        public static void close(Closeable closeable) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    @SuppressWarnings("SimpleDateFormat") //unchecked
    public static class Date {

        public static final String YMD    = "yyyy-MM-dd";
        public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
        public static final String HM     = "HH:mm";


        private static SimpleDateFormat m        = new SimpleDateFormat("MM");
        private static SimpleDateFormat d        = new SimpleDateFormat("dd");
        private static SimpleDateFormat md       = new SimpleDateFormat("MM-dd");
        private static SimpleDateFormat ymdDot   = new SimpleDateFormat("yyyy.MM.dd");
        private static SimpleDateFormat ymdhmss  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        private static SimpleDateFormat ymdhm    = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        private static SimpleDateFormat mdhm     = new SimpleDateFormat("MM月dd日 HH:mm");
        private static SimpleDateFormat mdhmLink = new SimpleDateFormat("MM-dd HH:mm");

        /**
         * 年月日[2015-07-28]
         */
        public static String getYmd(long timeInMills) {
            return getFormatDateTime(new java.util.Date(timeInMills), YMD);
        }

        public static String getHm(long timeInMills) {
            return getFormatDateTime(new java.util.Date(timeInMills), HM);
        }

        public static String getCurrentHm() {
            return getFormatDateTime(new java.util.Date(), HM);
        }

        public static String getCurrentDate() {
            return getFormatDateTime(new java.util.Date(), YMD);
        }

        /**
         * 时间是否大于选择的时间
         *
         * @param hour   时
         * @param minute 分
         */
        public static boolean isRightTime(int hour, int minute) {
            Calendar calendar = Calendar.getInstance();
            int h = calendar.get(Calendar.HOUR_OF_DAY);
            int m = calendar.get(Calendar.MINUTE);
            return h > hour || (h == hour && m >= minute);
        }

        public static String friendlyTime(String dateStr) {
            java.util.Date time = null;
            try {
                time = getFormatDate(YMDHMS).parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (time == null) {
                return "Unknown";
            }
            String ftime = "";
            Calendar cal = Calendar.getInstance();
            // 判断是否是同一天
            SimpleDateFormat formatDate = getFormatDate(YMD);
            String curDate = formatDate.format(cal.getTime());
            String paramDate = formatDate.format(time);
            if (curDate.equals(paramDate)) {
                int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
                if (hour == 0)
                    ftime = Math.max(
                            (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                            + "分钟前";
                else
                    ftime = hour + "小时前";
                return ftime;
            }

            long lt = time.getTime() / 86400000;
            long ct = cal.getTimeInMillis() / 86400000;
            int days = (int) (ct - lt);
            if (days == 0) {
                int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
                if (hour == 0)
                    ftime = Math.max(
                            (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                            + "分钟前";
                else
                    ftime = hour + "小时前";
            } else if (days == 1) {
                ftime = "昨天";
            } else if (days == 2) {
                ftime = "前天";
            } else if (days > 2 && days <= 10) {
                ftime = days + "天前";
            } else if (days > 10) {
                ftime = formatDate.format(time);
            }
            return ftime;
        }

        /**
         * 根据给定的格式与时间(Date类型的)，返回时间字符串<br>
         *
         * @param date   指定的日期
         * @param format 日期格式字符串
         * @return String 指定格式的日期字符串.
         */
        public static String getFormatDateTime(java.util.Date date, String format) {
            return new SimpleDateFormat(format, Locale.getDefault()).format(date);
        }

        public static SimpleDateFormat getFormatDate(String format) {
            return new SimpleDateFormat(format, Locale.getDefault());
        }


        /**
         * 年月日[2015.07.28]
         */
        public static String getYmdDot(long timeInMills) {
            return ymdDot.format(new java.util.Date(timeInMills));
        }

        public static String getYmdhms(long timeInMills) {
            return getFormatDateTime(new java.util.Date(timeInMills * 1000L), YMDHMS);
        }

        public static String getYmdhmsS(long timeInMills) {
            return ymdhmss.format(new java.util.Date(timeInMills));
        }

        public static String getYmdhm(long timeInMills) {
            return ymdhm.format(new java.util.Date(timeInMills));
        }


        public static String getMd(long timeInMills) {
            return md.format(new java.util.Date(timeInMills));
        }

        public static String getMdhm(long timeInMills) {
            return mdhm.format(new java.util.Date(timeInMills));
        }

        public static String getMdhmLink(long timeInMills) {
            return mdhmLink.format(new java.util.Date(timeInMills));
        }

        public static String getM(long timeInMills) {
            return m.format(new java.util.Date(timeInMills));
        }

        public static String getD(long timeInMills) {
            return d.format(new java.util.Date(timeInMills));
        }

        /**
         * 是否是今天
         */
        public static boolean isToday(long timeInMills) {
            String dest = getYmd(timeInMills);
            String now = getYmd(Calendar.getInstance().getTimeInMillis());
            return dest.equals(now);
        }

        /**
         * 是否是同一天
         */
        public static boolean isSameDay(long aMills, long bMills) {
            String aDay = getYmd(aMills);
            String bDay = getYmd(bMills);
            return aDay.equals(bDay);
        }

        /**
         * 获取年份
         */
        public static int getYear(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            return calendar.get(Calendar.YEAR);
        }

        /**
         * 获取月份
         */
        public static int getMonth(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            return calendar.get(Calendar.MONTH) + 1;
        }


        /**
         * 获取月份的天数
         */
        public static int getDaysInMonth(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);

            switch (month) {
                case Calendar.JANUARY:
                case Calendar.MARCH:
                case Calendar.MAY:
                case Calendar.JULY:
                case Calendar.AUGUST:
                case Calendar.OCTOBER:
                case Calendar.DECEMBER:
                    return 31;
                case Calendar.APRIL:
                case Calendar.JUNE:
                case Calendar.SEPTEMBER:
                case Calendar.NOVEMBER:
                    return 30;
                case Calendar.FEBRUARY:
                    return (year % 4 == 0) ? 29 : 28;
                default:
                    throw new IllegalArgumentException("Invalid Month");
            }
        }


        /**
         * 获取星期,0-周日,1-周一，2-周二，3-周三，4-周四，5-周五，6-周六
         */
        public static int getWeek(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);

            return calendar.get(Calendar.DAY_OF_WEEK) - 1;
        }

        /**
         * 返回一个代表当期日期是星期几的数字。1表示星期天、2表示星期一、7表示星期六
         */
        public static int getDayOfWeek(java.util.Date date) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(date);
            return cal.get(Calendar.DAY_OF_WEEK);
        }

        /**
         * 获取当月第一天的时间（毫秒值）
         */
        public static long getFirstOfMonth(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            return calendar.getTimeInMillis();
        }

    }


    public static class NetWork {
        public static final String NETWORK_TYPE_WIFI       = "wifi";
        public static final String NETWORK_TYPE_3G         = "eg";
        public static final String NETWORK_TYPE_2G         = "2g";
        public static final String NETWORK_TYPE_WAP        = "wap";
        public static final String NETWORK_TYPE_UNKNOWN    = "unknown";
        public static final String NETWORK_TYPE_DISCONNECT = "disconnect";

        public static int getNetworkType(Context context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
            return networkInfo == null ? -1 : networkInfo.getType();
        }

        public static String getNetworkTypeName(Context context) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo;
            String type = NETWORK_TYPE_DISCONNECT;
            if (manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null) {
                return type;
            }

            if (networkInfo.isConnected()) {
                String typeName = networkInfo.getTypeName();
                if ("WIFI".equalsIgnoreCase(typeName)) {
                    type = NETWORK_TYPE_WIFI;
                } else if ("MOBILE".equalsIgnoreCase(typeName)) {
                    String proxyHost = android.net.Proxy.getDefaultHost();
                    type = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORK_TYPE_3G : NETWORK_TYPE_2G)
                            : NETWORK_TYPE_WAP;
                } else {
                    type = NETWORK_TYPE_UNKNOWN;
                }
            }
            return type;
        }

        private static boolean isFastMobileNetwork(Context context) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager == null) {
                return false;
            }

            switch (telephonyManager.getNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false;
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false;
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false;
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true;
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true;
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false;
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true;
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true;
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true;
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                    return true;
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    return true;
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return true;
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return false;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return true;
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    return false;
                default:
                    return false;
            }
        }

    }


}
