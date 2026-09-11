package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.ItemUtil;
import com.benzimmer123.koth.util.LangUtil;

public class StarterItem extends SubCommand {

	public StarterItem(KOTH instance) {
		super(instance, true);
		addAlias("starteritem");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		if (Bukkit.getPlayer(args[0]) == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_PLAYER.toString());
			return false;
		}

		Player to = Bukkit.getPlayer(args[0]);

		to.getInventory().addItem(ItemUtil.getStarterItem());

		String name = sender instanceof ConsoleCommandSender ? "Console" : sender.getName();

		LangUtil.sendMessage(sender, LangUtil.STARTER_ITEM_GIVEN.toString().replaceAll("%player%", to.getName()));
		LangUtil.sendMessage(to, LangUtil.STARTER_ITEM_RECEIVED.toString().replaceAll("%player%", name));
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
		return "/koth starteritem <PLAYER>";
	}

	@Override
	public String getPermission() {
		return "KOTH.GIVESTARTERITEM";
	}

	@Override
	public String getDescription() {
		return "Give a player access to start a KOTH with an item.";
	}

}
