package cc.javajobs.factionsbridge.bridge.infrastructure.struct;

import org.bukkit.entity.Player;

public interface FPlayer {
	boolean hasFaction();

	Faction getFaction();

	Player getPlayer();

	String getName();

	boolean isOnline();
}
