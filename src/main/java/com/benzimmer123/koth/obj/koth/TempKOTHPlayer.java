package com.benzimmer123.koth.obj.koth;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.api.enums.EditLootAction;
import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.api.objects.KOTHPlayer;
import com.benzimmer123.koth.scoreboard.PlayerScoreboard;
import com.benzimmer123.koth.util.LoggerUtil;

public class TempKOTHPlayer implements KOTHPlayer {

	private final String playerName;
	private String teamName;
	private double cappingSpeed;
	private boolean inDisabledScoreboardWorld;
	private boolean inBypassMode;
	private boolean hasDisabledScoreboard;
	private boolean isStartingKOTH;
	private boolean hasFeatherboardDisplayed;
	private boolean hasKiteboardDisplayed;
	private long kothCooldown;
	private long kothTimeout;
	private long editingLootTimeout;
	private EditLootAction editingLootAction;
	private KOTHArena editingKOTHLoot;
	private PlayerScoreboard scoreboard;
	private TempInventory inventory;
	private TempWandLocation wandLocation;

	public TempKOTHPlayer(Player player) {
		this.playerName = player.getName();
		this.teamName = KOTH.getInstance().getTeamManager().getTeamName(player);

		if (KOTH.getInstance().getConfig().getBoolean("DEFAULT_CAPTURE.PERMISSION_BASED")) {
			for (PermissionAttachmentInfo perm : player.getEffectivePermissions()) {
				if (perm.getPermission().startsWith("KOTH.CAPTURESPEED")) {
					String[] permission = perm.getPermission().split(".");
					try {
						cappingSpeed = Integer.parseInt(permission[2]);
					} catch (NumberFormatException e) {
						LoggerUtil.warning("Failed to load capture speed due to incorrect permission format.");
					}
				}
			}
		}

		if (cappingSpeed == 0) {
			if (KOTH.getInstance().getConfig().getBoolean("KOTH_POINTS_SYSTEM.ENABLED")) {
				this.cappingSpeed = KOTH.getInstance().getConfig().getDouble("DEFAULT_CAPTURE.POINTS");
			} else {
				this.cappingSpeed = KOTH.getInstance().getConfig().getDouble("DEFAULT_CAPTURE.TIMER");
			}
		}
	}

	public boolean hasFeatherboardDisplayed() {
		return hasFeatherboardDisplayed;
	}

	public void setFeatherboardDisplayed(boolean hasFeatherboardDisplayed) {
		this.hasFeatherboardDisplayed = hasFeatherboardDisplayed;
	}

	public boolean hasKiteboardDisplayed() {
		return hasKiteboardDisplayed;
	}

	public void setKiteboardDisplayed(boolean hasKiteboardDisplayed) {
		this.hasKiteboardDisplayed = hasKiteboardDisplayed;
	}

	public int getCappingSpeed() {
		return (int) cappingSpeed;
	}
	
	public double getCappingSpeedDouble() {
		return cappingSpeed;
	}

	public void setKOTHTimeout(long kothTimeout) {
		this.kothTimeout = kothTimeout;
	}

	public long getKOTHTimeout() {
		return kothTimeout;
	}

	public boolean hasKOTHTimeout() {
		return kothTimeout != 0;
	}

	public boolean hasKOTHTimeoutExpired() {
		if (System.currentTimeMillis() > kothTimeout)
			return true;
		return false;
	}

	public boolean hasKOTHCooldown() {
		if (kothCooldown == 0)
			return false;
		if (System.currentTimeMillis() < kothCooldown)
			return true;
		return false;
	}

	public void setKOTHCooldown(long kothCooldown) {
		this.kothCooldown = kothCooldown;
	}

	public long getKOTHCooldown() {
		return kothCooldown;
	}

	public boolean hasEditingLootExpired() {
		if (editingLootTimeout == 0)
			return true;
		if (System.currentTimeMillis() > editingLootTimeout)
			return true;
		return false;
	}

	public EditLootAction getEditingKOTHLootAction() {
		return editingLootAction;
	}

	public void setEditingKOTHLootAction(EditLootAction editingLootAction) {
		this.editingLootAction = editingLootAction;
	}

	public long getEditingLootTimeout() {
		return editingLootTimeout;
	}

	public void setEditingKOTHLootTimeout(long editingLootTimeout) {
		this.editingLootTimeout = editingLootTimeout;
	}

	public KOTHArena getEditingKOTHLoot() {
		return editingKOTHLoot;
	}

	public void setEditingKOTHLoot(KOTHArena editingKOTHLoot) {
		this.editingKOTHLoot = editingKOTHLoot;
	}

	public void setStartingKOTH(boolean isStartingKOTH) {
		this.isStartingKOTH = isStartingKOTH;
	}

	public boolean isStartingKOTH() {
		return isStartingKOTH;
	}

	public void setBypassMode(boolean inBypassMode) {
		this.inBypassMode = inBypassMode;
	}

	public boolean inBypassMode() {
		return inBypassMode;
	}

	public void setDisabledScoreboard(boolean hasDisabledScoreboard) {
		this.hasDisabledScoreboard = hasDisabledScoreboard;

		if (hasDisabledScoreboard && getScoreboard() != null) {
			getScoreboard().disappear();
			setScoreboard(null);
		}
	}

	public TempWandLocation getWandLocation() {
		return wandLocation;
	}

	public void addWandLocation(int numberLocation, Location wandLocation) {
		TempWandLocation location;
		if (this.wandLocation != null) {
			location = this.wandLocation;
		} else {
			location = new TempWandLocation();
		}
		location.setLocation(numberLocation, wandLocation);
		this.wandLocation = location;
	}

	public boolean hasWandLocation() {
		return wandLocation != null && wandLocation.hasLocations();
	}

	public PlayerScoreboard getScoreboard() {
		return scoreboard;
	}

	public void setScoreboard(PlayerScoreboard scoreboard) {
		this.scoreboard = scoreboard;
	}

	public boolean hasDisabledScoreboard() {
		return hasDisabledScoreboard;
	}

	public void setDisabledScoreboardWorld(boolean inDisabledScoreboardWorld) {
		this.inDisabledScoreboardWorld = inDisabledScoreboardWorld;
	}

	public boolean inDisabledScoreboardWorld() {
		return inDisabledScoreboardWorld;
	}

	public void setStoredInventory(TempInventory inventory) {
		this.inventory = inventory;
	}

	public TempInventory getStoredInventory() {
		return inventory;
	}

	public boolean hasStoredInventory() {
		return inventory != null;
	}

	public String getName() {
		return playerName;
	}

	public String getTeamName() {
		return teamName;
	}

}
