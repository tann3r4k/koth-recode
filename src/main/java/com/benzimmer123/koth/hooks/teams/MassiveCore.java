package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;
import com.massivecraft.factions.entity.MPlayer;

public class MassiveCore implements TeamHook {

	@Override
	public boolean hasTeam(Player p) {
		if (MPlayer.get(p) != null && MPlayer.get(p).getFaction().isNormal()) {
			return true;
		}
		return false;
	}

	@Override
	public String getTeamLeader(Player p) {
		if (MPlayer.get(p) != null && MPlayer.get(p).getFaction().isNormal()) {
			if (MPlayer.get(p).getFaction().getLeader().getPlayer() != null) {
				return MPlayer.get(p).getFaction().getLeader().getPlayer().getName();
			}
		}
		return null;
	}

	@Override
	public String getTeamName(Player p) {
		if (MPlayer.get(p) != null && MPlayer.get(p).getFaction().isNormal()) {
			return MPlayer.get(p).getFaction().getName();
		}
		return LangUtil.NO_TEAM.toString();
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		if (MPlayer.get(p) != null && MPlayer.get(p).getFaction().isNormal()) {
			for (MPlayer mp : MPlayer.get(p).getFaction().getMPlayersWhereOnline(true)) {
				teamPlayers.add(mp.getPlayer());
			}
		}
		return teamPlayers;
	}
	
	@Override
	public String getTeamID(Player p) {
		if (hasTeam(p)) {
			return MPlayer.get(p).getFaction().getId();
		}
		return null;
	}
	
	@Override
	public boolean exists(String name) {
		return true;
	}
	
	@Override
	public String getAPIName() {
		return "MassiveCore";
	}

}
