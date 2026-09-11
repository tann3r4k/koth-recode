package com.benzimmer123.koth.compatible;

import java.util.Optional;

import org.bukkit.Material;

/**
 * Tiny Paper 26+ stand-in for the old CryptoMorin XMaterial enum.
 * That enum died on Minecraft 26.2 because it parsed versions as "1.x".
 */
public final class XMaterial {

	public static final XMaterial AIR = of(Material.AIR);
	public static final XMaterial ARROW = of(Material.ARROW);
	public static final XMaterial CHEST = of(Material.CHEST);
	public static final XMaterial EMERALD_BLOCK = of(Material.EMERALD_BLOCK);
	public static final XMaterial PAPER = of(Material.PAPER);
	public static final XMaterial REDSTONE_BLOCK = of(Material.REDSTONE_BLOCK);
	public static final XMaterial STICK = of(Material.STICK);

	private final Material material;

	private XMaterial(Material material) {
		this.material = material;
	}

	public Material parseMaterial() {
		return material;
	}

	public static Optional<XMaterial> matchXMaterial(String name) {
		if (name == null || name.isEmpty()) {
			return Optional.empty();
		}
		String key = name.trim();
		int colon = key.indexOf(':');
		if (colon >= 0) {
			key = key.substring(0, colon);
		}
		Material matched = Material.matchMaterial(key);
		if (matched == null) {
			matched = Material.matchMaterial(key, true);
		}
		return matched == null ? Optional.empty() : Optional.of(new XMaterial(matched));
	}

	private static XMaterial of(Material material) {
		return new XMaterial(material);
	}
}
