package com.benzimmer123.koth.hooks.teams;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;
import com.songoda.skyblock.api.SkyBlockAPI;
import com.songoda.skyblock.api.island.IslandManager;

public class FabledSkyblock implements TeamHook {

	@Override
	public String getTeamName(Player p) {
		if (IslandManager.hasIsland(p)) {
			if (Bukkit.getPlayer(com.songoda.skyblock.api.SkyBlockAPI.getIslandManager().getIsland(p).getOwnerUUID()) != null) {
				return Bukkit.getPlayer(com.songoda.skyblock.api.SkyBlockAPI.getIslandManager().getIsland(p).getOwnerUUID()).getName();
			}
		}
		return LangUtil.NO_TEAM.toString();
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> players = Lists.newArrayList();
		if (IslandManager.hasIsland(p)) {
			for (UUID uuid : com.songoda.skyblock.api.SkyBlockAPI.getIslandManager().getMembersOnline(
					com.songoda.skyblock.api.SkyBlockAPI.getIslandManager().getIsland(p))) {
				if (Bukkit.getPlayer(uuid) != null) {
					players.add(Bukkit.getPlayer(uuid));
				}
			}
		}
		return players;
	}

	@Override
	public String getTeamLeader(Player p) {
		if (IslandManager.hasIsland(p)) {
			if (Bukkit.getPlayer(com.songoda.skyblock.api.SkyBlockAPI.getIslandManager().getIsland(p).getOwnerUUID()) != null) {
				return Bukkit.getPlayer(com.songoda.skyblock.api.SkyBlockAPI.getIslandManager().getIsland(p).getOwnerUUID()).getName();
			}
		}
		return null;
	}

	@Override
	public boolean hasTeam(Player p) {
		if (IslandManager.hasIsland(p)) {
			return true;
		}
		return false;
	}

	public void toggleScoreboard(Player p) {
		SkyBlockAPI.getImplementation().getScoreboardManager().addDisabledPlayer(p);
	}
	
	public void removeScoreboard(Player p) {
		SkyBlockAPI.getImplementation().getScoreboardManager().removeDisabledPlayer(p);
	}
	
	@Override
	public String getTeamID(Player p) {
		if (hasTeam(p)) {
			return SkyBlockAPI.getIslandManager().getIsland(p).getIslandUUID().toString();
		}
		return null;
	}
	
	@Override
	public boolean exists(String name) {
		return true;
	}
	
	@Override
	public String getAPIName() {
		return "FabledSkyblock";
	}
}
