package dev.kitteh.factions;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;

public interface Faction {
	int id();

	String tag();

	boolean isWilderness();

	boolean isSafeZone();

	boolean isWarZone();

	default boolean isNormal() {
		return !isWilderness() && !isSafeZone() && !isWarZone();
	}

	FPlayer admin();

	Set<FPlayer> membersOnline(boolean online);

	default List<Player> membersOnlineAsPlayers() {
		return Collections.emptyList();
	}
}
