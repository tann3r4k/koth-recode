package com.benzimmer123.koth.obj.koth.serial;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class MemoryCuboid implements Serializable {
	
	private static final long serialVersionUID = 6305192333264842159L;
	private final String worldName;
	private final int x1, y1, z1;
	private final int x2, y2, z2;

	public MemoryCuboid(Location l1, Location l2) {
		if(l1.getWorld() == null || l2.getWorld() == null)
			throw new IllegalArgumentException("World doesn't exist");
		if (!l1.getWorld().equals(l2.getWorld()))
			throw new IllegalArgumentException("Locations must be on the same world");
		this.worldName = l1.getWorld().getName();
		this.x1 = Math.min(l1.getBlockX(), l2.getBlockX());
		this.y1 = Math.min(l1.getBlockY(), l2.getBlockY());
		this.z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
		this.x2 = Math.max(l1.getBlockX(), l2.getBlockX());
		this.y2 = Math.max(l1.getBlockY(), l2.getBlockY());
		this.z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());
	}

	private boolean contains(int x, int y, int z) {
		return x >= this.x1 && x <= this.x2 && y >= this.y1 && y <= this.y2 && z >= this.z1 && z <= this.z2;
	}

	public Location getCenter() {
		return new Location(Bukkit.getWorld(worldName), (x2 - x1) / 2 + x1, (y2 - y1) / 2 + y1, (z2 - z1) / 2 + z1);
	}
	
	public boolean contains(Location l) {
		if (!this.worldName.equals(l.getWorld().getName()))
			return false;
		return this.contains(l.getBlockX(), l.getBlockY(), l.getBlockZ());
	}
}