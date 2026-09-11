package com.benzimmer123.koth.hooks.teams;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

import dev.drawethree.ultraprisoncore.gangs.UltraPrisonGangs;

public class UltraPrisonCore implements TeamHook {

	@Override
	public boolean hasTeam(Player p) {
		if (dev.drawethree.ultraprisoncore.UltraPrisonCore.getInstance().getGangs().getApi().getPlayerGang(p) != null
				&& dev.drawethree.ultraprisoncore.UltraPrisonCore.getInstance().getGangs().getApi().getPlayerGang(p).isPresent()) {
			return true;
		}
		return false;
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			UUID uuid = dev.drawethree.ultraprisoncore.UltraPrisonCore.getInstance().getGangs().getApi().getPlayerGang(p).get().getGangOwner();
			if (Bukkit.getPlayer(uuid) != null) {
				return Bukkit.getPlayer(uuid).getName();
			}
		}
		return p.getName();
	}

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			return dev.drawethree.ultraprisoncore.UltraPrisonCore.getInstance().getGangs().getApi().getPlayerGang(p).get().getName();
		}
		return LangUtil.NO_TEAM.toString().replaceAll("%player%", p.getName());
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		if (hasTeam(p)) {
			teamPlayers.addAll(dev.drawethree.ultraprisoncore.UltraPrisonCore.getInstance().getGangs().getApi().getPlayerGang(p).get()
					.getOnlinePlayers());
		}
		return teamPlayers;
	}

	@Override
	public String getTeamID(Player p) {
		return null;
	}

	@Override
	public String getAPIName() {
		return "UltraPrisonCore";
	}

	@Override
	public boolean exists(String teamName) {
		return UltraPrisonGangs.getInstance().getApi().getByName(teamName).isPresent();
	}

}
