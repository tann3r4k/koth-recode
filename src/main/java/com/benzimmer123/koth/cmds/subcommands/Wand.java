package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.util.ItemUtil;
import com.benzimmer123.koth.util.LangUtil;

public class Wand extends SubCommand {

	public Wand(KOTH instance) {
		super(instance, false);
		addAlias("wand");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		Player t;

		if (args.length > 1) {
			if (Bukkit.getPlayer(args[0]) == null) {
				LangUtil.sendMessage(sender, LangUtil.INVALID_PLAYER.toString());
				return false;
			}
			t = Bukkit.getPlayer(args[0]);
		} else if (!(sender instanceof ConsoleCommandSender)) {
			t = (Player) sender;
		} else {
			LangUtil.sendMessage(sender, LangUtil.INVALID_PLAYER.toString());
			return false;
		}

		ItemStack kothWand = ItemUtil.getWand();
		t.getInventory().addItem(kothWand);

		if (t == sender) {
			LangUtil.sendMessage(sender, LangUtil.WAND_ADDED.toString());
		} else {
			String name = sender instanceof ConsoleCommandSender ? "Console" : sender.getName();
			LangUtil.sendMessage(sender, LangUtil.WAND_GIVEN.toString().replaceAll("%player%", t.getName()));
			LangUtil.sendMessage(t, LangUtil.WAND_RECEIVED.toString().replaceAll("%player%", name));
		}

		return false;
	}

	@Override
	public boolean validArgumentLength(int length) {
		if (length == 1 || length == 2) {
			return true;
		}
		return false;
	}

	@Override
	public String getHelp() {
		return "/koth wand [player]";
	}

	@Override
	public String getPermission() {
		return "KOTH.WAND";
	}

	@Override
	public String getDescription() {
		return "Give a player a KOTH wand to create regions.";
	}
}
