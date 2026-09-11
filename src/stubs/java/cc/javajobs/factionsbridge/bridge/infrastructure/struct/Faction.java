package cc.javajobs.factionsbridge.bridge.infrastructure.struct;

import java.util.Collections;
import java.util.List;

public interface Faction {
	String getId();

	String getName();

	FPlayer getLeader();

	boolean isWilderness();

	boolean isWarZone();

	boolean isSafeZone();

	boolean isServerFaction();

	default List<FPlayer> getOnlineMembers() {
		return Collections.emptyList();
	}
}
