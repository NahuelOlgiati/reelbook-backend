package com.reelbook.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil
{
	// Date formats.
	public static final String SIMPLE_DATE_FORMAT = "dd/MM/yyyy";
	public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";
	public static final String FULL_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	public static final String POSTGRESQL_TIMESTAMP_WITHOUT_TIME_ZONE = "yyyy-MM-dd HH:mm:ss.SSS";

	// Prebuilt dates.
	public static final Date EMPTY_DATE = getDate("01/01/1900");
	public static final Date FINAL_DATE = getDate("01/01/5000");

	/**
	 */
	public static final Date getDate(final Integer year, final Integer month, final Integer day)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.YEAR, year);
		return c.getTime();
	}

	/**
	 */
	public static final Date getDate(final String stringDate, final String format)
	{
		Date date;
		try
		{
			date = new SimpleDateFormat(format).parse(stringDate);
		}
		catch (ParseException p)
		{
			date = null;
		}
		return date;
	}

	/**
	 */
	public static final Date getDate(final String stringDate)
	{
		return getDate(stringDate, SIMPLE_DATE_FORMAT);
	}

	/**
	 */
	public static final String getDate(final Date date, final String format)
	{
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 */
	public static final String getDate(final Date date)
	{
		return getDate(date, SIMPLE_DATE_FORMAT);
	}

	/**
	 */
	public static final Date getDateFDYear(final Date date)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		return c.getTime();
	}

	/**
	 */
	public static final Date getDateLDYear(final Date date)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DAY_OF_MONTH, 31);
		return c.getTime();
	}

	/**
	 */
	public static final Date getDateFDMonth(final Date date)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	/**
	 */
	public static final Date getDateLDMonth(final Date date)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	/**
	 */
	public static final Date getDateFDQuarter(Date date)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		int quater = (c.get(Calendar.MONTH) / 3);

		switch (quater)
		{
		case 3:
			c.set(Calendar.MONTH, Calendar.OCTOBER);
			break;
		case 2:
			c.set(Calendar.MONTH, Calendar.JULY);
			break;
		case 1:
			c.set(Calendar.MONTH, Calendar.APRIL);
			break;
		case 0:
		default:
			c.set(Calendar.MONTH, Calendar.JANUARY);
			break;
		}
		return getDateFDMonth(c.getTime());
	}

	/**
	 */
	public static final Date getDateLDQuarter(Date date)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		int quater = (c.get(Calendar.MONTH) / 3);

		switch (quater)
		{
		case 3:
			c.set(Calendar.MONTH, Calendar.DECEMBER);
			break;
		case 2:
			c.set(Calendar.MONTH, Calendar.SEPTEMBER);
			break;
		case 1:
			c.set(Calendar.MONTH, Calendar.JUNE);
			break;
		case 0:
		default:
			c.set(Calendar.MONTH, Calendar.MARCH);
			break;
		}
		return getDateLDMonth(c.getTime());
	}

	/**
	 */
	public static final Date getDateFD(final Date date)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	/**
	 */
	public static final Date getDateFT(final Date date)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 */
	public static final Date getDateLT(final Date date)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 */
	public static final int getYear(final Date date)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 */
	public static final int getMonth(final Date date)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH);
	}

	/**
	 */
	public static final Date addMinutes(final Date date, final int minutes)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, minutes);
		return c.getTime();
	}

	/**
	 */
	public static final Date addDays(final Date date, final int days)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, days);
		return c.getTime();
	}

	/**
	 */
	public static final Date addMonths(final Date date, final int months)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, months);
		return c.getTime();
	}

	/**
	 */
	public static final Date addYears(final Date date, final int years)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, years);
		return c.getTime();
	}

	/**
	 */
	public static final long getTime()
	{
		return System.currentTimeMillis();
	}

	/**
	 */
	public static final long getElapsedTime(final long startTime)
	{
		return getTime() - startTime;
	}

	/**
	 */
	public static final long getDiffTime(final Date date1, final Date date2)
	{
		return date1.getTime() - date2.getTime();
	}

	/**
	 */
	public static final long getDiffDays(final Date date1, final Date date2)
	{
		return getDiffTime(date1, date2) / (24 * 60 * 60 * 1000);
	}

	/**
	 */
	public static final long getDiffMonths(final Date date1, final Date date2)
	{
		return (getYear(date1) - getYear(date2)) * 12 + (getMonth(date1) - getMonth(date2));
	}

	/**
	 */
	public static final long getDiffYears(final Date date1, final Date date2)
	{
		return (getYear(date1) - getYear(date2));
	}
}