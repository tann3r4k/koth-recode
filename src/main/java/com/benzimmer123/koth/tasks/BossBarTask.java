package com.benzimmer123.koth.tasks;

import org.bukkit.Bukkit;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.hooks.other.BossBar;
import com.benzimmer123.koth.obj.threads.AsyncThread;

public class BossBarTask extends AsyncThread {

	public void run() {
		if (!KOTH.getInstance().getKOTHManager().isActiveKOTH())
			return;

		Bukkit.getOnlinePlayers().stream().forEach(player -> {
			Bukkit.getScheduler().runTask(KOTH.getInstance(), () -> {
				BossBar.updateTitle(player);
			});
		});
	}

}
