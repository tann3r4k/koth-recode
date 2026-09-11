package com.benzimmer123.koth.cmds.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.enums.EditLootAction;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.util.LangUtil;

public class SetLootLocation extends SubCommand {

	public SetLootLocation(KOTH instance) {
		super(instance, false);
		addAlias("setlootlocation");
		addAlias("addlootlocation");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		KOTHArena koth = KOTH.getInstance().getKOTHManager().getKOTHFromString(args[0]);

		if (koth == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_KOTH.toString());
			return false;
		}
		
		KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(player);

		kothPlayer.setEditingKOTHLoot(koth);
		kothPlayer.setEditingKOTHLootAction(EditLootAction.ADD);
		kothPlayer.setEditingKOTHLootTimeout(System.currentTimeMillis() + 30000);

		LangUtil.sendMessage(sender, LangUtil.LEFT_CLICK_BLOCK_ADD.toString());
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
		return "/koth setlootlocation <KOTH>";
	}

	@Override
	public String getPermission() {
		return "KOTH.SETLOOTLOCATION";
	}

	@Override
	public String getDescription() {
		return "Set a KOTH's loot location";
	}
}
