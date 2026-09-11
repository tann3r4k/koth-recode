package com.benzimmer123.koth.handlers;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHScheduler;
import com.benzimmer123.koth.obj.koth.serial.MemoryKOTHScheduler;
import com.benzimmer123.koth.storage.GsonStorage;

public class RandomScheduleHandler {

	private final static RandomScheduleHandler INSTANCE;
	private KOTHScheduler schedule;

	static {
		INSTANCE = new RandomScheduleHandler();
	}

	private RandomScheduleHandler() {
		KOTHScheduler schedule = GsonStorage.deserialize(MemoryKOTHScheduler.class, KOTH.getInstance().getDataFolder() + "schedules/schedule.json",
				"schedule");
		if (schedule != null) {
			setRandomSchedule(schedule);
		} else {
			setRandomSchedule(new MemoryKOTHScheduler(true));
		}
	}

	public void setRandomSchedule(KOTHScheduler schedule) {
		this.schedule = schedule;
	}

	public KOTHScheduler getSchedule() {
		return schedule;
	}

	public static RandomScheduleHandler getInstance() {
		return INSTANCE;
	}
}
