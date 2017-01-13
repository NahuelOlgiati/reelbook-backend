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
	public static final String TIME_FORMAT = "HH:mm";
	public static final String FULL_TIME_FORMAT = "HH:mm:ss";
	public static final String POSTGRESQL_TIMESTAMP_WITHOUT_TIME_ZONE = "yyyy-MM-dd HH:mm:ss.SSS";

	// Prebuilt dates.
	public static final Date EMPTY_DATE = getDate("01/01/1900");
	public static final Date FINAL_DATE = getDate("01/01/3000");

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

	public static final Date getDate(final String stringDate)
	{
		return getDate(stringDate, SIMPLE_DATE_FORMAT);
	}

	public static final String getDate(final Date date, final String format)
	{
		return new SimpleDateFormat(format).format(date);
	}

	public static final String getDate(final Date date)
	{
		return getDate(date, SIMPLE_DATE_FORMAT);
	}

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

	public static final Date getDateFD(final Date date)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	public static final Date getDateFS(final Date date)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

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
	 * @param date
	 *            La fecha de la que se extraerá el valor
	 * @param calendarConstant
	 *            Una constante de java.util.Calendar (ej: Calendar.YEAR)
	 */
	public static final int get(final Date date, final Integer calendarConstant)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(calendarConstant);
	}

	/**
	 * @param date
	 *            La fecha a la que se sumará el amount
	 * @param amount
	 *            La cantidad de unidades definidas por calendarConstant que queremos sumar
	 * @param calendarConstant
	 *            Una constante de java.util.Calendar (ej: Calendar.YEAR)
	 */
	public static final Date add(final Date date, final int amount, final Integer calendarConstant)
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarConstant, amount);
		return c.getTime();
	}

	public static final long getTime()
	{
		return System.currentTimeMillis();
	}

	public static final long getElapsedTime(final long startTime)
	{
		return getTime() - startTime;
	}

	public static final long getElapsedSeconds(final long startTime)
	{
		return (getTime() - startTime) / 1000;
	}

	public static final long getDiffTime(final Date date1, final Date date2)
	{
		return date1.getTime() - date2.getTime();
	}

	public static final long getDiffSeconds(final Date date1, final Date date2)
	{
		return getDiffTime(date1, date2) / (1000);
	}

	public static final long getDiffMinutes(final Date date1, final Date date2)
	{
		return getDiffTime(date1, date2) / (60 * 1000);
	}

	public static final long getDiffHours(final Date date1, final Date date2)
	{
		return getDiffTime(date1, date2) / (60 * 60 * 1000);
	}

	public static final long getDiffDays(final Date date1, final Date date2)
	{
		return getDiffTime(date1, date2) / (24 * 60 * 60 * 1000);
	}

	public static final long getDiffMonths(final Date date1, final Date date2)
	{
		return (get(date1, Calendar.YEAR) - get(date2, Calendar.YEAR)) * 12 + (get(date1, Calendar.MONTH) - get(date2, Calendar.MONTH));
	}

	/**
	 * Calcula la edad de alguien teniendo en cuenta años bisiestos.
	 */
	public static final int getAge(final Date dateOfBirth, final Date date)
	{
		final Calendar today = Calendar.getInstance();
		final Calendar bd = Calendar.getInstance();
		today.setTime(date);
		bd.setTime(dateOfBirth);
		int age = today.get(Calendar.YEAR) - bd.get(Calendar.YEAR);
		if (today.get(Calendar.MONTH) < bd.get(Calendar.MONTH))
		{
			age--;
		}
		else if (today.get(Calendar.MONTH) == bd.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < bd.get(Calendar.DAY_OF_MONTH))
		{
			age--;
		}
		return age;
	}

	public static final Date getDate(final Date thisDate, final Date withThisTime)
	{
		final Calendar c1 = Calendar.getInstance();
		final Calendar c2 = Calendar.getInstance();
		c1.setTime(withThisTime);
		c2.setTime(thisDate);
		c1.set(Calendar.DAY_OF_MONTH, c2.get(Calendar.DAY_OF_MONTH));
		c1.set(Calendar.MONTH, c2.get(Calendar.MONTH));
		c1.set(Calendar.YEAR, c2.get(Calendar.YEAR));
		return c1.getTime();
	}
}