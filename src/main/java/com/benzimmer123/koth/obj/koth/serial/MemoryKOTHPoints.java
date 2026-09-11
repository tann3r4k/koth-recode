package com.benzimmer123.koth.obj.koth.serial;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.benzimmer123.koth.api.objects.KOTHPoints;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.google.common.collect.Maps;

public class MemoryKOTHPoints implements KOTHPoints, Serializable {

	private static final long serialVersionUID = 1951062478459669533L;
	private transient Map<String, Double> pointsObtained;
	private transient Map<String, UUID> playersStored;

	public Map<String, Double> getPointsMap() {
		if (pointsObtained == null)
			return Maps.newHashMap();
		return pointsObtained;
	}

	public String getPosition(int pos) {
		if (pointsObtained == null || playersStored == null) {
			pointsObtained = Maps.newHashMap();
			playersStored = Maps.newHashMap();
		}
		if (pointsObtained.size() >= pos) {
			String[] playerList = (String[]) pointsObtained.keySet().toArray(new String[pointsObtained.keySet().size()]);
			return playerList[pos - 1];
		}
		return null;
	}

	public String getAmountPosition(int pos) {
		if (pointsObtained == null || playersStored == null) {
			pointsObtained = Maps.newHashMap();
			playersStored = Maps.newHashMap();
		}
		if (pointsObtained.size() >= pos) {
			return "" + pointsObtained.values().toArray()[pos - 1];
		}
		return null;
	}

	public void addPoints(String name, int amount, UUID uuid) {
		if (pointsObtained == null || playersStored == null) {
			pointsObtained = Maps.newHashMap();
			playersStored = Maps.newHashMap();
		}
		pointsObtained.put(name, getPoints(name) + (double) amount);
		playersStored.put(name, uuid);
		sortPoints();
		KOTHHandler.getInstance().addTotalPoints(name, amount);
	}

	public void addPoints(String name, double amount, UUID uuid) {
		if (pointsObtained == null || playersStored == null) {
			pointsObtained = Maps.newHashMap();
			playersStored = Maps.newHashMap();
		}
		pointsObtained.put(name, getPointsDouble(name) + amount);
		playersStored.put(name, uuid);
		sortPoints();
		KOTHHandler.getInstance().addTotalPoints(name, amount);
	}

	public int getPoints(String name) {
		if (pointsObtained == null || playersStored == null) {
			pointsObtained = Maps.newHashMap();
			playersStored = Maps.newHashMap();
		}
		if (pointsObtained.containsKey(name)) {
			int value = (int) Math.round(pointsObtained.get(name));
			return value;
		}
		return 0;
	}

	public double getPointsDouble(String name) {
		if (pointsObtained == null || playersStored == null) {
			pointsObtained = Maps.newHashMap();
			playersStored = Maps.newHashMap();
		}
		if (pointsObtained.containsKey(name)) {
			return pointsObtained.get(name);
		}
		return 0;
	}

	public UUID getUUIDFromName(String name) {
		if (pointsObtained == null || playersStored == null) {
			pointsObtained = Maps.newHashMap();
			playersStored = Maps.newHashMap();
		}
		if (playersStored.containsKey(name)) {
			return playersStored.get(name);
		}
		return null;
	}

	public void sortPoints() {
		if (pointsObtained == null)
			return;
		LinkedHashMap<String, Double> treeMap = new LinkedHashMap<String, Double>(pointsObtained);
		treeMap = treeMap.entrySet().stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).collect(Collectors.toMap(Map.Entry::getKey,
				Map.Entry::getValue, (oldV, newV) -> oldV, LinkedHashMap::new));
		pointsObtained = treeMap;
	}
}
