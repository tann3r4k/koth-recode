package com.benzimmer123.koth.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.scoreboard.PlayerScoreboard;
import com.benzimmer123.koth.scoreboard.providers.Default;
import com.benzimmer123.koth.util.LangUtil;
import com.benzimmer123.koth.util.PlaceholderUtil;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		KOTHHandler.getInstance().addKOTHPlayer(e.getPlayer());

		for (KOTHArena koth : KOTHHandler.getInstance().getKOTHS()) {
			if (koth.getKOTHAutoStart().checkAutoStart(Bukkit.getOnlinePlayers().size())) {
				KOTH.getInstance().getKOTHManager().callTask(koth, koth.getKOTHAutoStart().getRunTime(), null, false, 0, 0);
				koth.getKOTHAutoStart().resetAutoStart();
				koth.save();
			}
		}

		KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(e.getPlayer());

		Bukkit.getScheduler().runTaskLater(KOTH.getInstance(), () -> {
			List<KOTHArena> active = KOTH.getInstance().getKOTHManager().getActiveKOTHs();

			if (!active.isEmpty()) {
				if (KOTH.getInstance().getConfig().getBoolean("ANNOUNCE_KOTH_ON_JOIN")) {
					LangUtil.sendMessage(e.getPlayer(), LangUtil.ANNOUNCE_KOTH_ON_JOIN.toString().replaceAll("%koth%", PlaceholderUtil.getKoths())
							.replaceAll("%x%", PlaceholderUtil.getXCords()).replaceAll("%y%", PlaceholderUtil.getYCords()).replaceAll("%z%",
									PlaceholderUtil.getZCords()).replaceAll("%time%", PlaceholderUtil.getTimes()).replaceAll("%capper%",
											PlaceholderUtil.getCappers()));
				}
			}
		}, 2L);

		if (!KOTH.getInstance().getScoreboardManager().isScoreboardAllowed(e.getPlayer()))
			return;

		KOTH.getInstance().getScoreboardManager().checkScoreboardChunks(e.getPlayer(), e.getPlayer().getLocation().getChunk());

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
