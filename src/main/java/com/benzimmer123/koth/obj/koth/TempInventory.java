package com.benzimmer123.koth.obj.koth;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TempInventory {

	private ItemStack[] inventories;
	private ItemStack[] armors;

	public TempInventory(Player player) {
		armors = player.getInventory().getArmorContents();
		inventories = player.getInventory().getContents();
	}

	public ItemStack[] getInventory() {
		return inventories;
	}

	public ItemStack[] getArmor() {
		return armors;
	}
}