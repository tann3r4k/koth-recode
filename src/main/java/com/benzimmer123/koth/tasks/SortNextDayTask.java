package com.benzimmer123.koth.tasks;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.obj.threads.AsyncThread;
import com.benzimmer123.koth.util.DateUtil;

public class SortNextDayTask extends AsyncThread {

	private int lastDayChecked;

	@Override
	public void run() {
		int currentDay = ZonedDateTime.now(ZoneId.of(KOTH.getInstance().getConfig().getString("CURRENT_TIMEZONE"))).getDayOfMonth();

		if (lastDayChecked >= currentDay)
			return;

		lastDayChecked = currentDay;

		DateUtil.sortUpcomingDates();
	}
}
