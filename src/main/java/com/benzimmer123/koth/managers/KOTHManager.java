package com.benzimmer123.koth.managers;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.hooks.other.PremiumVanish;
import com.benzimmer123.koth.obj.koth.serial.MemoryCuboid;
import com.benzimmer123.koth.util.BroadcastUtil;
import com.benzimmer123.koth.util.LangUtil;
import com.benzimmer123.koth.util.ServerVersionUtil;
import com.benzimmer123.koth.versionspecific.Minecraft1_8;
import com.google.common.collect.Lists;

public class KOTHManager {

	public boolean isPointsEnabled() {
		return KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.ENABLED");
	}

	public boolean isUsingTeams() {
		return KOTH.getInstance().getConfig().getString("KOTH_POINTS_SYSTEM.STORED_TYPE").equalsIgnoreCase("TEAM");
	}

	public List<Player> getValidCapturers(KOTHArena koth) {
		List<Player> validCapture = Lists.newArrayList();
		boolean permission = KOTH.getInstance().getConfig().getBoolean("PERMISSION_FOR_CAPTURE");

		for (Player online : Bukkit.getOnlinePlayers()) {
			if (koth.contains(online.getLocation()) && !online.isDead()) {
				if (ServerVersionUtil.isAboveVersion(ServerVersionUtil.v1_8_R1) && Minecraft1_8.isGamemodeSpectator(online))
					continue;
				if (KOTH.getInstance().getAPIManager().isHooked("PremiumVanish") && PremiumVanish.isVanished(online))
					continue;
				if (!permission || online.hasPermission("KOTH.CAPTURE")) {
					if (KOTH.getInstance().getConfig().getBoolean("ALLOW_NO_TEAM_CAPTURE") || KOTH.getInstance().getTeamManager().hasTeam(online)) {
						KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(online);
						if (!kothPlayer.inBypassMode()) {
							validCapture.add(online);
						}
					}
				}
			}
		}

		return validCapture;
	}

	private boolean allowFullTeamCapture() {
		if (KOTH.getInstance().getTeamManager().getAPI() != null && "FactionsBridge".equals(KOTH.getInstance().getTeamManager().getAPI()
				.getAPIName()))
			return true;
		return KOTH.getInstance().getConfig().getBoolean("ALLOW_FULL_TEAM_CAPTURE", true);
	}

	private Player checkTeamLocations(KOTHArena koth) {
		if (!allowFullTeamCapture() || koth.getCapper() == null)
			return null;

		String teamId = KOTH.getInstance().getTeamManager().getTeamID(koth.getCapper());
		if (teamId == null)
			return null;

		for (Player player : getValidCapturers(koth)) {
			if (teamId.equals(KOTH.getInstance().getTeamManager().getTeamID(player))) {
				return player;
			}
		}

		return null;
	}

	public boolean checkCapture(KOTHArena koth) {
		if (checkOtherTeams(new MemoryCuboid(koth.getKOTHLocation().getLocation1(), koth.getKOTHLocation().getLocation2()), koth.getPlayerCapper()))
			return true;

		KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(koth.getPlayerCapper());

		if (kothPlayer.hasKOTHTimeout() && !kothPlayer.hasKOTHTimeoutExpired()) {
			return true;
		}

		if (!isPointsEnabled()) {
			if (koth.getKOTHDetails().getCaptureTime() >= koth.getKOTHDetails().getRequiredTime()) {
				koth.successful();
			}
		}

		return false;
	}

	public boolean getTopPointsWinner(KOTHArena koth) {
		koth.getKOTHPoints().sortPoints();

		if (!isUsingTeams()) {
			if (koth.getKOTHPoints().getPosition(1) == null || Bukkit.getPlayer(koth.getKOTHPoints().getPosition(1)) == null) {
				return false;
			}
		}

		if (koth.getKOTHPoints().getPosition(1) != null) {
			koth.setCapper(Bukkit.getPlayer(koth.getKOTHPoints().getUUIDFromName(koth.getKOTHPoints().getPosition(1))), false);
			koth.successful();
			return true;
		}

		return false;
	}

	public void checkMaxPointsReached(KOTHArena koth) {
		if (koth.getKOTHPoints().getPosition(1) == null)
			return;

		if (!isUsingTeams()) {
			if (Bukkit.getPlayer(koth.getKOTHPoints().getPosition(1)) == null) {
				return;
			}
		}

		if (koth.getKOTHPoints().getPointsDouble(koth.getKOTHPoints().getPosition(1)) >= koth.getKOTHDetails().getMaxPoints()) {
			koth.setCapper(Bukkit.getPlayer(koth.getKOTHPoints().getUUIDFromName(koth.getKOTHPoints().getPosition(1))), false);
			koth.successful();
			return;
		}
	}

	public void addCaptureValue(KOTHArena koth) {
		if (koth.getPlayerCapper() == null)
			return;

		KOTHPlayer player = KOTHHandler.getInstance().getKOTHPlayer(koth.getPlayerCapper());
		double playerSpeed = player.getCappingSpeedDouble();

		if (isPointsEnabled()) {
			if (isUsingTeams()) {
				String teamName = KOTH.getInstance().getTeamManager().getTeamName(koth.getPlayerCapper());
				koth.getKOTHPoints().addPoints(teamName, playerSpeed, koth.getPlayerCapper().getUniqueId());
			} else {
				koth.getKOTHPoints().addPoints(koth.getCapper(), playerSpeed, koth.getPlayerCapper().getUniqueId());
			}
		} else {
			koth.getKOTHDetails().setCaptureTime(koth.getKOTHDetails().getCaptureTime() + (int) playerSpeed);
			List<Integer> times = koth.getKOTHDetails().getBroadcastTimes();

			if (times != null) {
				if (times.contains(koth.getTimeRemainingAsInt())) {
					String team = KOTH.getInstance().getTeamManager().getTeamName(koth.getPlayerCapper());
					String storedName = isUsingTeams() ? (KOTH.getInstance().getTeamManager().hasTeam(koth.getPlayerCapper()) ? team
							: LangUtil.NO_TEAM.toString()) : koth.getCapper();

					BroadcastUtil.sendMessage(LangUtil.TIME_LEFT.toString().replaceAll("%points%", koth.getKOTHPoints().getPointsDouble(storedName)
							+ "").replaceAll("%time%", koth.getTimeRemainingAsString()).replaceAll("%koth%", koth.getName(true)).replaceAll(
									"%player%", koth.getCapper()).replaceAll("%team%", KOTH.getInstance().getTeamManager().getTeamName(koth
											.getPlayerCapper())));
				}
			}
		}
	}

