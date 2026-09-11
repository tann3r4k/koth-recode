package com.benzimmer123.koth.handlers;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.KOTHRegion;
import com.benzimmer123.koth.obj.koth.serial.MemoryCuboid;
import com.benzimmer123.koth.obj.koth.serial.MemoryKOTHRegion;
import com.benzimmer123.koth.storage.GsonStorage;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class RegionHandler {

	private final static RegionHandler INSTANCE;
	private Set<KOTHRegion> regions;
	private Map<String, String> validScoreboardChunks;

	static {
		INSTANCE = new RegionHandler();
	}

	private RegionHandler() {
		regions = Sets.newHashSet();
		validScoreboardChunks = Maps.newHashMap();

		for (File file : new File(KOTH.getInstance().getDataFolder() + "/regions").listFiles()) {
			KOTHRegion region = (KOTHRegion) GsonStorage.deserialize(MemoryKOTHRegion.class, file.getPath(), "region");
			if (region != null) {
				addRegion(region);
			}
		}
	}

	// Not used in constructor to ensure outposts get loaded first
	// This is causes a lot cpu usage so only needs to be run once
	public void loadValidScoreboardChunks() {
		validScoreboardChunks.clear();

		if (!KOTH.getInstance().getConfig().getBoolean("SCOREBOARD.SCOREBOARD_RADIUS.ENABLED"))
			return;

		int radius = KOTH.getInstance().getConfig().getInt("SCOREBOARD.SCOREBOARD_RADIUS.DISTANCE");

		for (KOTHArena koth : KOTHHandler.getInstance().getKOTHS()) {
			Location kothLoc1 = koth.getKOTHLocation().getLocation1();
			Location kothLoc2 = koth.getKOTHLocation().getLocation2();
			MemoryCuboid cuboid = new MemoryCuboid(kothLoc1, kothLoc2);
			Location center = cuboid.getCenter();

			int chunkRadius = radius / 16;

			if (chunkRadius <= 0) {
				chunkRadius = 1;
			}

			Collection<Chunk> validChunks = getChunks(center.getChunk(), chunkRadius);

			for (Chunk chunk : validChunks) {
				validScoreboardChunks.put(chunk.getX() + "," + chunk.getZ(), chunk.getWorld().getName());
			}
		}
	}

	public static Collection<Chunk> getChunks(Chunk origin, int radius) {
		World world = origin.getWorld();

		int length = (radius * 2) + 1;
		Set<Chunk> chunks = new HashSet<>(length * length);

		int cX = origin.getX();
		int cZ = origin.getZ();

		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				chunks.add(world.getChunkAt(cX + x, cZ + z));
			}
		}
		return chunks;
	}

	public Map<String, String> getValidScoreboardChunks() {
		return validScoreboardChunks;
	}

	public Set<KOTHRegion> getRegions() {
		return regions;
	}

	public void removeRegion(String name) {
		List<KOTHRegion> toRemove = getRegions().stream().filter(region -> region.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
		regions.removeAll(toRemove);
	}

	public void addRegion(KOTHRegion region) {
		regions.add(region);
	}

	public static RegionHandler getInstance() {
		return INSTANCE;
	}
}
