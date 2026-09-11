package com.benzimmer123.koth.obj.koth.serial;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import com.benzimmer123.koth.api.enums.ScheduleType;
import com.benzimmer123.koth.api.objects.KOTHScheduler;
import com.benzimmer123.koth.api.objects.Schedule;
import com.benzimmer123.koth.storage.GsonStorage;
import com.benzimmer123.koth.util.DateUtil;
import com.google.common.collect.Lists;

public class MemoryKOTHScheduler implements KOTHScheduler, Serializable {

	private static final long serialVersionUID = 8802402232546297550L;
	private List<MemorySchedule> scheduledKOTHs;
	private int nextId;
	private boolean randomSchedule;

	public MemoryKOTHScheduler(boolean randomSchedule) {
		scheduledKOTHs = Lists.newArrayList();
		this.randomSchedule = randomSchedule;
		save();
	}

	public void addSchedule(ZonedDateTime date, int requiredTime, int maxRunTime, int maxPoints) {
		scheduledKOTHs.add(new MemorySchedule(date, getNextId(), requiredTime, 0, maxRunTime, maxPoints, ScheduleType.SCHEDULE));
		DateUtil.sortUpcomingDates();
		add();
		save();
	}

	public void addDaily(ZonedDateTime date, int requiredTime, int maxRunTime, int maxPoints) {
		scheduledKOTHs.add(new MemorySchedule(date, getNextId(), requiredTime, 0, maxRunTime, maxPoints, ScheduleType.DAILY));
		DateUtil.sortUpcomingDates();
		add();
		save();
	}

	public void addWeekly(ZonedDateTime date, int requiredTime, int maxRunTime, int maxPoints) {
		scheduledKOTHs.add(new MemorySchedule(date, getNextId(), requiredTime, 0, maxRunTime, maxPoints, ScheduleType.WEEKLY));
		DateUtil.sortUpcomingDates();
		add();
		save();
	}

	public List<Schedule> checkAllSchedules() {
		List<Schedule> toRemove = Lists.newArrayList();

		for (Schedule schedule : getScheduled()) {
			if (schedule.ended()) {
				toRemove.add(schedule);
			}
		}

		return toRemove;
	}

	public void remove(Schedule schedule) {
		scheduledKOTHs.remove(schedule);
		DateUtil.sortUpcomingDates();
	}

	public Schedule getSchedule(int id) {
		for (Schedule schedule : getScheduled()) {
			if (schedule.getId() == id) {
				return schedule;
			}
		}
		return null;
	}

	public int getNextId() {
		return nextId;
	}

	private void add() {
		nextId += 1;
	}

	public boolean isRandom() {
		return randomSchedule;
	}

	public List<Schedule> getScheduled() {
		List<Schedule> scheduledKOTHList = Lists.newArrayList(scheduledKOTHs);
		return scheduledKOTHList;
	}

	private void save() {
		if (isRandom())
			GsonStorage.serialize(this, "schedules/schedule.json", "schedule");
	}
}