package dev.kitteh.factions;

import java.util.UUID;

import org.bukkit.OfflinePlayer;

public interface FPlayers {
	static FPlayers fPlayers() {
		return null;
	}

	FPlayer get(UUID uuid);

	default FPlayer get(OfflinePlayer player) {
		return player == null ? null : get(player.getUniqueId());
	}
}