	public void lostControl(KOTHArena koth) {
		Player teamPlayer = checkTeamLocations(koth);

		if (teamPlayer == null) {
			if (KOTH.getInstance().getConfig().getInt("DELAYED_PLAYER_LOSE_CAPTURE") != -1) {
				KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(koth.getPlayerCapper());
				if (!kothPlayer.hasKOTHTimeout()) {
					long cooldown = KOTH.getInstance().getConfig().getInt("DELAYED_PLAYER_LOSE_CAPTURE") * 1000;
					long endTime = System.currentTimeMillis() + cooldown;
					kothPlayer.setKOTHTimeout(endTime);
					koth.setTempDisabled(true);
					return;
				}
			}
			koth.setCapper(null, true);
		} else {
			koth.setCapper(teamPlayer.getName());
		}
	}

	public boolean checkDelayedLoseCapture(KOTHPlayer kothPlayer, KOTHArena koth) {
		if (kothPlayer.hasKOTHTimeout()) {
			if (kothPlayer.hasKOTHTimeoutExpired()) {
				Player player = Bukkit.getPlayer(kothPlayer.getName());
				kothPlayer.setKOTHTimeout(0);
				if (player == null || player.isDead() || !koth.contains(player.getLocation()) || kothPlayer.inBypassMode()) {
					koth.setCapper(null, true);
					return true;
				}
				koth.setTempDisabled(false);
			}
			return true;
		}
		return false;
	}

	public List<KOTHArena> getActiveKOTHs() {
		List<KOTHArena> koths = Lists.newArrayList();
		for (KOTHArena koth : KOTHHandler.getInstance().getKOTHS()) {
			if (koth.isActive()) {
				koths.add(koth);
			}
		}
		return koths;
	}

	public KOTHArena isCapping(Player p) {
		for (KOTHArena koth : KOTHHandler.getInstance().getKOTHS()) {
			if (koth.getCapper() != null) {
				if (koth.getCapper().equals(p.getName()))
					return koth;
			}
		}
		return null;
	}

	public boolean isActiveKOTH() {
		for (KOTHArena koth : KOTHHandler.getInstance().getKOTHS()) {
			if (koth.isActive())
				return true;
		}
		return false;
	}

	public KOTHArena getKOTHFromString(String name) {
		for (KOTHArena koth : KOTHHandler.getInstance().getKOTHS()) {
			if (koth.getName(false).equalsIgnoreCase(name)) {
				return koth;
			}
		}
		return null;
	}

	private boolean checkOtherTeams(MemoryCuboid cuboid, Player capper) {
		if (KOTH.getInstance().getConfig().getBoolean("PAUSE_OTHER_TEAMS")) {
			String cappingTeamName = KOTH.getInstance().getTeamManager().getTeamName(capper);
			for (Player player : Bukkit.getOnlinePlayers()) {
				String teamName = KOTH.getInstance().getTeamManager().getTeamName(player);
				if (KOTH.getInstance().getTeamManager().hasTeam(player) && !teamName.equalsIgnoreCase(cappingTeamName)) {
					if (cuboid.contains(player.getLocation()) && !player.isDead()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean callTask(KOTHArena koth, int requiredtime, Player p, boolean anonymous, int maxruntime, int maxpoints) {
		String world = koth.getKOTHLocation().getWorld();
		int x = koth.getKOTHLocation().getLocation1().getBlockX();
		int y = koth.getKOTHLocation().getLocation1().getBlockY();
		int z = koth.getKOTHLocation().getLocation1().getBlockZ();

		if (!KOTH.getInstance().getEventManager().callKothStartEvent(koth, Bukkit.getWorld(world), x, y, z))
			return false;

		if (koth.isActive()) {
			if (p != null)
				LangUtil.sendMessage(p, LangUtil.KOTH_CURRENTLY_ACTIVE.toString());
			return false;
		}

		int maxKoths = KOTH.getInstance().getConfig().getInt("MAX_KOTHS_ACTIVE");
		int amount = getActiveKOTHs().size();

		if (maxKoths != -1) {
			if (amount >= maxKoths) {
				if (p != null)
					LangUtil.sendMessage(p, LangUtil.MAX_KOTHS.toString().replaceAll("%amount%", maxKoths + ""));
				return false;
			}
		}

		if (!anonymous) {
			String name = p == null ? LangUtil.AUTOMATIC_HOSTNAME.toString() : p.getName();
			BroadcastUtil.sendMessage(LangUtil.KOTH_STARTING.toString().replaceAll("%koth%", koth.getName(true)).replaceAll("%x%", x + "").replaceAll(
					"%y%", y + "").replaceAll("%z%", z + "").replaceAll("%player%", name).replaceAll("%world%", world));
		}

		koth.start(maxruntime, requiredtime, maxpoints, true);
		return true;
	}
}
