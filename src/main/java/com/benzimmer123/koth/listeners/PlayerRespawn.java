package com.benzimmer123.koth.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.obj.koth.TempInventory;
import com.benzimmer123.koth.obj.koth.TempKOTHPlayer;

public class PlayerRespawn implements Listener {

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		TempKOTHPlayer kothPlayer = (TempKOTHPlayer) KOTHHandler.getInstance().getKOTHPlayer(e.getPlayer());

		if (kothPlayer.hasStoredInventory()) {
			TempInventory inventory = kothPlayer.getStoredInventory();
			e.getPlayer().getInventory().setContents(inventory.getInventory());
			e.getPlayer().getInventory().setArmorContents(inventory.getArmor());
			kothPlayer.setStoredInventory(null);
		}
	}
}
