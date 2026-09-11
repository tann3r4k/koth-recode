package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.obj.koth.TempKOTHPlayer;
import com.benzimmer123.koth.obj.koth.TempWandLocation;
import com.benzimmer123.koth.util.ItemUtil;
import com.benzimmer123.koth.util.LangUtil;

public class ReCreate extends SubCommand {

	public ReCreate(KOTH instance) {
		super(instance, false);
		addAlias("recreate");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(player);

		if (kothPlayer.hasWandLocation()) {
			KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);

			if (koth == null) {
				LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
				return false;
			}

			if (koth.isActive()) {
				LangUtil.sendMessage(sender, LangUtil.KOTH_CURRENTLY_ACTIVE.toString());
				return false;
			}

			TempWandLocation wandLoc = ((TempKOTHPlayer) kothPlayer).getWandLocation();

			koth.getKOTHLocation().setLocation1(wandLoc.getLocation(1));
			koth.getKOTHLocation().setLocation2(wandLoc.getLocation(2));
			koth.save();

			LangUtil.sendMessage(sender, LangUtil.RECREATED_KOTH.toString().replaceAll("%koth%", args[0]));
			return true;
		} else {
			LangUtil.sendMessage(sender, LangUtil.NO_WAND.toString());

			ItemStack kothWand = ItemUtil.getWand();

			if (!player.getInventory().contains(kothWand)) {
				player.getInventory().addItem(kothWand);
				LangUtil.sendMessage(sender, LangUtil.WAND_ADDED.toString());
			}
		}
		return false;
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
		return "/koth recreate <KOTH>";
	}

	@Override
	public String getPermission() {
		return "KOTH.RECREATE";
	}

	@Override
	public String getDescription() {
		return "Recreate a current KOTH zone.";
	}
}
