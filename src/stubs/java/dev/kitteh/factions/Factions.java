package dev.kitteh.factions;

public interface Factions {
	static Factions factions() {
		return null;
	}

	Faction get(int id);

	Faction get(String tag);
}
