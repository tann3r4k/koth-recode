package com.benzimmer123.koth.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.gui.KOTHGui;
import com.benzimmer123.koth.util.LangUtil;

public class InventoryClick implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getCurrentItem() == null)
			return;

		if (e.getView().getTitle().equals(LangUtil.replaceString(KOTH.getInstance().getConfig().getString("REWARDS.VIEW_INVENTORY_NAME")))) {
			e.setCancelled(true);
		} else if (e.getView().getTitle().equals(LangUtil.replaceString(KOTH.getInstance().getConfig().getString("KOTH_PLAYER_GUI.NAME"))) || e
				.getView().getTitle().equals(LangUtil.replaceString(KOTH.getInstance().getConfig().getString("KOTH_ADMIN_GUI.NAME")))) {
			e.setCancelled(true);
			new KOTHGui(KOTH.getInstance()).checkKOTH((Player) e.getWhoClicked(), e.getCurrentItem(), e.getView().getTitle());
		} else if (e.getView().getTitle().endsWith(" Length")) {
			e.setCancelled(true);
			e.getWhoClicked().closeInventory();

			String[] name = e.getView().getTitle().split("'");
			String koth = name[0];

			new KOTHGui(KOTH.getInstance()).lengthInventory((Player) e.getWhoClicked(), e.getCurrentItem(), koth);
		}
	}
}
