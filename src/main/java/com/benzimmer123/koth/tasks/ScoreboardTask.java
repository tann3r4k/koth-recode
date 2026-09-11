package com.benzimmer123.koth.tasks;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.obj.threads.AsyncThread;

public class ScoreboardTask extends AsyncThread {

	private boolean loadedScoreboard;

	@Override
	public void run() {
		try {
			if (!KOTHHandler.getInstance().isPluginLoaded())
				return;

			if (KOTH.getInstance().getScoreboardManager().isScoreboardDisabled())
				return;

			boolean sbLoaded = isScoreboardLoaded();

			if (!KOTH.getInstance().getKOTHManager().isActiveKOTH()) {
				if (sbLoaded) {
					KOTH.getInstance().getScoreboardManager().disableAllScoreboards();
					setLoadedScoreboard(false);
				}
				return;
			}

			KOTH.getInstance().getScoreboardManager().updateScoreboards();

			if (!sbLoaded) {
				setLoadedScoreboard(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setLoadedScoreboard(boolean loadedScoreboard) {
		this.loadedScoreboard = loadedScoreboard;
	}

	public boolean isScoreboardLoaded() {
		return loadedScoreboard;
	}
}