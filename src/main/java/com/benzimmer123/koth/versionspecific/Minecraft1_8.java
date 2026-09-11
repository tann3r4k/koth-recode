package com.benzimmer123.koth.versionspecific;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Minecraft1_8 {

	public static boolean isGamemodeSpectator(Player player) {
		return player.getGameMode() == GameMode.SPECTATOR;
	}

	public static ItemStack addGlow(ItemStack item) {
		if (item == null) {
			return null;
		}
		ItemMeta meta = item.getItemMeta();
		if (meta != null) {
			meta.setEnchantmentGlintOverride(true);
			item.setItemMeta(meta);
		}
		return item;
	}
}
