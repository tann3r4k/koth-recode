package com.benzimmer123.koth.hooks.teams;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;

import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.user.User;

public class Bento implements TeamHook {

	private World getWorld() {
		Set<World> worlds = BentoBox.getInstance().getIWM().getWorlds();
		Iterator<World> iter = worlds.iterator();
		if (iter.hasNext()) {
			World first = iter.next();
			return first;
		}
		return null;
	}

	public boolean hasTeam(Player p) {
		if (User.getInstance(p) != null) {
			User user = User.getInstance(p);
			World world = getWorld();
			if (BentoBox.getInstance().getIslands().hasIsland(world, user) && Bukkit.getPlayer(BentoBox.getInstance().getIslands().getIsland(world,
					user).getOwner()) != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			User user = User.getInstance(p);
			if (BentoBox.getInstance().getIslands().getIsland(getWorld(), user).getName() != null) {
				return BentoBox.getInstance().getIslands().getIsland(getWorld(), user).getName();
			} else {
				return getTeamLeader(p);
			}
		}
		return LangUtil.NO_TEAM.toString();
	}

	@Override
	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			User user = User.getInstance(p);
			return Bukkit.getPlayer(BentoBox.getInstance().getIslands().getIsland(getWorld(), user).getOwner()).getName();
		}
		return null;
	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();
		if (hasTeam(p)) {
			User user = User.getInstance(p);
			teamPlayers.addAll(BentoBox.getInstance().getIslands().getIsland(getWorld(), user).getPlayersOnIsland());
		}
		return teamPlayers;
	}
	
	@Override
	public boolean exists(String name) {
		return true;
	}

	@Override
	public String getAPIName() {
		return "BentoBox";
	}

	@Override
	public String getTeamID(Player p) {
		if (hasTeam(p)) {
			User user = User.getInstance(p);
			return BentoBox.getInstance().getIslands().getIsland(getWorld(), user).getUniqueId();
		}
		return null;
	}
}
