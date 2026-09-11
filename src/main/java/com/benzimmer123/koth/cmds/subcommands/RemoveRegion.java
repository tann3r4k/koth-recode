package com.benzimmer123.koth.cmds.subcommands;

import java.io.File;

import org.bukkit.command.CommandSender;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHRegion;
import com.benzimmer123.koth.cmds.rootcommand.SubCommand;
import com.benzimmer123.koth.handlers.RegionHandler;
import com.benzimmer123.koth.obj.koth.serial.MemoryKOTHRegion;
import com.benzimmer123.koth.storage.GsonStorage;
import com.benzimmer123.koth.util.LangUtil;

public class RemoveRegion extends SubCommand {

	public RemoveRegion(KOTH instance) {
		super(instance, true);
		addAlias("removeregion");
		addAlias("deleteregion");
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		KOTHRegion region = KOTH.getInstance().getRegionManager().getRegionFromString(args[0]);

		if (region == null) {
			LangUtil.sendMessage(sender, LangUtil.INVALID_REGION.toString());
			return false;
		}

		File regionFile = new File(plugin.getDataFolder(), "regions/" + region.getName().toLowerCase() + ".json");

		if (regionFile.exists()) {
			region = (KOTHRegion) GsonStorage.deserialize(MemoryKOTHRegion.class, regionFile.getPath(), "region");
			regionFile.delete();
		}

		RegionHandler.getInstance().removeRegion(args[0]);
		LangUtil.sendMessage(sender, LangUtil.REMOVED_REGION.toString().replaceAll("%region%", args[0]));
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
		return "/koth removeregion <REGION>";
	}

	@Override
	public String getPermission() {
		return "KOTH.REMOVEREGION";
	}

	@Override
	public String getDescription() {
		return "Remove a custom KOTH region.";
	}
}
