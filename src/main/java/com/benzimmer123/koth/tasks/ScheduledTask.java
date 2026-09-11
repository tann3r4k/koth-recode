package com.benzimmer123.koth.tasks;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.enums.ScheduleType;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.Schedule;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.handlers.RandomScheduleHandler;
import com.benzimmer123.koth.obj.threads.AsyncThread;
import com.benzimmer123.koth.util.DateUtil;

public class ScheduledTask extends AsyncThread {

	private boolean sortDates = false;

	@Override
	public void run() {
		if (!KOTHHandler.getInstance().isPluginLoaded())
			return;

		List<Schedule> randomKOTHs = RandomScheduleHandler.getInstance().getSchedule().checkAllSchedules();

		if (!randomKOTHs.isEmpty()) {
			for (Schedule randSchedule : randomKOTHs) {
				KOTHArena koth = KOTHHandler.getInstance().getKOTHS().get(new Random().nextInt(KOTHHandler.getInstance().getKOTHS().size()));

				Bukkit.getScheduler().runTask(KOTH.getInstance(), () -> {
					if (KOTH.getInstance().getConfig().getInt("MINIMUM_PLAYERS_FOR_SCHEDULE_START") == -1 || Bukkit.getOnlinePlayers().size() >= KOTH
							.getInstance().getConfig().getInt("MINIMUM_PLAYERS_FOR_SCHEDULE_START")) {
						KOTH.getInstance().getKOTHManager().callTask(koth, randSchedule.getRequiredTime(), null, false, randSchedule.getMaxRunTime(),
								randSchedule.getMaxPoints());
						sortDates = true;
					}

					if (randSchedule.getScheduleType().equals(ScheduleType.SCHEDULE)) {
						RandomScheduleHandler.getInstance().getSchedule().remove(randSchedule);
						sortDates = true;
					}
				});
			}
		}

		for (KOTHArena koth : KOTHHandler.getInstance().getKOTHS()) {
			List<Schedule> scheduledKOTHs = koth.getKOTHScheduler().checkAllSchedules();

			if (scheduledKOTHs.isEmpty())
				continue;

			Bukkit.getScheduler().runTask(KOTH.getInstance(), () -> {
				for (Schedule schedule : scheduledKOTHs) {
					if (KOTH.getInstance().getConfig().getInt("MINIMUM_PLAYERS_FOR_SCHEDULE_START") == -1 || Bukkit.getOnlinePlayers().size() >= KOTH
							.getInstance().getConfig().getInt("MINIMUM_PLAYERS_FOR_SCHEDULE_START")) {
						KOTH.getInstance().getKOTHManager().callTask(koth, schedule.getRequiredTime(), null, false, schedule.getMaxRunTime(), schedule
								.getMaxPoints());
						sortDates = true;
					}

					if (schedule.getScheduleType().equals(ScheduleType.SCHEDULE)) {
						koth.getKOTHScheduler().remove(schedule);
						sortDates = true;
					}
				}
			});

			koth.save();
		}

		if (sortDates) {
			DateUtil.sortUpcomingDates();
			sortDates = false;
		}
	}
}
