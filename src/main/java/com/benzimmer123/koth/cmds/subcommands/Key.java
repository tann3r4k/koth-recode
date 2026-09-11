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

public class Key extends SubCommand {

	public Key(KOTH instance) {
		super(instance, true);
		addAlias("givekey");
		addAlias("key");
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

		ItemStack key = ItemUtil.getKey();
		t.getInventory().addItem(key);

		if (t == sender) {
			LangUtil.sendMessage(t, LangUtil.KEY_RECEIVED.toString().replaceAll("%player%", "Yourself"));
		} else {
			String name = sender instanceof ConsoleCommandSender ? "Console" : sender.getName();
			LangUtil.sendMessage(sender, LangUtil.KEY_GIVEN.toString().replaceAll("%player%", t.getName()));
			LangUtil.sendMessage(t, LangUtil.KEY_RECEIVED.toString().replaceAll("%player%", name));
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
		return "/koth key [player]";
	}

	@Override
	public String getPermission() {
		return "KOTH.KEY";
	}

	@Override
	public String getDescription() {
		return "Give a player the KOTH key.";
	}
}
