package com.benzimmer123.koth.obj.koth.serial;

import java.io.IOException;
import java.io.Serializable;

import org.bukkit.inventory.ItemStack;

import com.benzimmer123.koth.api.objects.KOTHLootItem;
import com.benzimmer123.koth.compatible.XMaterial;
import com.benzimmer123.koth.util.ItemUtil;

public class MemoryKOTHLootItem implements KOTHLootItem, Serializable {

	private static final long serialVersionUID = 5374073364224483736L;
	private String itemStack;
	private int percent;
	private int slot;

	public MemoryKOTHLootItem(ItemStack item, int percent, int slot) {
		this.itemStack = ItemUtil.itemStackToBase64(item);
		this.percent = percent;
		this.slot = slot;
	}

	public int getPercent() {
		return percent;
	}

	public String getString() {
		return itemStack;
	}

	public int getSlot() {
		if (slot == -1)
			slot = 0;

		return slot;
	}

	public ItemStack toItemStack() {
		try {
			return ItemUtil.fromBase64(getString());
		} catch (IOException e) {
		}
		return new ItemStack(XMaterial.AIR.parseMaterial());
	}

}