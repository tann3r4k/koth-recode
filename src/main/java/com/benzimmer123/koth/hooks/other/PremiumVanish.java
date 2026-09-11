package com.benzimmer123.koth.hooks.other;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.OtherHook;

import de.myzelyam.api.vanish.VanishAPI;

public class PremiumVanish implements OtherHook {

	@Override
	public void setup() {
	}
	
	@Override
	public String getAPIName() {
		return "PremiumVanish";
	}

	public static boolean isVanished(Player player) {
		return VanishAPI.isInvisible(player);
	}

}
