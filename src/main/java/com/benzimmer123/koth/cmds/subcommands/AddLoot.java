package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.compatible.XMaterial;
import com.benzimmer123.koth.util.LangUtil;

public class AddLoot extends SubCommand {

	public AddLoot(KOTH instance) {
		super(instance, false);
		addAlias("addloot");
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);

		if (koth == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
			return false;
		}

		int percent = 0;

		if (args.length == 2) {
			try {
				percent = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				LangUtil.sendMessage(sender, LangUtil.INVALID_CHANCE.toString());
				return false;
			}
		}
		
		if (player.getItemInHand() == null || player.getItemInHand().getType().equals(XMaterial.AIR.parseMaterial())) {
			LangUtil.sendMessage(sender, LangUtil.NO_ITEM_IN_HAND.toString());
			return false;
		}

		LangUtil.sendMessage(sender, LangUtil.ADDED_LOOT.toString().replaceAll("%koth%", args[0]).replaceAll("%percent%", percent + ""));

		int slot = 0;

		if (!koth.getKOTHLoot().getLootItems(true).isEmpty()) {
			slot = koth.getKOTHLoot().getLootItems(true).size();
		}

		koth.getKOTHLoot().addLootItem(player.getItemInHand(), percent, slot);
		koth.save();
		return true;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 2 || length == 3) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "/koth addloot <KOTH> [CHANCE]";
	}

	@Override
	public String getPermission() {
		return "KOTH.ADDLOOT";
	}

	@Override
	public String getDescription() {
		return "Add item to KOTH loot (with percent chance).";
	}
}
