package com.benzimmer123.koth.handlers;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.obj.koth.TempKOTHPlayer;
import com.benzimmer123.koth.obj.koth.serial.MemoryKOTHArena;
import com.benzimmer123.koth.obj.koth.serial.MemoryKOTHTick;
import com.benzimmer123.koth.storage.GsonStorage;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class KOTHHandler {

	private final static KOTHHandler INSTANCE;
	private Map<ZonedDateTime, KOTHArena> upcomingDates;
	private Map<UUID, KOTHPlayer> kothPlayers;
	private Map<String, Double> totalKothPoints;
	private List<KOTHArena> koths;
	private String lastCappedTeam;
	private String lastCappedPlayer;
	private boolean isPluginLoaded;
	private MemoryKOTHTick kothTick;

	static {
		INSTANCE = new KOTHHandler();
	}

	private KOTHHandler() {
		koths = Lists.newArrayList();
		kothPlayers = Maps.newHashMap();
		upcomingDates = Maps.newHashMap();
		totalKothPoints = Maps.newHashMap();

		kothTick = new MemoryKOTHTick();

		for (File file : new File(KOTH.getInstance().getDataFolder() + "/koths").listFiles()) {
			KOTHArena koth = GsonStorage.deserialize(MemoryKOTHArena.class, file.getPath(), "koth");
			if (koth != null) {
				addKOTH(koth);

				if (koth.isActive()) {
					koth.resetData(null);
					koth.start(koth.getKOTHDetails().getMaxRunTime(), koth.getKOTHDetails().getRequiredTime(), koth.getKOTHDetails().getMaxPoints(),
							false);
				}
			}
		}
	}

	public static KOTHHandler getInstance() {
		return INSTANCE;
	}

	public MemoryKOTHTick getKOTHTick() {
		return kothTick;
	}

	public double getTotalPoints(String name) {
		if (name != null && totalKothPoints.containsKey(name))
			return totalKothPoints.get(name);
		return 0;
	}

	public String getTotalPointsPosition(int pos) {
		if (totalKothPoints.size() >= pos) {
			String[] playerList = (String[]) totalKothPoints.keySet().toArray(new String[totalKothPoints.keySet().size()]);
			return playerList[pos - 1];
		}
		return null;
	}

	public void addTotalPoints(String name, double amount) {
		totalKothPoints.put(name, getTotalPoints(name) + amount);
		sortTotalPoints();
	}

	public void removeTotalPoints(String name, int amount) {
		double newTotal = getTotalPoints(name) - amount;
		totalKothPoints.put(name, newTotal);
		sortTotalPoints();
	}

	public void sortTotalPoints() {
		LinkedHashMap<String, Double> treeMap = new LinkedHashMap<String, Double>(totalKothPoints);
		treeMap = treeMap.entrySet().stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).collect(Collectors.toMap(Map.Entry::getKey,
				Map.Entry::getValue, (oldV, newV) -> oldV, LinkedHashMap::new));
		totalKothPoints = treeMap;
	}

	public boolean isPluginLoaded() {
		return isPluginLoaded;
	}

	public void setPluginLoaded(boolean isPluginLoaded) {
		this.isPluginLoaded = isPluginLoaded;
	}

	public Map<ZonedDateTime, KOTHArena> getUpcomingDates() {
		return upcomingDates;
	}

	public void setUpcomingDates(Map<ZonedDateTime, KOTHArena> upcomingDates) {
		this.upcomingDates = upcomingDates;
	}

	public List<KOTHArena> getKOTHS() {
		return koths;
	}

	public void addKOTHPlayer(Player player) {
		if (!kothPlayers.containsKey(player.getUniqueId())) {
			kothPlayers.put(player.getUniqueId(), new TempKOTHPlayer(player));
		}
	}

	public void removeKOTHPlayer(Player player) {
		kothPlayers.remove(player.getUniqueId());
	}

	public KOTHPlayer getKOTHPlayer(Player player) {
		if (!kothPlayers.containsKey(player.getUniqueId())) {
			kothPlayers.put(player.getUniqueId(), new TempKOTHPlayer(player));
		}
		return kothPlayers.get(player.getUniqueId());
	}

	public void addKOTH(KOTHArena koth) {
		koths.add(koth);
	}

	public void removeKOTH(String name) {
		List<KOTHArena> toRemove = getKOTHS().stream().filter(koth -> koth.getName(false).equalsIgnoreCase(name)).collect(Collectors.toList());
		koths.removeAll(toRemove);
	}

	public void setLastCappedTeam(String lastCappedTeam) {
		this.lastCappedTeam = lastCappedTeam;
	}

	public void setLastCappedPlayer(String lastCappedPlayer) {
		this.lastCappedPlayer = lastCappedPlayer;
	}

	public String getLastCappedPlayer() {
		if (lastCappedPlayer == null) {
			return LangUtil.NO_CAPPER.toString();
		}
		return lastCappedPlayer;
	}

	public String getLastCappedTeam() {
		if (lastCappedTeam == null) {
			return LangUtil.NO_TEAM.toString();
		}
		return lastCappedTeam;
	}
}