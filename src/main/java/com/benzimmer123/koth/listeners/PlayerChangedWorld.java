package com.benzimmer123.koth.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.scoreboard.PlayerScoreboard;
import com.benzimmer123.koth.scoreboard.providers.Default;

public class PlayerChangedWorld implements Listener {
	
	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent e) {
		List<KOTHArena> active = KOTH.getInstance().getKOTHManager().getActiveKOTHs();

		if (!active.isEmpty()) {
			if (KOTH.getInstance().getScoreboardManager().isScoreboardAllowed(e.getPlayer())) {
				KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(e.getPlayer());

				if (KOTH.getInstance().getScoreboardManager().isLoaded("TitleManager") || KOTH.getInstance().getScoreboardManager().isLoaded("Featherboard")
						|| KOTH.getInstance().getScoreboardManager().isLoaded("QuickBoard")) {
					
					Bukkit.getScheduler().runTaskLater(KOTH.getInstance(), () -> {
						if (kothPlayer.getScoreboard() != null) {
							kothPlayer.getScoreboard().disappear();

							if (KOTH.getInstance().getScoreboardManager().isLoaded("TitleManager")) {
								KOTH.getInstance().getScoreboardManager().getScoreboardsLoaded().get("TitleManager").removeScoreboard(e.getPlayer());
							} else if (KOTH.getInstance().getScoreboardManager().isLoaded("Featherboard")) {
								KOTH.getInstance().getScoreboardManager().getScoreboardsLoaded().get("Featherboard").removeScoreboard(e.getPlayer());
							} else if (KOTH.getInstance().getScoreboardManager().isLoaded("QuickBoard")) {
								KOTH.getInstance().getScoreboardManager().getScoreboardsLoaded().get("QuickBoard").removeScoreboard(e.getPlayer());
							}

							kothPlayer.setScoreboard(new PlayerScoreboard(new Default(), e.getPlayer()));
						}
					}, 60L);
				}
			}
		}
	}
}
