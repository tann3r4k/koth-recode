package com.benzimmer123.koth.util;

import org.bukkit.Bukkit;

/**
 * Paper 26+ has no versioned NMS package name, so the old v1_x_Ry lookup
 * always returned null and treated the server as pre-1.8.
 */
public enum ServerVersionUtil {
	v1_7_R1, v1_7_R2, v1_7_R3, v1_8_R1, v1_8_R2, v1_8_R3, v1_9_R1, v1_9_R2, v1_10_R1, v1_11_R1, v1_12_R1, v1_13_R1, v1_13_R2, v1_14_R1, v1_15_R1,
	v1_16_R1, v1_16_R2, v1_16_R3, v1_17_R1, v1_17_R2, v1_17_R3, v1_18_R1, v1_18_R2, v1_18_R3, PAPER_26;

	public static ServerVersionUtil getServerVersion() {
		return PAPER_26;
	}

	public static boolean isAboveVersion(ServerVersionUtil version) {
		return true;
	}

	@Override
	public String toString() {
		try {
			return Bukkit.getMinecraftVersion();
		} catch (Throwable ignored) {
			return name();
		}
	}
}
