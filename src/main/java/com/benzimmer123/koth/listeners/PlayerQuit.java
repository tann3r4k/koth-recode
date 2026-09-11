package com.benzimmer123.koth.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.hooks.other.BossBar;

public class PlayerQuit implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		KOTHArena koth = KOTH.getInstance().getKOTHManager().isCapping(e.getPlayer());
		KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(e.getPlayer());

		if (koth != null) {
			koth.setCapper(null, true);
		}

		if (kothPlayer.getScoreboard() != null) {
			kothPlayer.getScoreboard().disappear();
			kothPlayer.setScoreboard(null);
		}

		KOTHHandler.getInstance().removeKOTHPlayer(e.getPlayer());
		BossBar.removeBossBar(e.getPlayer());
	}
}
