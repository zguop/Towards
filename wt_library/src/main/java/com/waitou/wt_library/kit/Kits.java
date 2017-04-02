package com.waitou.wt_library.kit;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by wanglei on 2016/11/28.
 */

public class Kits {

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
         * 取得当前日期的年份，以yyyy格式返回.
         *
         * @return 当年 yyyy
         */
        public static String getCurrentYear() {
            return getFormatDateTime(new java.util.Date(), "yyyy");
        }

        /**
         * 取得当前日期的月份，以MM格式返回.
         *
         * @return 当前月份 MM
         */
        public static String getCurrentMonth() {
            return getFormatDateTime(new java.util.Date(), "MM");
        }


        /**
         * @return 当前月份有多少天；
         */
        public static int getDaysOfCurMonth() {
            int curYear = Integer.parseInt(getCurrentYear()); // 当前年份
            int curMonth = Integer.parseInt(getCurrentMonth());// 当前月份
            int mArray[] = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,
                    31};
            // 判断闰年的情况 ，2月份有29天；
            if ((curYear % 400 == 0)
                    || ((curYear % 100 != 0) && (curYear % 4 == 0))) {
                mArray[1] = 29;
            }
            return mArray[curMonth - 1];
            // 如果要返回下个月的天数，注意处理月份12的情况，防止数组越界；
            // 如果要返回上个月的天数，注意处理月份1的情况，防止数组越界；
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
}
