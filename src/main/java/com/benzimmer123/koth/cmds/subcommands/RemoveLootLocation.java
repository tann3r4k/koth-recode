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

public class RemoveLootLocation extends SubCommand {

	public RemoveLootLocation(KOTH instance) {
		super(instance, false);
		addAlias("removelootlocation");
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
		kothPlayer.setEditingKOTHLootAction(EditLootAction.REMOVE);
		kothPlayer.setEditingKOTHLootTimeout(System.currentTimeMillis() + 30000);
		
		LangUtil.sendMessage(sender, LangUtil.LEFT_CLICK_BLOCK_REMOVE.toString());
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
		return "/koth removelootlocation <KOTH>";
	}

	@Override
	public String getPermission() {
		return "KOTH.REMOVELOOTLOCATION";
	}

	@Override
	public String getDescription() {
		return "Remove a KOTH's loot location.";
	}

}
