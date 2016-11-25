package com.reelbook.core.util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import com.reelbook.core.model.BaseModel;
import com.reelbook.core.model.mpi.Emptiable;
import com.reelbook.core.service.util.QueryHintResult;

public final class CompareUtil
{
	public static final boolean isEmpty(final Object object)
	{
		return (object == null);
	}

	public static final boolean isEmpty(final Object[] object)
	{
		return (object == null || object.length == 0);
	}

	public static final boolean isEmpty(final String value)
	{
		return (value == null || value.trim().length() == 0);
	}

	public static final boolean isEmpty(final Date date)
	{
		return (date == null);
	}

	public static final boolean isEmpty(final Collection<?> value)
	{
		return (value == null || value.isEmpty());
	}

	public static final boolean isEmpty(final Map<?, ?> value)
	{
		return (value == null || value.isEmpty());
	}

	public static final boolean isEmpty(final byte[] value)
	{
		return (value == null || value.length == 0);
	}

	public static final boolean isEmpty(final BaseModel baseModel)
	{
		return (baseModel == null || isEmpty(baseModel.getID()));
	}

	public static final boolean isEmpty(final Emptiable emptiable)
	{
		return (emptiable == null || emptiable.isBlank());
	}

	public static final boolean isEmpty(final Integer value)
	{
		return (value == null || value.equals(0));
	}

	public static final boolean isEmpty(final Long value)
	{
		return (value == null || value.equals(0l));
	}

	public static final boolean isEmpty(final Double value)
	{
		return (value == null || value.equals(0d));
	}

	public static final boolean isEmpty(final BigDecimal value)
	{
		return (value == null || value.compareTo(BigDecimal.ZERO) == 0);
	}

	public static final boolean isEmpty(final QueryHintResult<?> value)
	{
		return (value == null || value.getRowCount() == 0);
	}

	public static final boolean isPositive(final Integer value)
	{
		return !isEmpty(value) && value.compareTo(0) > 0;
	}

	public static final boolean isPositive(final Long value)
	{
		return !isEmpty(value) && value.compareTo(0l) > 0;
	}

	public static final boolean isPositive(final Double value)
	{
		return !isEmpty(value) && value.compareTo(0d) > 0;
	}

	public static final boolean isPositive(final BigDecimal value)
	{
		return !isEmpty(value) && value.compareTo(BigDecimal.ZERO) > 0;
	}

	public static final boolean isNegative(final Integer value)
	{
		return !isEmpty(value) && value.compareTo(0) < 0;
	}

	public static final boolean isNegative(final Long value)
	{
		return !isEmpty(value) && value.compareTo(0l) < 0;
	}

	public static final boolean isNegative(final Double value)
	{
		return !isEmpty(value) && value.compareTo(0d) < 0;
	}

	public static final boolean isNegative(final BigDecimal value)
	{
		return !isEmpty(value) && value.compareTo(BigDecimal.ZERO) < 0;
	}

	public static final <T extends Comparable<T>> boolean isBetween(final T value, final T value1, final T value2)
	{
		return !isEmpty(value) && !isEmpty(value1) && !isEmpty(value2) && value.compareTo(value1) >= 0 && value.compareTo(value2) <= 0;
	}

	public static final <T extends Comparable<T>> boolean isLessThan(final T value1, final T value2)
	{
		return value1.compareTo(value2) == -1;
	}

	public static final <T extends Comparable<T>> boolean isEqualThan(final T value1, final T value2)
	{
		return value1.compareTo(value2) == 0;
	}

	public static final <T extends Comparable<T>> boolean isGreaterThan(final T value1, final T value2)
	{
		return value1.compareTo(value2) == 1;
	}
}