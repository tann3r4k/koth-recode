package com.benzimmer123.koth.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;

public class PlayerTeleport implements Listener {

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		KOTHArena koth = KOTH.getInstance().getKOTHManager().isCapping(e.getPlayer());

		if (koth != null) {
			if (e.getPlayer().isDead() || !koth.contains(e.getPlayer().getLocation())) {
				koth.setCapper(null, true);
			}
		}
	}
}
