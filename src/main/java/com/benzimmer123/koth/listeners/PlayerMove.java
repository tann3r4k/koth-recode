package com.benzimmer123.koth.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.benzimmer123.koth.KOTH;

public class PlayerMove implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (e.getFrom().getBlockX() >> 4 == e.getTo().getBlockX() >> 4 && e.getFrom().getBlockZ() >> 4 == e.getTo().getBlockZ() >> 4 && e.getFrom()
				.getWorld() == e.getTo().getWorld()) {
			return;
		}

		KOTH.getInstance().getScoreboardManager().checkScoreboardChunks(e.getPlayer(), e.getTo().getChunk());
	}
}