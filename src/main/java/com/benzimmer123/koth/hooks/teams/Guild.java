package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

import me.glaremasters.guilds.Guilds;

public class Guild implements TeamHook{

	@Override
	public boolean hasTeam(Player p){
		if (Guilds.getApi().getGuild(p) != null) {
			return true;
		}
		return false;
	}
	
	@Override
	public String getTeamLeader(Player p){
		if (Guilds.getApi().getGuild(p) != null) {
			if (Guilds.getApi().getGuild(p).getGuildMaster() != null) {
				return Guilds.getApi().getGuild(p).getGuildMaster().getName();
			}
		}
		return null;
	}
	
	@Override
	public String getTeamName(Player p){
		if (Guilds.getApi().getGuild(p) != null) {
			return Guilds.getApi().getGuild(p).getName();
		}
		return LangUtil.NO_TEAM.toString();
	}
	
	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		if (Guilds.getApi().getGuild(p) != null) {
			teamPlayers.addAll(Guilds.getApi().getGuild(p).getOnlineAsPlayers());
		}
		return teamPlayers;
	}
	
	@Override
	public String getTeamID(Player p) {
		if (hasTeam(p)) {
			return Guilds.getApi().getGuild(p).getId().toString();
		}
		return null;
	}
	
	@Override
	public boolean exists(String name) {
		return true;
	}
	
	@Override
	public String getAPIName() {
		return "Guilds";
	}
	
}
