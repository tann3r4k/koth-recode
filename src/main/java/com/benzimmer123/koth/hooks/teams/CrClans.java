package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

import net.cosmogrp.crclans.ClansAPI;
import net.cosmogrp.crclans.clan.member.ClanMemberData;
import net.cosmogrp.crclans.clan.service.ClanService;
import net.cosmogrp.crclans.user.User;

public class CrClans implements TeamHook {

	private final ClansAPI clansAPI = (ClansAPI) Bukkit.getPluginManager().getPlugin("CrClans");
	private final ClanService<ClanMemberData> memberService = clansAPI.getService(ClanMemberData.class);

	@Override
	public boolean hasTeam(Player p) {
		User user = clansAPI.getUserService().getUser(p.getUniqueId());
		if (user == null)
			return false;
		return user.hasClan();
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			User user = clansAPI.getUserService().getUser(p.getUniqueId());
			if (user == null)
				return null;
			if (memberService == null)
				return null;
			if (user.getClanTag() == null)
				return null;
			ClanMemberData memberData = memberService.getData(user.getClanTag());
			if (memberData == null)
				return null;
			return memberData.getOwner().getPlayerName();
		}
		return null;
	}

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			User user = clansAPI.getUserService().getUser(p.getUniqueId());
			if (user != null) {
				return user.getClanTag();
			}
		}
		return LangUtil.NO_TEAM.toString().replaceAll("%player%", p.getName());
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		if (hasTeam(p)) {
			User user = clansAPI.getUserService().getUser(p.getUniqueId());
			if (user == null)
				return teamPlayers;
			if (memberService == null)
				return teamPlayers;
			if (user.getClanTag() == null)
				return teamPlayers;
			ClanMemberData memberData = memberService.getData(user.getClanTag());
			if (memberData == null)
				return teamPlayers;
			memberData.getOnlineIdMembers().stream().forEach(uuid -> {
				Player player = Bukkit.getPlayer(uuid);
				if (player != null) {
					teamPlayers.add(player);
				}
			});
		}
		return teamPlayers;
	}

	@Override
	public String getTeamID(Player p) {
		return null;
	}

	@Override
	public String getAPIName() {
		return "CrClans";
	}

	@Override
	public boolean exists(String teamID) {
		return true;
	}

}
