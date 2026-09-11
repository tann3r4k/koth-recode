package com.massivecraft.factions;

public class FPlayers {
	public static FPlayers getInstance() {
		return new FPlayers();
	}

	public FPlayer getByPlayer(org.bukkit.entity.Player p) {
		return new FPlayer();
	}

	public FPlayer getByOfflinePlayer(org.bukkit.OfflinePlayer p) {
		return new FPlayer();
	}
}
