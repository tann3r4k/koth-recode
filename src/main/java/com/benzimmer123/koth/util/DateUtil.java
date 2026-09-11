package com.benzimmer123.koth.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.enums.ScheduleType;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.Schedule;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.handlers.RandomScheduleHandler;
import com.google.common.collect.Maps;

public class DateUtil {

	public static void sortUpcomingDates() {
		LoggerUtil.info("[KOTH] Sorting through upcoming KOTH schedules...");

		Map<ZonedDateTime, KOTHArena> unsortedDates = Maps.newHashMap();
		ZonedDateTime endDate = ZonedDateTime.now(ZoneId.of(KOTH.getInstance().getConfig().getString("CURRENT_TIMEZONE"))).plusDays(7);

		for (KOTHArena koth : KOTHHandler.getInstance().getKOTHS()) {
			for (Schedule schedule : koth.getKOTHScheduler().getScheduled()) {
				ZonedDateTime date = schedule.getDate();

				if (schedule.getScheduleType() == ScheduleType.DAILY) {
					date = getNextDailyDate(date);

					for (int i = 0; i < 7; i++) {
						ZonedDateTime daily = date.plusDays(i);

						if (daily.isBefore(endDate))
							unsortedDates.put(daily, koth);
					}

					continue;
				} else if (schedule.getScheduleType() == ScheduleType.WEEKLY) {
					date = getNextWeeklyDate(date);
				}

				if (date == null || date.isAfter(endDate))
					continue;

				unsortedDates.put(date, koth);
			}
		}

		for (Schedule schedule : RandomScheduleHandler.getInstance().getSchedule().getScheduled()) {
			ZonedDateTime date = schedule.getDate();

			if (schedule.getScheduleType() == ScheduleType.DAILY) {
				date = getNextDailyDate(date);

				for (int i = 0; i < 7; i++) {
					ZonedDateTime daily = date.plusDays(i);

					if (daily.isBefore(endDate))
						unsortedDates.put(daily, null);
				}

				continue;
			} else if (schedule.getScheduleType() == ScheduleType.WEEKLY) {
				date = getNextWeeklyDate(date);
			}

			if (date == null || date.isAfter(endDate))
				continue;

			unsortedDates.put(date, null);
		}

		java.util.TreeMap<ZonedDateTime, KOTHArena> sortedDates = new java.util.TreeMap<ZonedDateTime, KOTHArena>();
		sortedDates.putAll(unsortedDates);
		KOTHHandler.getInstance().setUpcomingDates(sortedDates);

		LoggerUtil.info("[KOTH] Successfully sorted through all KOTH schedules.");
	}

	public static String getScheduleCountdown() {
		ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.of(KOTH.getInstance().getConfig().getString("CURRENT_TIMEZONE")));
		long seconds = ChronoUnit.SECONDS.between(currentDate, getNextStart());
		return LangUtil.SCHEDULED_COUNTDOWN.toString().replaceAll("%time%", new TimeUtil((int) seconds).formatTime());
	}

	public static ZonedDateTime getNextStart() {
		ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.of(KOTH.getInstance().getConfig().getString("CURRENT_TIMEZONE")));

		for (ZonedDateTime date : KOTHHandler.getInstance().getUpcomingDates().keySet()) {
			if (date.isAfter(currentDate)) {
				return date;
			}
		}

		return null;
	}

	public static String getNextStartName() {
		ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.of(KOTH.getInstance().getConfig().getString("CURRENT_TIMEZONE")));

		for (ZonedDateTime date : KOTHHandler.getInstance().getUpcomingDates().keySet()) {
			if (date.isAfter(currentDate)) {
				if (KOTHHandler.getInstance().getUpcomingDates().get(date) != null) {
					return KOTHHandler.getInstance().getUpcomingDates().get(date).getName(true);
				}
				return LangUtil.RANDOM_SCHEDULE.toString();
			}
		}

		return null;
	}

	private static ZonedDateTime getNextDailyDate(ZonedDateTime date) {
		ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.of(KOTH.getInstance().getConfig().getString("CURRENT_TIMEZONE")));
		ZonedDateTime expectedDate = ZonedDateTime.of(date.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth(), date.getHour(), date
				.getMinute(), 0, 0, ZoneId.of(KOTH.getInstance().getConfig().getString("CURRENT_TIMEZONE")));
		expectedDate = expectedDate.withMonth(currentDate.getMonthValue()).withDayOfMonth(currentDate.getDayOfMonth()).withDayOfYear(currentDate
				.getDayOfYear());

		if (expectedDate.getHour() < currentDate.getHour() || expectedDate.getHour() == currentDate.getHour() && expectedDate
				.getMinute() < currentDate.getMinute()) {
			expectedDate = expectedDate.plusDays(1);
		}

		return expectedDate;
	}

	private static ZonedDateTime getNextWeeklyDate(ZonedDateTime date) {
		ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.of(KOTH.getInstance().getConfig().getString("CURRENT_TIMEZONE")));
		ZonedDateTime expectedDate = ZonedDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(), date.getMinute(), 0,
				0, ZoneId.of(KOTH.getInstance().getConfig().getString("CURRENT_TIMEZONE")));
		DayOfWeek dow = expectedDate.getDayOfWeek();

		int threshold = 0;

		while (expectedDate.getDayOfMonth() == currentDate.getDayOfMonth() && expectedDate.getHour() == currentDate.getHour() && expectedDate
				.getMinute() < currentDate.getMinute() || dow != expectedDate.getDayOfWeek()) {
			expectedDate = expectedDate.plusDays(1);
			threshold++;

			if (threshold >= 30)
				break;
		}

		if (threshold >= 30) {
			LoggerUtil.warning("[ERROR] There was a fatal error when loading scheduler. Please report this to Benzimmer immediately.");
			return null;
		}

		return expectedDate;
	}

	public static LocalDate nextDayOfWeek(DayOfWeek day, ZoneId zoneId) {
		LocalDate nextWed = LocalDate.now(zoneId).with(TemporalAdjusters.next(day));
		return nextWed;
	}

}
