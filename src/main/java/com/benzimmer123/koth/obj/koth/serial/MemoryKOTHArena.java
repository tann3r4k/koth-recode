package com.benzimmer123.koth.obj.koth.serial;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.KOTHAutoStart;
import com.benzimmer123.koth.api.objects.KOTHDetails;
import com.benzimmer123.koth.api.objects.KOTHLocation;
import com.benzimmer123.koth.api.objects.KOTHLoot;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.api.objects.KOTHPoints;
import com.benzimmer123.koth.api.objects.KOTHScheduler;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.handlers.ThreadHandler;
import com.benzimmer123.koth.hooks.other.BossBar;
import com.benzimmer123.koth.storage.GsonStorage;
import com.benzimmer123.koth.tasks.KOTHAsyncTask;
import com.benzimmer123.koth.util.BroadcastUtil;
import com.benzimmer123.koth.util.LangUtil;
import com.benzimmer123.koth.util.TimeUtil;
import com.benzimmer123.koth.util.TitleUtil;
import com.benzimmer123.koth.util.TitleUtil.TitleType;

public class MemoryKOTHArena implements KOTHArena, Serializable {

	private static final long serialVersionUID = 6599254230001879627L;
	private String name;
	private String capper;
	private boolean active;
	private boolean tempDisabled;
	private MemoryKOTHLocation kothLoc;
	private MemoryKOTHScheduler kothSchedule;
	private MemoryKOTHDetails kothDetails;
	private MemoryKOTHLoot kothLoot;
	private MemoryKOTHAutoStart kothAutoStart;
	private MemoryKOTHPoints kothPoints;

	public MemoryKOTHArena(String name, Location loc1, Location loc2) {
		this.name = name;
		this.kothLoc = new MemoryKOTHLocation(loc1, loc2);
		this.kothSchedule = new MemoryKOTHScheduler(false);
		this.kothDetails = new MemoryKOTHDetails();
		this.kothLoot = new MemoryKOTHLoot();
		this.kothAutoStart = new MemoryKOTHAutoStart();
		this.kothPoints = new MemoryKOTHPoints();
		this.save();
		KOTHHandler.getInstance().addKOTH(this);
	}

	public void start(int maxRunTime, int requiredTime, int maxPoints, boolean reset) {
		getKOTHDetails().setMaxRunTime(maxRunTime);
		getKOTHDetails().setMaxPoints(maxPoints);
		getKOTHDetails().setRequiredTime(requiredTime);
		getKOTHDetails().setDisabledChat(false);
		getKOTHDetails().setChatDelay(0);
		this.active = true;
		this.kothPoints = new MemoryKOTHPoints();

		if (reset) {
			getKOTHDetails().setRunTime(0);
		}

		KOTHAsyncTask newAsyncTask = new KOTHAsyncTask(this);
		newAsyncTask.submitRepeatingScheduledTask(TimeUnit.SECONDS, 1);
		getKOTHDetails().setAsyncTaskID(newAsyncTask.getThreadID());
		this.save();

		BossBar.resetPercentage();
		Bukkit.getOnlinePlayers().stream().forEach(x -> BossBar.addBossBar(x));
		Bukkit.getOnlinePlayers().stream().forEach(x -> TitleUtil.send(x, TitleType.START, getName(true), "", ""));
	}

	public Player findPlayer() {
		List<Player> templist = KOTH.getInstance().getKOTHManager().getValidCapturers(this);

		if (templist.size() > 0) {
			Player found = templist.get(new Random().nextInt(templist.size()));
			String teamName = KOTH.getInstance().getTeamManager().getTeamName(found);
			String storedName = KOTH.getInstance().getKOTHManager().isUsingTeams() ? (!teamName.equalsIgnoreCase(LangUtil.NO_TEAM.toString())
					? teamName
					: LangUtil.NO_TEAM.toString()) : found.getName();

			if (!KOTH.getInstance().getEventManager().callKothStartCapEvent(this, found, KOTH.getInstance().getTeamManager().getTeamName(found),
					Bukkit.getWorld(getKOTHLocation().getWorld()), getKOTHLocation().getLocation1().getBlockX(), getKOTHLocation().getLocation1()
							.getBlockY(), getKOTHLocation().getLocation1().getBlockZ()))
				return null;

			BroadcastUtil.sendMessage(LangUtil.ATTEMPTING_CAPTURE.toString().replace("%koth%", getName(true)).replace("%player%", found.getName())
					.replace("%points%", getKOTHPoints().getPointsDouble(storedName) + "").replace("%time%", getTimeRemainingAsString()).replace(
							"%team%", KOTH.getInstance().getTeamManager().getTeamName(found)));

			setCapper(found, false);

			KOTH.getInstance().getTopManager().addTopParticiaption(found);
			return found;
		}

		return null;
	}

