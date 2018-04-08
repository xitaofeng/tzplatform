package com.tzplatform.utils.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 解决SimpleDateFormat线程不安全
 *
 * @author leijie
 */
public class DateUtil {
	private static ThreadLocal<SimpleDateFormat> localDateFormmat = new ThreadLocal<SimpleDateFormat>();

	public static Date parse(String str, String dateformat) throws Exception {
		SimpleDateFormat simpleDateFormat = localDateFormmat.get();
		if (simpleDateFormat == null) {
			simpleDateFormat = new SimpleDateFormat(dateformat, Locale.CHINA);
			localDateFormmat.set(simpleDateFormat);
		}
		return simpleDateFormat.parse(str);
	}

	public static String format(Date date, String dateformat) throws Exception {
		SimpleDateFormat simpleDateFormat = localDateFormmat.get();
		if (simpleDateFormat == null) {
			simpleDateFormat = new SimpleDateFormat(dateformat, Locale.CHINA);
			localDateFormmat.set(simpleDateFormat);
		}
		return simpleDateFormat.format(date);
	}
}
