package com.reelbook.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class MathUtil
{
	/**
	 */
	public final BigDecimal add(final BigDecimal augend, final BigDecimal addend)
	{
		return augend.add(addend).setScale(getScale(), getRoundingMode());
	}

	/**
     */
	public final BigDecimal subtract(final BigDecimal minuend, final BigDecimal subtrahend)
	{
		return minuend.subtract(subtrahend).setScale(getScale(), getRoundingMode());
	}

	/**
	 */
	public final BigDecimal multiply(final BigDecimal multiplicand, final BigDecimal multiplier)
	{
		return multiplicand.multiply(multiplier).setScale(getScale(), getRoundingMode());
	}

	/**
	 */
	public final BigDecimal divide(final BigDecimal dividend, final BigDecimal divisor)
	{
		return dividend.divide(divisor, getScale(), getRoundingMode());
	}

	/**
	 */
	public final BigDecimal asPercentage(final BigDecimal total, final BigDecimal value)
	{
		return divide(BigDecimal.valueOf(100d).multiply(value), total);
	}

	/**
	 */
	public final BigDecimal percentage(final BigDecimal quantity, final BigDecimal percent)
	{
		return quantity.multiply(percent).divide(BigDecimal.valueOf(100d)).setScale(getScale(), getRoundingMode());
	}

	/**
	 */
	public final BigDecimal percentage1000(final BigDecimal quantity, final BigDecimal percent)
	{
		return quantity.multiply(percent).divide(BigDecimal.valueOf(1000d)).setScale(getScale(), getRoundingMode());
	}

	/**
	 */
	public final BigDecimal percentageWithMinimum(final BigDecimal percent, final BigDecimal minimum, final BigDecimal quantity)
	{
		return minimum.max(this.percentage(quantity, percent));
	}

	/**
	 */
	public final BigDecimal percentage1000WithMinimum(final BigDecimal percent, final BigDecimal minimum, final BigDecimal quantity)
	{
		return minimum.max(this.percentage1000(quantity, percent));
	}

	/**
	 */
	public final BigDecimal getScaled(final BigDecimal value)
	{
		return value.setScale(getScale(), getRoundingMode());
	}

	/**
	 */
	public abstract int getScale();

	/**
	 */
	public abstract RoundingMode getRoundingMode();
}