	public void setCapper(Player capper, boolean lostControl) {
		if (lostControl && capper == null) {
			int x = this.getKOTHLocation().getLocation1().getBlockX();
			int y = this.getKOTHLocation().getLocation1().getBlockY();
			int z = this.getKOTHLocation().getLocation1().getBlockZ();
			if (!KOTH.getInstance().getEventManager().callKothLoseCapEvent(this, capper, KOTH.getInstance().getTeamManager().getTeamName(capper),
					Bukkit.getWorld(getKOTHLocation().getWorld()), x, y, z, getKOTHDetails().getCaptureTime()))
				return;

			String team = getPlayerCapper() == null ? LangUtil.NO_TEAM.toString()
					: KOTH.getInstance().getTeamManager().getTeamName(getPlayerCapper());
			String storedName = KOTH.getInstance().getKOTHManager().isUsingTeams() ? (!team.equalsIgnoreCase(LangUtil.NO_TEAM.toString()) ? team
					: LangUtil.NO_TEAM.toString()) : getCapper();

			BroadcastUtil.sendMessage(LangUtil.LOST_CONTROL.toString().replaceAll("%koth%", getName(true)).replaceAll("%player%", getCapper())
					.replaceAll("%team%", team).replaceAll("%points%", getKOTHPoints().getPointsDouble(storedName) + ""));
		}

		if (KOTH.getInstance().getConfig().getBoolean("RESET_MAXRUNTIME_ON_CAPTURE") && capper != null) {
			getKOTHDetails().setRunTime(0);
			save();
		}

		setTempDisabled(false);
		resetData(capper);
	}

	public void callImportantTasks() {
		getKOTHDetails().setRunTime(getKOTHDetails().getRunTime() + 1);

		if (getKOTHDetails().getDisabledChat()) {
			getKOTHDetails().setChatDelay(getKOTHDetails().getChatDelay() - 1);

			if (getKOTHDetails().getChatDelay() == 0) {
				getKOTHDetails().setDisabledChat(false);
			}
		}

		if (getCapper() != null && !isTempDisabled()) {
			KOTH.getInstance().getKOTHManager().addCaptureValue(this);
		}

		if (KOTH.getInstance().getKOTHManager().isPointsEnabled() && getKOTHDetails().getMaxPoints() != 0) {
			KOTH.getInstance().getKOTHManager().checkMaxPointsReached(this);
		}

		if (getKOTHDetails().getMaxRunTime() != -1 && getKOTHDetails().getMaxRunTime() != 0 && getKOTHDetails().getRunTime() >= getKOTHDetails()
				.getMaxRunTime()) {
			if (KOTH.getInstance().getKOTHManager().isPointsEnabled()) {
				disable(null, !KOTH.getInstance().getKOTHManager().getTopPointsWinner(this));
				return;
			}
			disable(null, true);
			return;
		}
	}

	public void callCapperTasks() {
		Bukkit.getScheduler().runTask(KOTH.getInstance(), () -> {
			if (getCapper() != null) {
				Player current = getPlayerCapper();
				if (current == null) {
					KOTH.getInstance().getKOTHManager().lostControl(this);
					return;
				}

				KOTHPlayer kothPlayer = KOTHHandler.getInstance().getKOTHPlayer(current);

				if (KOTH.getInstance().getKOTHManager().checkDelayedLoseCapture(kothPlayer, this)) {
					return;
				}

				if (current.isDead() || !contains(current.getLocation()) || kothPlayer.inBypassMode()) {
					KOTH.getInstance().getKOTHManager().lostControl(this);
				} else {
					if (KOTH.getInstance().getKOTHManager().checkCapture(this)) {
						setTempDisabled(true);
						return;
					} else {
						if (isTempDisabled())
							setTempDisabled(false);
					}
				}
			}

			if (getCapper() == null && !getKOTHDetails().getDisabledChat()) {
				Player capper = findPlayer();

				if (capper != null) {
					setCapper(capper, false);
				}
			}
		});
	}

	public void successful() {
		Player capper = getPlayerCapper();
		int xLoc = this.getKOTHLocation().getLocation1().getBlockX();
		int yLoc = this.getKOTHLocation().getLocation1().getBlockY();
		int zLoc = this.getKOTHLocation().getLocation1().getBlockZ();

		Bukkit.getScheduler().runTask(KOTH.getInstance(), () -> {
			if (!KOTH.getInstance().getEventManager().callKothWinEvent(this, capper, KOTH.getInstance().getTeamManager().getTeamName(capper),
					Bukkit.getWorld(getKOTHLocation().getWorld()), xLoc, yLoc, zLoc, getKOTHDetails().getCaptureTime()))
				return;
		});

		try {
			KOTH.getInstance().getTopManager().addTopWin(capper);

			String capperName = getCapper();
			String team = KOTH.getInstance().getTeamManager().getTeamName(capper);
			String storedName = KOTH.getInstance().getKOTHManager().isUsingTeams() ? (!team.equalsIgnoreCase(LangUtil.NO_TEAM.toString()) ? team
					: LangUtil.NO_TEAM.toString()) : capper.getName();

			KOTHHandler.getInstance().setLastCappedPlayer(capper.getName());
			KOTHHandler.getInstance().setLastCappedTeam(team);

			Bukkit.getScheduler().runTask(KOTH.getInstance(), () -> {
				BroadcastUtil.sendMessage(LangUtil.CAPTURED_KOTH.toString().replaceAll("%koth%", getName(true)).replaceAll("%player%", capper
						.getName()).replaceAll("%team%", KOTH.getInstance().getTeamManager().getTeamName(capper)).replaceAll("%points%",
								getKOTHPoints().getPointsDouble(storedName) + ""));
				Bukkit.getOnlinePlayers().stream().forEach(x -> TitleUtil.send(x, TitleType.CAPTURE, getName(true), team, capperName));
				KOTH.getInstance().getRewardManager().rewardTeamMembers(capper, this);
			});
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			disable(null, false);
		}
	}

