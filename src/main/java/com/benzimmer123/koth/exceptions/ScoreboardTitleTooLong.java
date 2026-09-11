package com.benzimmer123.koth.exceptions;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.scoreboard.PlayerScoreboard;

public class ScoreboardTitleTooLong extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * This is used to prevent the scoreboard from kicking players with lines
	 * being too long.
	 * 
	 */

	public ScoreboardTitleTooLong(String exception, PlayerScoreboard playerSb) {
		super(exception);
		KOTH.getInstance().getScoreboardManager().setScoreboardDisabled(true);
		KOTH.getInstance().getScoreboardManager().disableAllScoreboards();
		playerSb.disappear();
	}
}
