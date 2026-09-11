package com.benzimmer123.koth.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.compatible.XMaterial;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.util.LangUtil;

public class OpeningGUI {

	private KOTH plugin;

	public OpeningGUI(KOTH instance) {
		plugin = instance;
	}

	public void openGUI(Player p, boolean showActive) {
		Inventory inv;

		if (showActive)
			inv = Bukkit.createInventory(null, plugin.getConfig().getInt("KOTH_ADMIN_GUI.SIZE"), LangUtil.replaceString(plugin.getConfig()
					.getString("KOTH_ADMIN_GUI.NAME")));
		else
			inv = Bukkit.createInventory(null, plugin.getConfig().getInt("KOTH_PLAYER_GUI.SIZE"), LangUtil.replaceString(plugin.getConfig()
					.getString("KOTH_PLAYER_GUI.NAME")));

		for (KOTHArena koth : KOTHHandler.getInstance().getKOTHS()) {
			if (KOTH.getInstance().getConfig().getStringList("KOTH_STARTER_ITEM.BLACKLISTED").contains(koth.getName(false).toLowerCase()))
				continue;

			if (koth.isActive()) {
				ItemStack isActive = new ItemStack(XMaterial.EMERALD_BLOCK.parseMaterial());
				ItemMeta isActiveMeta = isActive.getItemMeta();
				isActiveMeta.setDisplayName(ChatColor.GREEN + koth.getName(true));
				isActiveMeta.setLore(Arrays.asList(LangUtil.GUI_INACTIVE.toString()));
				isActive.setItemMeta(isActiveMeta);

				if (showActive)
					inv.addItem(isActive);
			} else {
				ItemStack notActive = new ItemStack(XMaterial.REDSTONE_BLOCK.parseMaterial());
				ItemMeta notActiveMeta = notActive.getItemMeta();
				notActiveMeta.setDisplayName(ChatColor.RED + koth.getName(true));
				notActiveMeta.setLore(Arrays.asList(LangUtil.GUI_INACTIVE.toString()));
				notActive.setItemMeta(notActiveMeta);

				inv.addItem(notActive);
			}
		}

		ItemStack nextPage = new ItemStack(XMaterial.ARROW.parseMaterial());
		ItemMeta nextPageMeta = nextPage.getItemMeta();
		nextPageMeta.setDisplayName(LangUtil.GUI_NEXT_PAGE.toString());
		nextPage.setItemMeta(nextPageMeta);

		if (showActive) {
			inv.setItem(26, nextPage);
		} else {
			KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(p);
			kothPlayer.setStartingKOTH(true);
		}

		p.openInventory(inv);
	}
}
