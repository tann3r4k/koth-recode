package com.benzimmer123.koth.obj.koth.serial;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.api.objects.KOTHRegion;
import com.benzimmer123.koth.handlers.RegionHandler;
import com.benzimmer123.koth.storage.GsonStorage;

public class MemoryKOTHRegion implements KOTHRegion, Serializable {

	private static final long serialVersionUID = -3100985878998246671L;
	private String name;
	private String world;
	private int x1;
	private int y1;
	private int z1;
	private int x2;
	private int y2;
	private int z2;

	public MemoryKOTHRegion(String name, Location loc1, Location loc2) {
		this.name = name;
		this.world = loc1.getWorld().getName();
		this.x1 = loc1.getBlockX();
		this.y1 = loc1.getBlockY();
		this.z1 = loc1.getBlockZ();
		this.x2 = loc2.getBlockX();
		this.y2 = loc2.getBlockY();
		this.z2 = loc2.getBlockZ();
		RegionHandler.getInstance().addRegion(this);
		save();
	}

	public boolean isInRegion(Player p) {
		if (getLocation1() == null)
			return false;
		if (getLocation2() == null)
			return false;
		MemoryCuboid cuboid = new MemoryCuboid(getLocation1(), getLocation2());
		if (cuboid.contains(p.getLocation()))
			return true;
		return false;
	}

	public String getName() {
		return name;
	}

	private Location getLocation1() {
		if (Bukkit.getWorld(world) != null)
			return new Location(Bukkit.getWorld(world), x1, y1, z1);
		return null;
	}

	private Location getLocation2() {
		if (Bukkit.getWorld(world) != null)
			return new Location(Bukkit.getWorld(world), x2, y2, z2);
		return null;
	}

	private void save() {
		GsonStorage.serialize(this, "regions/" + getName() + ".json", "region");
	}

}
