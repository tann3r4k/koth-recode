package com.benzimmer123.koth.obj.koth.serial;

import java.io.Serializable;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import com.benzimmer123.koth.api.enums.LootType;
import com.benzimmer123.koth.api.objects.KOTHLoot;
import com.benzimmer123.koth.api.objects.KOTHLootItem;
import com.benzimmer123.koth.api.objects.KOTHLootLocation;
import com.google.common.collect.Lists;

public class MemoryKOTHLoot implements KOTHLoot, Serializable {

	private static final long serialVersionUID = 460827852198259357L;
	private List<MemoryKOTHLootItem> lootItems;
	private List<MemoryKOTHLootLocation> lootLocations;
	private LootType lootType;

	public MemoryKOTHLoot() {
		lootItems = Lists.newArrayList();
		lootLocations = Lists.newArrayList();
		lootType = LootType.INVENTORY;
	}

	public List<Location> getLootLocations() {
		List<Location> locations = Lists.newArrayList();
		for (KOTHLootLocation lootLocation : lootLocations) {
			locations.add(lootLocation.toLocation());
		}
		return locations;
	}

	public List<ItemStack> getLootItemStacks() {
		List<ItemStack> items = Lists.newArrayList();
		for (KOTHLootItem loot : lootItems) {
			items.add(loot.toItemStack());
		}
		return items;
	}

	public void clearLootItems() {
		lootItems.clear();
	}

	public List<KOTHLootItem> getLootItems(boolean allowDuplicates) {
		if (!allowDuplicates) {
			List<KOTHLootItem> clonedLootItems = Lists.newArrayList(lootItems);
			for (KOTHLootItem lootItem : lootItems) {
				lootItems.stream().filter(x -> lootItem.toItemStack().isSimilar(x.toItemStack())).forEach(x -> clonedLootItems.remove(x));
				clonedLootItems.add(lootItem);
			}
			return clonedLootItems;
		}
		List<KOTHLootItem> extendedLootItems = Lists.newArrayList(lootItems);
		return extendedLootItems;
	}

	public void setLootType(LootType lootType) {
		this.lootType = lootType;
	}

	public LootType getLootType() {
		return lootType;
	}

	public void clearLootLocations() {
		lootLocations.clear();
	}

	public void addLootItem(ItemStack loot, int percent, int slot) {
		lootItems.add(new MemoryKOTHLootItem(loot, percent, slot));
	}

	public void addLootLocation(Location loc) {
		lootLocations.add(new MemoryKOTHLootLocation(loc));
	}

	public void removeLootLocation(Location loc) {
		MemoryKOTHLootLocation toRemove = null;

		for (MemoryKOTHLootLocation lootLoc : lootLocations) {
			if (lootLoc.toLocation().equals(loc)) {
				toRemove = lootLoc;
			}
		}

		lootLocations.remove(toRemove);
	}
}
