package com.benzimmer123.koth.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.util.ItemUtil;
import com.benzimmer123.koth.util.LangUtil;

public class InventoryClose implements Listener {

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getView().getTitle().startsWith("KOTH Rewards: ")) {
			KOTH.getInstance().getRewardManager().setRewards(e.getInventory(), (Player) e.getPlayer());
		} else if (e.getView().getTitle().equalsIgnoreCase(LangUtil.replaceString(KOTH.getInstance().getConfig().getString("KOTH_PLAYER_GUI.NAME")))) {
			KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer((Player) e.getPlayer());
			if (kothPlayer.isStartingKOTH()) {
				e.getPlayer().getInventory().addItem(ItemUtil.getStarterItem());				
				kothPlayer.setStartingKOTH(false);
				
				Bukkit.getScheduler().runTaskLater(KOTH.getInstance(), () -> {
					((Player) e.getPlayer()).updateInventory();
				}, 5L);
			}
		}
	}
}
