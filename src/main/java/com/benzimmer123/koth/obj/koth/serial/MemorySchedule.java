package com.benzimmer123.koth.obj.koth.serial;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.enums.ScheduleType;
import com.benzimmer123.koth.api.objects.Schedule;

public class MemorySchedule implements Schedule, Serializable {

	private static final long serialVersionUID = -3053570868393934139L;
	private int id;
	private int requiredTime;
	private int playersRequired;
	private int maxRunTime;
	private int maxPoints;
	private ZonedDateTime date;
	private ScheduleType scheduleType;

	public MemorySchedule(ZonedDateTime date, int id, int requiredTime, int playersRequired, int maxRunTime, int maxPoints, ScheduleType scheduleType) {
		this.id = id;
		this.date = date;
		this.scheduleType = scheduleType;
		this.requiredTime = requiredTime;
		this.playersRequired = playersRequired;
		this.maxRunTime = maxRunTime;
		this.maxPoints = maxPoints;
	}

	public boolean ended() {
		Instant instant = Instant.now();
		ZoneId zoneId = ZoneId.of(KOTH.getInstance().getConfig().getString("CURRENT_TIMEZONE"));
		ZonedDateTime date = ZonedDateTime.ofInstant(instant, zoneId);
		if (getScheduleType().equals(ScheduleType.SCHEDULE)) {
			if (date.isAfter(this.date))
				return true;
		} else if (getScheduleType().equals(ScheduleType.WEEKLY)) {
			if (date.getDayOfWeek() == this.date.getDayOfWeek() && date.getHour() == this.date.getHour() && date.getMinute() == this.date.getMinute())
				return true;
		} else if (getScheduleType().equals(ScheduleType.DAILY)) {
			if (date.getHour() == this.date.getHour() && date.getMinute() == this.date.getMinute()) {
				return true;
			}
		}
		return false;
	}

	public ScheduleType getScheduleType() {
		return scheduleType;
	}

	public int getId() {
		return id;
	}

	public int getRequiredTime() {
		return requiredTime;
	}

	public int getPlayersRequired() {
		return playersRequired;
	}

	public ZonedDateTime getDate() {
		return date;
	}

	public int getMaxRunTime() {
		return maxRunTime;
	}

	public int getMaxPoints() {
		return maxPoints;
	}

}
