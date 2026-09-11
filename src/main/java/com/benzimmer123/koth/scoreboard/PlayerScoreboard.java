package com.benzimmer123.koth.scoreboard;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.kyori.adventure.text.Component;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.exceptions.ScoreboardTitleTooLong;
import com.benzimmer123.koth.util.TextUtil;

public class PlayerScoreboard {

	private ScoreboardProvider provider;
	private Player player;
	private Scoreboard scoreboard;
	private Objective objective;
	private boolean active;
	private int lastSentCount = -1;

	public PlayerScoreboard(ScoreboardProvider provider, Player player) {
		this.player = player;
		this.provider = provider;

		if (provider.getTitle(player) != null && provider.getTitle(player).length() > KOTH.getInstance().getScoreboardManager().getMaxTitleLength()) {
			throw new ScoreboardTitleTooLong("Title is longer than " + KOTH.getInstance().getScoreboardManager().getMaxTitleLength() + " characters.",
					this);
		}

		Bukkit.getScheduler().runTask(KOTH.getInstance(), () -> {
			if (!KOTH.getInstance().getEventManager().callScoreboardEnableEvent(player))
				return;
			
			scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
			objective = getOrCreateObjective(provider.getTitle(player));
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			player.setScoreboard(scoreboard);
			active = true;
		});
	}

	public Scoreboard getBukkitScoreboard() {
		return this.scoreboard;
	}

	public void setProvider(ScoreboardProvider provider) {
		this.provider = provider;

		if (provider != null) {
			this.update();
		} else {
			this.disappear();
		}
	}

	public void disappear() {
		if (this.active && this.scoreboard != null) {
			this.scoreboard.getTeams().forEach((team) -> team.unregister());
			this.scoreboard.getObjectives().forEach((obj) -> obj.unregister());
		}
		Bukkit.getScheduler().runTask(KOTH.getInstance(), () -> {
			KOTH.getInstance().getEventManager().callScoreboardDisableEvent(player);
			KOTH.getInstance().getScoreboardManager().displayDefaultBoard(getPlayer());
		});
	}

	public void remove(int index) {
		if (!this.active)
			return;

		String name = this.getNameForIndex(index);

		this.scoreboard.resetScores(name);

		Team team = getOrCreateTeam(ChatColor.stripColor(TextUtil.left(this.provider.getTitle(this.player), 14)) + index, index);
		team.unregister();
	}

	public void update() {
		if (!this.active)
			return;

		String title = this.provider.getTitle(this.player);
		List<ScoreboardText> lines = this.provider.getLines(this.player);
		int maxLength = KOTH.getInstance().getScoreboardManager().getMaxLineLength() / 2;

		if (this.objective.getDisplaySlot() == DisplaySlot.SIDEBAR) {
			this.objective.displayName(Component.text(title == null ? "" : title));
		}

		for (int i = 0; i < lines.size(); i++) {
			Team team = this.getOrCreateTeam(ChatColor.stripColor(TextUtil.left(title, 14)) + i, i);
			ScoreboardText text = lines.get(lines.size() - i - 1);
			String firstText = text.getText();
			boolean colourCode = false;

			if (firstText.endsWith("§")) {
				firstText = firstText.substring(0, firstText.length() - 1);
				colourCode = true;
			}

			String extendedText = colourCode ? "§" + text.getExtendedText() : ChatColor.getLastColors(firstText) + text.getExtendedText();

			this.objective.getScore(this.getNameForIndex(i)).setScore(i + 1);

			if (extendedText.length() > maxLength) {
				extendedText = extendedText.length() > maxLength ? extendedText.substring(0, maxLength - 1)
						: extendedText.substring(0, extendedText.length() - 1);
			}

			team.setPrefix(firstText);
			team.setSuffix(extendedText);
		}

		if (this.lastSentCount != -1) {
			for (int i = 0; i < this.lastSentCount - lines.size(); i++) {
				this.remove(lines.size() + i);
			}
		}

		this.lastSentCount = lines.size();
	}

	public Team getOrCreateTeam(String team, int i) {
		Team value = this.scoreboard.getTeam(team);

		if (value == null) {
			value = this.scoreboard.registerNewTeam(team);
			value.addEntry(this.getNameForIndex(i));
		}

		return value;
	}

	public Objective getOrCreateObjective(String objective) {
		Objective value = this.scoreboard.getObjective("dummyBoard");
		Component display = Component.text(objective == null ? "" : objective);

		if (value == null) {
			value = this.scoreboard.registerNewObjective("dummyBoard", Criteria.DUMMY, display);
		} else {
			value.displayName(display);
		}
		return value;
	}

	public String getNameForIndex(int index) {
		return ChatColor.values()[index].toString() + ChatColor.RESET;
	}

	public ScoreboardProvider getProvider() {
		return this.provider;
	}

	public Player getPlayer() {
		return this.player;
	}

	public boolean isActive() {
		return this.active;
	}

}
