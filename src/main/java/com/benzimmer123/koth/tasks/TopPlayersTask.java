package com.benzimmer123.koth.tasks;

import com.benzimmer123.koth.KOTH;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.obj.threads.AsyncThread;

public class TopPlayersTask extends AsyncThread {

	@Override
	public void run() {
		if (!KOTHHandler.getInstance().isPluginLoaded())
			return;

		KOTH.getInstance().getTopManager().sortPlayerList();
	}

}
