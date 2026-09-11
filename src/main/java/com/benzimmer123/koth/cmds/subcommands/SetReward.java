package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.enums.LootType;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.LangUtil;

public class SetReward extends SubCommand {

	public SetReward(KOTH instance) {
		super(instance, true);
		addAlias("setreward");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		if (!args[0].equalsIgnoreCase("INVENTORY") && !args[0].equalsIgnoreCase("NONE") && !args[0].equalsIgnoreCase("KEY")) {
			sender.sendMessage(ChatColor.RED + "Current Reward Types:" + ChatColor.WHITE + " KEY, INVENTORY, NONE");
			return false;
		}

		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[1]);

		if (koth == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
			return false;
		}

		if (args[0].equalsIgnoreCase("INVENTORY")) {
			koth.getKOTHLoot().setLootType(LootType.INVENTORY);
		} else if (args[0].equalsIgnoreCase("KEY")) {
			koth.getKOTHLoot().setLootType(LootType.KEY);
		} else if (args[0].equalsIgnoreCase("NONE")) {
			koth.getKOTHLoot().setLootType(LootType.NONE);
		}

		koth.save();

		LangUtil.sendMessage(sender, LangUtil.SET_LOOT_TYPE.toString().replaceAll("%koth%", koth.getName(true)).replaceAll("%type%", args[0]));
		return true;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 3) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "/koth setreward <INVENTORY/KEY/NONE> <KOTH>";
	}

	@Override
	public String getPermission() {
		return "KOTH.REWARDTYPE";
	}

	@Override
	public String getDescription() {
		return "Select the reward type.";
	}
}
