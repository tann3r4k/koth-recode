package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.KOTHLootItem;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.util.LangUtil;

public class Loot extends SubCommand {

	public Loot(KOTH instance) {
		super(instance, false);
		addAlias("loot");
		addAlias("viewloot");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		KOTHArena koth;

		if (args.length == 0) {
			if (KOTHHandler.getInstance().getKOTHS().isEmpty() || KOTHHandler.getInstance().getKOTHS().size() > 1) {
				sender.sendMessage(ChatColor.RED + "Correct Usage: " + ChatColor.YELLOW + getHelp());
				return false;
			}

			koth = KOTHHandler.getInstance().getKOTHS().get(0);
		} else {
			koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);
		}

		if (koth == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
			return false;
		}

		int invSize = KOTH.getInstance().getRewardManager().getInventorySize(koth);

		Inventory lootInventory = Bukkit.createInventory(null, invSize, LangUtil.replaceString(plugin.getConfig().getString(
				"REWARDS.VIEW_INVENTORY_NAME")));

		for (KOTHLootItem item : koth.getKOTHLoot().getLootItems(true)) {
			int slot = item.getSlot();
			if (item.getSlot() >= invSize) {
				if (lootInventory.firstEmpty() == -1)
					continue;
				slot = lootInventory.firstEmpty();
			}
			lootInventory.setItem(slot, item.toItemStack());
		}

		player.openInventory(lootInventory);
		return true;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 2 || length == 1) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "/koth loot <KOTH>";
	}

	@Override
	public String getPermission() {
		return "KOTH.VIEWLOOT";
	}

	@Override
	public String getDescription() {
		return "View the KOTH loot.";
	}
}
