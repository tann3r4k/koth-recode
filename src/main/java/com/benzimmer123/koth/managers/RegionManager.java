package com.benzimmer123.koth.managers;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHRegion;
import com.benzimmer123.koth.handlers.RegionHandler;

public class RegionManager {

	public KOTHRegion getRegionFromString(String name) {
		for (KOTHRegion region : RegionHandler.getInstance().getRegions()) {
			if (region.getName().equalsIgnoreCase(name)) {
				return region;
			}
		}
		return null;
	}

	public boolean checkInventoryRestoreRegions(Player p) {
		if (KOTH.getInstance().getConfig().getBoolean("KEEP_INVENTORY_WHILE_ACTIVE.ONLY_IN_REGION.ENABLED")) {
			List<String> worldGuardRegions = KOTH.getInstance().getConfig().getStringList("KEEP_INVENTORY_WHILE_ACTIVE.ONLY_IN_REGION.REGIONS");
			worldGuardRegions.stream().forEach(region -> region = region.toLowerCase());
			if (!checkRegions(p, worldGuardRegions)) {
				return false;
			}
		}
		return true;
	}

	public boolean checkWGScoreboardRegions(Player p) {
		if (KOTH.getInstance().getConfig().getBoolean("SCOREBOARD.ONLY_IN_REGION.ENABLED")) {
			List<String> worldGuardRegions = KOTH.getInstance().getConfig().getStringList("SCOREBOARD.ONLY_IN_REGION.REGIONS");
			worldGuardRegions.stream().forEach(region -> region = region.toLowerCase());
			if (!checkRegions(p, worldGuardRegions)) {
				return false;
			}
		}
		return true;
	}

	public int getDistance(Location a, Location b) {
		if (a.getWorld().equals(b.getWorld()))
			return (int) Math.round(a.distance(b));
		return 0;
	}

	private boolean checkRegions(Player player, List<String> regionList) {
		for (KOTHRegion region : RegionHandler.getInstance().getRegions()) {
			if (regionList.contains(region.getName().toLowerCase()) && region.isInRegion(player)) {
				return true;
			}
		}
		return false;
	}

}
