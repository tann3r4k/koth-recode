package cc.javajobs.factionsbridge.bridge.infrastructure.struct;

import org.bukkit.OfflinePlayer;

public interface FactionsAPI {
	FPlayer getFPlayer(OfflinePlayer player);

	Faction getFaction(String id);
}
