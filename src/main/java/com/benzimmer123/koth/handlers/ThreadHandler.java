package com.benzimmer123.koth.handlers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.benzimmer123.koth.obj.threads.AsyncThread;
import com.google.common.collect.Maps;

public class ThreadHandler {

	private final static ThreadHandler INSTANCE;
	private Map<Integer, AsyncThread> threads;
	private int lastUsedTaskID;

	static {
		INSTANCE = new ThreadHandler();
	}

	private ThreadHandler() {
		threads = Maps.newHashMap();
	}

	public int getNewTaskID() {
		lastUsedTaskID += 1;
		return lastUsedTaskID;
	}

	public void cancelAll() {
		List<AsyncThread> threadList = threads.values().stream().collect(Collectors.toList());

		for (AsyncThread thread : threadList) {
			thread.cancel();
		}
	}

	public AsyncThread getThreadByID(int threadID) {
		if (threads.containsKey(threadID)) {
			return threads.get(threadID);
		}
		return null;
	}

	public void addThread(AsyncThread thread) {
		threads.put(thread.getThreadID(), thread);
	}

	public void removeThread(AsyncThread thread) {
		threads.remove(thread.getThreadID());
	}

	public int getActiveThreadCount() {
		return threads.size();
	}

	public static ThreadHandler getInstance() {
		return INSTANCE;
	}
}
