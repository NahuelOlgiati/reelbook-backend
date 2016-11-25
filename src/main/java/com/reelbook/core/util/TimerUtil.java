package com.reelbook.core.util;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.SessionContext;
import javax.ejb.TimerConfig;

public final class TimerUtil
{
	public static final <T extends Serializable> void scheduleStart(final SessionContext sc, final Date date, final T info, final Boolean persistent)
	{
		final TimerConfig timerConfig = new TimerConfig();
		timerConfig.setPersistent(persistent);
		timerConfig.setInfo(info);
		sc.getTimerService().createSingleActionTimer(date, timerConfig);
	}
}