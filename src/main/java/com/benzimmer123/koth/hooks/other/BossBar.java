package com.benzimmer123.koth.hooks.other;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.hooks.face.OtherHook;
import com.benzimmer123.koth.tasks.BossBarTask;
import com.benzimmer123.koth.util.LangUtil;
import com.benzimmer123.koth.util.LoggerUtil;

public class BossBar implements OtherHook {

	private static org.bukkit.boss.BossBar bb;

	@Override
	public void setup() {
		String title = LangUtil.replaceString(KOTH.getInstance().getConfig().getString("BOSS_BAR.TEXT"));
		title = KOTH.getInstance().getPlaceholderManager().parsePlaceholders(null, title);
		bb = Bukkit.createBossBar(title, BarColor.valueOf(KOTH.getInstance().getConfig().getString("BOSS_BAR.COLOR")), BarStyle.SEGMENTED_20);
		bb.setVisible(true);
		bb.setProgress(0);

		if (KOTH.getInstance().getConfig().isSet("BOSS_BAR.UPDATE_TITLE_TASK") && KOTH.getInstance().getConfig().getInt(
				"BOSS_BAR.UPDATE_TITLE_TASK") != -1) {
			BossBarTask bossBarTask = new BossBarTask();
			bossBarTask.submitRepeatingScheduledTask(TimeUnit.SECONDS, KOTH.getInstance().getConfig().getInt("BOSS_BAR.UPDATE_TITLE_TASK"));

			LoggerUtil.success("[KOTH] Successfully started BossBar title updating task.");

			if (KOTH.getInstance().getConfig().getInt("BOSS_BAR.UPDATE_TITLE_TASK") <= 5) {
				LoggerUtil.warning("[KOTH] You are trying to load BossBar task with: " + KOTH.getInstance().getConfig().getInt(
						"BOSS_BAR.UPDATE_TITLE_TASK") + " update ticks.");
				LoggerUtil.warning("[KOTH] It is highly advised to keep this at 20 (once per second).");
			}
		}
	}
	
	@Override
	public String getAPIName() {
		return "BossBar";
	}

	public static void resetPercentage() {
		if (bb == null)
			return;

		bb.setProgress(0);
	}

	public static void updateTitle(Player player) {
		if (!hasBar(player)) {
			addBossBar(player);
		}

		if (bb == null)
			return;

		String title = LangUtil.replaceString(KOTH.getInstance().getConfig().getString("BOSS_BAR.TEXT"));
		title = KOTH.getInstance().getPlaceholderManager().parsePlaceholders(player, title);
		bb.setTitle(title);
	}

	public static void updateHealth(Player player, float percentage) {
		if (!hasBar(player)) {
			addBossBar(player);
		}

		if (bb == null)
			return;

		if (percentage > 1) {
			percentage = (float) 1.0;
		}

		bb.setProgress(percentage);
	}

	public static void addBossBar(Player player) {
		if (hasBar(player)) {
			return;
		}

		if (bb == null)
			return;

		bb.addPlayer(player);
	}

	public static void removeBossBar(Player player) {
		if (bb == null)
			return;

		bb.removePlayer(player);
	}

	private static boolean hasBar(Player player) {
		if (bb == null)
			return false;

		return bb.getPlayers().contains(player);
	}

}
