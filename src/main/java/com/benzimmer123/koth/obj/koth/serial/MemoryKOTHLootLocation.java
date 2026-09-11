package com.benzimmer123.koth.obj.koth.serial;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.benzimmer123.koth.api.objects.KOTHLootLocation;

public class MemoryKOTHLootLocation implements KOTHLootLocation, Serializable {

	private static final long serialVersionUID = 5267457391472586136L;
	private String world;
	private int x;
	private int y;
	private int z;

	public MemoryKOTHLootLocation(Location loc) {
		this.world = loc.getWorld().getName();
		this.x = loc.getBlockX();
		this.y = loc.getBlockY();
		this.z = loc.getBlockZ();
	}

	public Location toLocation() {
		return new Location(Bukkit.getWorld(world), x, y, z);
	}
}