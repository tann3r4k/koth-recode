package com.benzimmer123.koth.util;

import java.util.List;
import java.util.Random;

import com.benzimmer123.koth.api.objects.KOTHLootItem;
import com.google.common.collect.Lists;

public class WeightedRandomUtil<T> {

	private class Entry {
		double accumulatedWeight;
		KOTHLootItem lootItem;
	}

	private List<Entry> entries = Lists.newArrayList();
	private double accumulatedWeight;
	private Random rand = new Random();

	public void addEntry(KOTHLootItem loot, double weight) {
		if (weight == 0)
			weight = 100;
		accumulatedWeight += weight;
		Entry e = new Entry();
		e.lootItem = loot;
		e.accumulatedWeight = accumulatedWeight;
		entries.add(e);
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void removeEntry(List<KOTHLootItem> currentEntries) {
		entries.clear();
		accumulatedWeight = 0;

		for (KOTHLootItem loot : currentEntries) {
			addEntry(loot, loot.getPercent());
		}
	}

	public KOTHLootItem getRandom() {
		double r = rand.nextDouble() * accumulatedWeight;

		for (Entry entry : entries) {
			if (entry.accumulatedWeight >= r) {
				return entry.lootItem;
			}
		}
		
		return null;
	}
}