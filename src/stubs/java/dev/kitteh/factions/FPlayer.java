package dev.kitteh.factions;

import org.bukkit.entity.Player;

public interface FPlayer {
	boolean hasFaction();

	Faction faction();

	Player asPlayer();

	String name();

	boolean isOnline();
}
