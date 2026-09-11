package com.benzimmer123.koth.hooks.scoreboard;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.hooks.face.ScoreboardHook;
import com.benzimmer123.koth.util.LoggerUtil;
import com.benzimmer123.koth.util.ReflectionUtil;

import net.kitesoftware.board.group.Group;
import net.kitesoftware.board.group.GroupType;
import net.kitesoftware.board.user.KiteUser;
import net.kitesoftware.board.user.UserManager;

public class KiteBoard implements ScoreboardHook {

	private static net.kitesoftware.board.KiteBoard kiteBoard;

	public void setup() {
		if (!ReflectionUtil.isPresent("net.kitesoftware.board.KiteBoard")) {
			KOTH.getInstance().getScoreboardManager().getScoreboardsLoaded().remove("KiteBoard");
			LoggerUtil.warning("[KOTH] Unloaded scoreboard KiteBoard because outdated plugin was found.");
			return;
		}

		RegisteredServiceProvider<net.kitesoftware.board.KiteBoard> serviceProvider = Bukkit.getServer().getServicesManager().getRegistration(
				net.kitesoftware.board.KiteBoard.class);

		if (serviceProvider == null) {
			KOTH.getInstance().getScoreboardManager().getScoreboardsLoaded().remove("KiteBoard");
			LoggerUtil.warning("[KOTH] Unloaded scoreboard KiteBoard because service provider was not found.");
			return;
		}

		kiteBoard = serviceProvider.getProvider();
	}

	public void displayTrigger(Player player) {
		UserManager userManager = kiteBoard.getUserManager();
		KiteUser user = userManager.getUser(player);
		Optional<Group> result = kiteBoard.getGroupManager().getGroup(KOTH.getInstance().getConfig().getString("KITEBOARD_TRIGGER.SCOREBOARD_NAME"),
				GroupType.SCOREBOARD);
		
		if (result.isPresent()) {
			user.setGroupOverride(GroupType.SCOREBOARD, result.get());
		}
	}

	public void removeScoreboard(Player player) {
		UserManager userManager = kiteBoard.getUserManager();
		KiteUser user = userManager.getUser(player);
		user.setGroupEnabled(GroupType.SCOREBOARD, false);
	}

	public void giveScoreboard(Player player) {
		UserManager userManager = kiteBoard.getUserManager();
		KiteUser user = userManager.getUser(player);

		if (user.isGroupOverridden(GroupType.SCOREBOARD)) {
			user.setGroupOverride(GroupType.SCOREBOARD, null);
		}

		user.setGroupEnabled(GroupType.SCOREBOARD, true);
		user.updateGroups();
	}

	public String getAPIName() {
		return "KiteBoard";
	}
}
