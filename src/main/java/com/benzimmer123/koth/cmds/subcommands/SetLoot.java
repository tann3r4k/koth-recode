package com.benzimmer123.koth.cmds.subcommands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.KOTHLootItem;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.LangUtil;

public class SetLoot extends SubCommand {

	public SetLoot(KOTH instance) {
		super(instance, false);
		addAlias("setloot");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);

		if (koth == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
			return false;
		}

		if (Bukkit.getPluginManager().isPluginEnabled("OpenInv")) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_COMPATIBILITY.toString());
			return false;
		}

		Inventory inv = Bukkit.createInventory(null, 54, "KOTH Rewards: " + koth.getName(true));

		List<KOTHLootItem> lootItems = koth.getKOTHLoot().getLootItems(true);

		for (KOTHLootItem lootItem : koth.getKOTHLoot().getLootItems(true)) {
			if(lootItem.getPercent() != 0 && lootItem.getPercent() != 100) {
				LangUtil.sendMessage(sender, LangUtil.CANNOT_EDIT_PERCENT_ITEMS.toString());
				return false;
			}
		}

		if (lootItems != null && !lootItems.isEmpty()) {
			for (KOTHLootItem item : lootItems) {
				int slot = item.getSlot();
				if (item.getSlot() >= 54) {
					if (inv.firstEmpty() == -1)
						continue;
					slot = inv.firstEmpty();
				}
				inv.setItem(slot, item.toItemStack());
			}
		}

		player.openInventory(inv);
		return true;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 2) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "/koth setloot <KOTH>";
	}

	@Override
	public String getPermission() {
		return "KOTH.SETLOOT";
	}

	@Override
	public String getDescription() {
		return "Set the koth loot rewards for a KOTH.";
	}
}
