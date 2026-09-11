package com.benzimmer123.koth.tasks;

import com.benzimmer123.koth.api.objects.KOTHArena;
import com.benzimmer123.koth.handlers.KOTHHandler;
import com.benzimmer123.koth.obj.threads.AsyncThread;

public class KOTHAsyncTask extends AsyncThread {

	private final KOTHArena koth;

	public KOTHAsyncTask(KOTHArena koth) {
		this.koth = koth;
	}

	@Override
	public void run() {
		if (!KOTHHandler.getInstance().isPluginLoaded())
			return;

		koth.callImportantTasks();
		koth.callCapperTasks();
	}
}
