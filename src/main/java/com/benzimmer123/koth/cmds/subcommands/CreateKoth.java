package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.obj.koth.TempKOTHPlayer;
import com.benzimmer123.koth.obj.koth.TempWandLocation;
import com.benzimmer123.koth.obj.koth.serial.MemoryKOTHArena;
import com.benzimmer123.koth.util.ItemUtil;
import com.benzimmer123.koth.util.LangUtil;

public class CreateKoth extends SubCommand {

	public CreateKoth(KOTH instance) {
		super(instance, false);
		addAlias("create");
		addAlias("new");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(player);

		if (kothPlayer.hasWandLocation()) {
			if (KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]) != null) {
				LangUtil.sendMessage(sender, LangUtil.KOTH_ALREADY_SET.toString());
				return false;
			}

			TempWandLocation wandLoc = ((TempKOTHPlayer) kothPlayer).getWandLocation();

			if (!wandLoc.getLocation(1).getWorld().equals(wandLoc.getLocation(2).getWorld())) {
				LangUtil.sendMessage(sender, LangUtil.INVALID_WORLD.toString());
				return false;
			}
			
			boolean allLetters = args[0].chars().allMatch(Character::isLetter);

			if (!allLetters) {
				LangUtil.sendMessage(sender, LangUtil.ILLEGAL_CHAR.toString());
				return false;
			}

			if (wandLoc.getLocation(1).getBlockY() == wandLoc.getLocation(2).getBlockY()) {
				Location location1 = new Location(wandLoc.getLocation(1).getWorld(), wandLoc.getLocation(1).getX(), 255, wandLoc.getLocation(1)
						.getZ());
				Location location2 = new Location(wandLoc.getLocation(2).getWorld(), wandLoc.getLocation(2).getX(), 0, wandLoc.getLocation(2).getZ());
				wandLoc.setLocation(1, location1);
				wandLoc.setLocation(2, location2);
			}

			new MemoryKOTHArena(args[0], wandLoc.getLocation(1), wandLoc.getLocation(2));
			LangUtil.sendMessage(sender, LangUtil.CREATED_KOTH.toString().replaceAll("%koth%", args[0]));
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
		return "/koth create <name>";
	}

	@Override
	public String getPermission() {
		return "KOTH.SETKOTH";
	}

	@Override
	public String getDescription() {
		return "Create a new KOTH area.";
	}
}