	public void disable(CommandSender sender, boolean announce) {
		try {
			Bukkit.getScheduler().runTask(KOTH.getInstance(), () -> {
				KOTH.getInstance().getEventManager().callKothEndEvent(this, Bukkit.getWorld(getKOTHLocation().getWorld()), getKOTHLocation()
						.getLocation1().getBlockX(), getKOTHLocation().getLocation1().getBlockY(), getKOTHLocation().getLocation1().getBlockZ());
			});

			for (String pointsName : getKOTHPoints().getPointsMap().keySet()) {
				int totalPoints = (int) getKOTHPoints().getPointsDouble(pointsName);
				KOTHHandler.getInstance().removeTotalPoints(pointsName, totalPoints);
			}

			if (announce) {
				String name = sender == null ? LangUtil.AUTOMATIC_HOSTNAME.toString() : sender.getName();
				BroadcastUtil.sendMessage(LangUtil.KOTH_ENDED.toString().replaceAll("%koth%", getName(true)).replaceAll("%player%", name));
				Bukkit.getOnlinePlayers().stream().forEach(x -> TitleUtil.send(x, TitleType.END, getName(true), "", ""));
			}

			Bukkit.getOnlinePlayers().stream().forEach(x -> BossBar.removeBossBar(x));
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			this.active = false;

			if (ThreadHandler.getInstance().getThreadByID(getKOTHDetails().getAsyncTaskID()) != null) {
				ThreadHandler.getInstance().getThreadByID(getKOTHDetails().getAsyncTaskID()).cancel();
			}

			setCapper(null, false);
			this.save();
		}
	}

	public void resetData(Player capper) {
		if (capper != null)
			this.capper = capper.getName();
		else
			this.capper = null;

		BossBar.resetPercentage();
		getKOTHDetails().setCaptureTime(0);
		getKOTHDetails().setDisabledChat(true);
		getKOTHDetails().setChatDelay(KOTH.getInstance().getConfig().getInt("KNOCK_DELAY"));
	}

	public KOTHPoints getKOTHPoints() {
		if (kothPoints == null) {
			this.kothPoints = new MemoryKOTHPoints();
		}
		return kothPoints;
	}

	public KOTHAutoStart getKOTHAutoStart() {
		if (kothAutoStart == null) {
			this.kothAutoStart = new MemoryKOTHAutoStart();
		}
		return kothAutoStart;
	}

	public KOTHLoot getKOTHLoot() {
		if (kothLoot == null) {
			this.kothLoot = new MemoryKOTHLoot();
		}
		return kothLoot;
	}

	public KOTHDetails getKOTHDetails() {
		if (kothDetails == null) {
			this.kothDetails = new MemoryKOTHDetails();
		}
		return kothDetails;
	}

	public KOTHScheduler getKOTHScheduler() {
		if (kothSchedule == null) {
			this.kothSchedule = new MemoryKOTHScheduler(false);
		}
		return kothSchedule;
	}

	public void setCapper(String capper) {
		this.capper = capper;
	}

	public KOTHLocation getKOTHLocation() {
		return kothLoc;
	}

	public int getTimeRemainingAsInt() {
		return getKOTHDetails().getRequiredTime() - getKOTHDetails().getCaptureTime();
	}

	public String getTimeRemainingAsString() {
		return new TimeUtil(getKOTHDetails().getRequiredTime() - getKOTHDetails().getCaptureTime()).formatTime();
	}

	public void setTempDisabled(boolean tempDisabled) {
		this.tempDisabled = tempDisabled;
	}

	public boolean isTempDisabled() {
		return tempDisabled;
	}

	public boolean isActive() {
		return active;
	}

	public String getCapper() {
		return capper;
	}

	public Player getPlayerCapper() {
		if (capper != null)
			return Bukkit.getPlayer(capper);
		return null;
	}

	public boolean contains(Location loc) {
		MemoryCuboid cuboid = new MemoryCuboid(getKOTHLocation().getLocation1(), getKOTHLocation().getLocation2());
		return cuboid.contains(loc);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getName(boolean capitalised) {
		if (capitalised) {
			return com.benzimmer123.koth.util.TextUtil.capitalize(name);
		}
		return name;
	}

	public void save() {
		GsonStorage.serialize(this, "koths/" + getName(false).toLowerCase() + ".json", "koth");
	}
}
