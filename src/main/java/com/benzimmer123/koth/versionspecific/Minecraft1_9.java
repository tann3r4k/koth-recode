package com.benzimmer123.koth.versionspecific;

import org.bukkit.inventory.EquipmentSlot;

public class Minecraft1_9 {

	public static boolean isUsingOffHand(EquipmentSlot equip) {
		return equip == EquipmentSlot.OFF_HAND;
	}

}