package org.example.utils;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private String pattern;
//    public static final String COMMON_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public DateUtil() {
	}

	public DateUtil(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Long format2Mills(String s) {
		return format2Date(s).getMillis();
	}

	public DateTime format2Date(String s) {
		DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
		return DateTime.parse(s, format);
	}

	public static DateTime format2Date(String s, String pattern) {
		DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
		return DateTime.parse(s, format);
	}
//
//    public static int compare(String arg1, String arg2) {
//        return format2Date(arg1, "yyyy-MM-dd HH:mm:ss").compareTo(format2Date(arg2, "yyyy-MM-dd HH:mm:ss"));
//    }
//
//    public static int compare(String arg1, String arg2, String format) {
//        return format2Date(arg1, format).compareTo(format2Date(arg2, format));
//    }

	public static Date parse(String s, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 增加或者减少的天数
	 *
	 * @param s     被增加天数的日期
	 * @param dates 需要增加或者减少的天数
	 * @return
	 */
	public String plusDays(String s, int dates) {
		DateTime date = format2Date(s);
		DateTime after = date.plusDays(dates);
		return after.toString(pattern);
	}

	/**
	 * 增加或者减少的天数
	 *
	 * @param s       被增加天数的日期
	 * @param dates   需要增加或者减少的天数
	 * @param pattern 格式化，例如：yyyy-MM-dd, yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String plusDays(String s, int dates, String pattern) {
		DateTime date = format2Date(s, pattern);
		DateTime after = date.plusDays(dates);
		return after.toString(pattern);
	}

	/**
	 * 如果s为空，则返回系统当前时间
	 *
	 * @param s
	 * @return
	 */
//    public String nowIfNull(String s) {
//        String time = null;
//        if (null == s || StringUtils.isEmpty(s) || "null".equals(s)) {
//            time = now();
//        } else {
//            time = plusDays(s, 0);
//        }
//        return time;
//    }

//    public String now() {
//        return DateTime.now().toString(pattern);
//    }

	/**
	 * 格式化返回系统当前日期
	 *
	 * @param pattern 格式化，例如：yyyy-MM-dd, yyyy-MM-dd HH:mm:ss
	 * @return
	 */
//    public static String now(String pattern) {
//        return DateTime.now().toString(pattern);
//    }
//
//    public static String nowMillsDate() {
//        return new DateTime().toString("yyyyMMddHHmmssS");
//    }
//
	public static void main(String[] args) throws Exception {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		DateUtil date = new DateUtil(pattern);
		System.out.println(new Interval(new Instant(), new Instant(date.format2Date("2020-01-24 00:00:00"))));
		System.out.println(date.format2Mills("2020-01-24 00:00:00"));
//
//        System.out.println(new DateTime(1493135537439l));
//        System.out.println(date.format2Date("2017-04-25 23:52:17").getMillis());
//        System.out.println(date.format2Date("2017-04-24 23:52:17").getMillis());
//        System.out.println(date.format2Date("2017-03-25 23:52:17").getMillis());
//        System.out.println(date.format2Date("2016-04-25 23:52:17").getMillis());
//        System.out.println(date.format2Date("2016-03-25 23:52:17").getMillis());
//        System.out.println(new DateTime(1493049137000l));
//        System.out.println(new DateTime(1493049137000l));
//        System.out.println(new Date());
//        System.out.println(current.format(new Date()));
//        System.out.println(date.plusDays("2020-01-24 00:00:00", 10));
//        System.out.println(date.plusDays("2017-11-24 23:52:17", 16));
//        System.out.println(System.currentTimeMillis());
//    System.out.println(now("dd-MM-yyyy HH:mm:ss"));//20-07-2017 14:02:41
//    System.out.println(DateUtil.parse("2017-08-25 11:48:04","yyyy-MM-dd HH:mm:ss"));
//        System.out.println(nowMillsDate());
//        System.out.println(now("yyyy-MM-dd HH:mm:ss SSS"));
//        System.out.println(DateUtil.compare("2018-11-20 19:46:24", "2018-11-20 01:45:23"));
//        List<String> list = new ArrayList<>();
//        list.add("2018-11-20 19:46:24");
//        list.add("2018-11-20 01:45:23");
//        System.out.println(DateUtil.now(DateUtil.COMMON_PATTERN));
//
//
//
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String str="2011-08-23 15:00:12";
//        Date dt=sdf.parse(str);
//        Calendar rightNow = Calendar.getInstance();
//        rightNow.setTime(dt);
//        rightNow.add(Calendar.HOUR,2);
//        Date dt1=rightNow.getTime();
//        String reStr = sdf.format(dt1);
//        System.out.println(reStr);
	}
}
