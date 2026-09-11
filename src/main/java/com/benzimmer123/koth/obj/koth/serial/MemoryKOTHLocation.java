package com.benzimmer123.koth.obj.koth.serial;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.benzimmer123.koth.api.objects.KOTHLocation;

public class MemoryKOTHLocation implements KOTHLocation, Serializable {

	private static final long serialVersionUID = 3966071133082622459L;
	private String world;
	private int x1;
	private int y1;
	private int z1;
	private int x2;
	private int y2;
	private int z2;

	protected MemoryKOTHLocation(Location loc1, Location loc2) {
		this.world = loc1.getWorld().getName();
		this.x1 = loc1.getBlockX();
		this.y1 = loc1.getBlockY();
		this.z1 = loc1.getBlockZ();
		this.x2 = loc2.getBlockX();
		this.y2 = loc2.getBlockY();
		this.z2 = loc2.getBlockZ();
	}

	public String getWorld() {
		return world;
	}

	public Location getLocation1() {
		return new Location(Bukkit.getWorld(world), x1, y1, z1);
	}

	public Location getLocation2() {
		return new Location(Bukkit.getWorld(world), x2, y2, z2);
	}

	public void setLocation1(Location loc) {
		this.world = loc.getWorld().getName();
		this.x1 = loc.getBlockX();
		this.y1 = loc.getBlockY();
		this.z1 = loc.getBlockZ();
	}

	public void setLocation2(Location loc) {
		this.world = loc.getWorld().getName();
		this.x2 = loc.getBlockX();
		this.y2 = loc.getBlockY();
		this.z2 = loc.getBlockZ();
	}
}
