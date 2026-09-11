package com.benzimmer123.koth.gui;

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
import com.benzimmer123.koth.util.ItemUtil;
import com.benzimmer123.koth.util.LangUtil;

public class KOTHGui {

	private KOTH plugin;

	public KOTHGui(KOTH instance) {
		plugin = instance;
	}

	public void checkKOTH(Player p, ItemStack i, String name) {
		if (i.getType().equals(XMaterial.EMERALD_BLOCK.parseMaterial())) {
			endKOTH(p, ChatColor.stripColor(i.getItemMeta().getDisplayName()));
		} else if (i.getType().equals(XMaterial.REDSTONE_BLOCK.parseMaterial())) {
			startKOTH(p, ChatColor.stripColor(i.getItemMeta().getDisplayName()), name);
		}
	}

	private void endKOTH(Player p, String kothName) {
		KOTHArena toDisable = null;

		for (KOTHArena koth : KOTHHandler.getInstance().getKOTHS()) {
			if (!koth.isActive())
				continue;
			if (koth.getName(false).equalsIgnoreCase(kothName)) {
				toDisable = koth;
			}
		}

		if (toDisable != null)
			toDisable.disable(p, true);
	}

	private void startKOTH(Player p, String kothName, String name) {
		if (name.equalsIgnoreCase(LangUtil.replaceString(plugin.getConfig().getString("KOTH_ADMIN_GUI.NAME")))) {
			Inventory inv = Bukkit.createInventory(null, 27, kothName + "'s Length");

			ItemStack goBack = new ItemStack(XMaterial.ARROW.parseMaterial());
			ItemMeta goBackmeta = goBack.getItemMeta();
			goBackmeta.setDisplayName(ChatColor.GREEN + "Go Back");
			goBack.setItemMeta(goBackmeta);

			inv.setItem(0, ItemUtil.getStarterItem(30, "seconds"));
			inv.setItem(1, ItemUtil.getStarterItem(1, "minute"));
			inv.setItem(2, ItemUtil.getStarterItem(2, "minutes"));
			inv.setItem(3, ItemUtil.getStarterItem(3, "minutes"));
			inv.setItem(4, ItemUtil.getStarterItem(4, "minutes"));
			inv.setItem(5, ItemUtil.getStarterItem(5, "minutes"));
			inv.setItem(6, ItemUtil.getStarterItem(6, "minutes"));
			inv.setItem(7, ItemUtil.getStarterItem(7, "minutes"));
			inv.setItem(8, ItemUtil.getStarterItem(8, "minutes"));
			inv.setItem(9, ItemUtil.getStarterItem(9, "minutes"));
			inv.setItem(10, ItemUtil.getStarterItem(10, "minutes"));
			inv.setItem(11, ItemUtil.getStarterItem(15, "minutes"));
			inv.setItem(12, ItemUtil.getStarterItem(20, "minutes"));
			inv.setItem(13, ItemUtil.getStarterItem(25, "minutes"));
			inv.setItem(14, ItemUtil.getStarterItem(30, "minutes"));
			inv.setItem(15, ItemUtil.getStarterItem(45, "minutes"));
			inv.setItem(16, ItemUtil.getStarterItem(1, "hour"));
			inv.setItem(17, ItemUtil.getStarterItem(2, "hours"));

			inv.setItem(22, goBack);

			p.openInventory(inv);
		} else {
			KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(p);
			KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(kothName);

			if (koth == null) {
				p.closeInventory();
				return;
			}

			int maxKoths = KOTH.getInstance().getConfig().getInt("MAX_KOTHS_ACTIVE");
			int amount = KOTH.getInstance().getKOTHManager().getActiveKOTHs().size();

			if (maxKoths != -1) {
				if (amount >= maxKoths) {
					LangUtil.sendMessage(p, LangUtil.MAX_KOTHS.toString().replaceAll("%amount%", maxKoths + ""));
					p.closeInventory();
					return;
				}
			}

			kothPlayer.setStartingKOTH(false);
			p.closeInventory();

			int seconds = plugin.getConfig().getInt("KOTH_STARTER_ITEM.START_TIME");
			int maxPoints = plugin.getConfig().getInt("KOTH_STARTER_ITEM.MAX_POINTS");
			int maxRunTime = plugin.getConfig().getInt("KOTH_STARTER_ITEM.MAX_RUN_TIME");

			KOTH.getInstance().getKOTHManager().callTask(koth, seconds, p, false, maxRunTime, maxPoints);
		}
	}

	public void lengthInventory(Player p, ItemStack i, String kothName) {
		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(kothName);

		if (koth == null)
			return;

		if (i.getType().equals(XMaterial.ARROW.parseMaterial())) {
			new OpeningGUI(plugin).openGUI(p, true);
		} else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "30 Seconds")) {
			KOTH.getInstance().getKOTHManager().callTask(koth, 30, p, false, 0, 0);
		} else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "1 Minute")) {
			KOTH.getInstance().getKOTHManager().callTask(koth, 60, p, false, 0, 0);
		} else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "2 Minutes")) {
			KOTH.getInstance().getKOTHManager().callTask(koth, 120, p, false, 0, 0);
		} else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "3 Minutes")) {
			KOTH.getInstance().getKOTHManager().callTask(koth, 180, p, false, 0, 0);
		} else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "4 Minutes")) {
			KOTH.getInstance().getKOTHManager().callTask(koth, 240, p, false, 0, 0);
		} else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "5 Minutes")) {
			KOTH.getInstance().getKOTHManager().callTask(koth, 300, p, false, 0, 0);
		} else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "6 Minutes")) {
			KOTH.getInstance().getKOTHManager().callTask(koth, 360, p, false, 0, 0);
		} else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "7 Minutes")) {
			KOTH.getInstance().getKOTHManager().callTask(koth, 420, p, false, 0, 0);
		} else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "8 Minutes")) {
			KOTH.getInstance().getKOTHManager().callTask(koth, 480, p, false, 0, 0);
		} else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "9 Minutes")) {
			KOTH.getInstance().getKOTHManager().callTask(koth, 540, p, false, 0, 0);
		} else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "10 Minutes")) {
			KOTH.getInstance().getKOTHManager().callTask(koth, 600, p, false, 0, 0);
		} else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "15 Minutes")) {
			KOTH.getInstance().getKOTHManager().callTask(koth, 900, p, false, 0, 0);
		} else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "20 Minutes")) {
			KOTH.getInstance().getKOTHManager().callTask(koth, 1200, p, false, 0, 0);
		} else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "25 Minutes")) {
			KOTH.getInstance().getKOTHManager().callTask(koth, 1500, p, false, 0, 0);
		} else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "30 Minutes")) {
			KOTH.getInstance().getKOTHManager().callTask(koth, 1800, p, false, 0, 0);
		} else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "45 Minutes")) {
			KOTH.getInstance().getKOTHManager().callTask(koth, 2700, p, false, 0, 0);
		} else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "1 Hour")) {
			KOTH.getInstance().getKOTHManager().callTask(koth, 3600, p, false, 0, 0);
		} else if (i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "2 Hours")) {
			KOTH.getInstance().getKOTHManager().callTask(koth, 7200, p, false, 0, 0);
		}

	}
}
