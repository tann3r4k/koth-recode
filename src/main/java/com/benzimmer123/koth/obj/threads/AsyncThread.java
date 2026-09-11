package com.benzimmer123.koth.obj.threads;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.benzimmer123.koth.handlers.ThreadHandler;

public abstract class AsyncThread implements Runnable {

	private int threadID;
	private ScheduledExecutorService executor;

	public AsyncThread() {
		executor = Executors.newScheduledThreadPool(1);
		threadID = ThreadHandler.getInstance().getNewTaskID();
		ThreadHandler.getInstance().addThread(this);
	}

	public abstract void run();

	public int getThreadID() {
		return threadID;
	}

	public void submitRepeatingScheduledTask(TimeUnit unit, int delay) {
		executor.scheduleAtFixedRate(this, delay, delay, unit);
	}

	public void submitScheduledTask(TimeUnit unit, int delay) {
		executor.schedule(this, delay, unit);
	}

	public void submitTask() {
		executor.submit(this);
	}

	public void cancel() {
		if (this.executor != null)
			this.executor.shutdownNow();
		ThreadHandler.getInstance().removeThread(this);
	}

}
