package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

import me.angeschossen.lands.api.integration.LandsIntegration;
import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.player.LandPlayer;

public class Lands implements TeamHook {

	private final LandsIntegration landsIntegration = new LandsIntegration(KOTH.getInstance());

	@Override
	public boolean hasTeam(Player p) {
		LandPlayer landPlayer = landsIntegration.getLandPlayer(p.getUniqueId());
		return landPlayer.ownsLand();
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			LandPlayer landPlayer = landsIntegration.getLandPlayer(p.getUniqueId());
			Land land = landPlayer.getEditLand();
			if (land != null) {
				if (Bukkit.getPlayer(land.getOwnerUID()) != null)
					return Bukkit.getPlayer(land.getOwnerUID()).getName();
				else if (Bukkit.getOfflinePlayer(land.getOwnerUID()) != null)
					return Bukkit.getOfflinePlayer(land.getOwnerUID()).getName();
			}
		}
		return null;
	}

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			LandPlayer landPlayer = landsIntegration.getLandPlayer(p.getUniqueId());
			if (landPlayer.getEditLand() != null) {
				return landPlayer.getEditLand().getName();
			}
		}
		return LangUtil.NO_TEAM.toString();
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		if (hasTeam(p)) {
			LandPlayer landPlayer = landsIntegration.getLandPlayer(p.getUniqueId());
			if (landPlayer.getEditLand() != null) {
				teamPlayers.addAll(landPlayer.getEditLand().getOnlinePlayers());
			}
		}
		return teamPlayers;
	}

	@Override
	public String getTeamID(Player p) {
		if (hasTeam(p)) {
			LandPlayer landPlayer = landsIntegration.getLandPlayer(p.getUniqueId());
			return landPlayer.getEditLand().getId() + "";
		}
		return null;
	}

	@Override
	public boolean exists(String name) {
		return true;
	}

	@Override
	public String getAPIName() {
		return "Lands";
	}

}
