package com.benzimmer123.koth.hooks.teams;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.hooks.face.TeamHook;
import com.benzimmer123.koth.util.LangUtil;
import com.google.common.collect.Lists;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;

public class AdvancedTowny implements TeamHook {

	private boolean useNations = KOTH.getInstance().getConfig().getBoolean("TOWNY_USE_NATIONS");

	@Override
	public boolean hasTeam(Player p) {
		Resident resident = TownyUniverse.getInstance().getResident(p.getUniqueId());

		if (useNations) {
			try {
				if (resident.hasTown() && resident.getTown().hasNation()) {
					return true;
				}
			} catch (NotRegisteredException e) {
				e.printStackTrace();
			}
		} else {
			if (resident.hasTown()) {
				return true;
			}
		}
		return false;
	}

	public String getTeamLeader(Player p) {
		if (hasTeam(p)) {
			if (useNations) {
				Nation nation = TownyUniverse.getInstance().getNation(p.getUniqueId());
				return nation.getCapital().getMayor().getName();
			} else {
				Resident resident = TownyUniverse.getInstance().getResident(p.getUniqueId());
				try {
					return resident.getTown().getMayor().getName();
				} catch (NotRegisteredException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public String getTeamName(Player p) {
		if (hasTeam(p)) {
			if (useNations) {
				Nation nation = TownyUniverse.getInstance().getNation(p.getUniqueId());
				return nation.getName();
			} else {
				Resident resident = TownyUniverse.getInstance().getResident(p.getUniqueId());
				try {
					return resident.getTown().getName();
				} catch (NotRegisteredException e) {
					e.printStackTrace();
				}
			}
		}
		return LangUtil.NO_TEAM.toString();

	}

	@Override
	public List<Player> getTeamPlayers(Player p) {
		List<Player> teamPlayers = Lists.newArrayList();

		if (hasTeam(p)) {
			try {
				if (useNations) {
					Resident resident = TownyUniverse.getInstance().getResident(p.getUniqueId());
					Town playerTown = resident.getTown();
					Nation nation = playerTown.getNation();
					for (Town town : nation.getTowns()) {
						for (Resident res : town.getResidents()) {
							if (Bukkit.getPlayer(res.getName()) != null) {
								teamPlayers.add(Bukkit.getPlayer(res.getName()));
							}
						}
					}
				} else {
					Resident resident = TownyUniverse.getInstance().getResident(p.getUniqueId());
					Town town = resident.getTown();
					for (Resident res : town.getResidents()) {
						if (Bukkit.getPlayer(res.getName()) != null) {
							teamPlayers.add(Bukkit.getPlayer(res.getName()));
						}
					}
				}
			} catch (NotRegisteredException e) {
				e.printStackTrace();
			}
		}

		return teamPlayers;
	}

	@Override
	public boolean exists(String name) {
		if (useNations) {
			Nation nation = TownyUniverse.getInstance().getNation(name);
			if (nation != null)
				return true;
		} else {
			Town town = TownyUniverse.getInstance().getTown(name);
			if (town != null)
				return true;
		}
		return false;
	}

	@Override
	public String getAPIName() {
		return "AdvancedTowny";
	}

	@Override
	public String getTeamID(Player p) {
		return null;
	}
}
