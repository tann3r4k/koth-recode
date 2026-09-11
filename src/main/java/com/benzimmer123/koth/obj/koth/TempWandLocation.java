package com.benzimmer123.koth.obj.koth;

import org.bukkit.Location;

public class TempWandLocation {

	private Location location1;
	private Location location2;

	public void setLocation(int num, Location loc) {
		if (num == 1) {
			this.location1 = loc;
		} else {
			this.location2 = loc;
		}
	}

	public Location getLocation(int num) {
		if (num == 1) {
			return location1;
		} else {
			return location2;
		}
	}

	public boolean hasLocations() {
		if (location1 != null && location2 != null)
			return true;
		return false;
	}

}
