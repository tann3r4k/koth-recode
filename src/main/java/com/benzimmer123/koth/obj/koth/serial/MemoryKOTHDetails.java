package com.benzimmer123.koth.obj.koth.serial;

import java.io.Serializable;
import java.util.List;

import org.bukkit.Bukkit;

import com.benzimmer123.koth.api.objects.KOTHDetails;
import com.benzimmer123.koth.hooks.other.BossBar;
import com.google.common.collect.Lists;

public class MemoryKOTHDetails implements KOTHDetails, Serializable {

	private static final long serialVersionUID = -3417062120216088406L;
	private int runTime;
	private int maxRunTime;
	private int maxPoints;
	private int captureTime;
	private int requiredTime;
	private int asyncTaskID;
	private int chatDelay;
	private boolean disableChat;
	private List<Integer> broadcastTimes;
	private List<String> rewardCmds;

	public List<String> getRewardCmds() {
		if (rewardCmds == null)
			return Lists.newArrayList();
		return rewardCmds;
	}

	public void addRewardCmd(String cmd) {
		if (rewardCmds == null)
			rewardCmds = Lists.newArrayList();
		rewardCmds.add(cmd);
	}

	public void setBroadcastTimes(List<Integer> times) {
		this.broadcastTimes = times;
	}

	public List<Integer> getBroadcastTimes() {
		if (broadcastTimes == null)
			return Lists.newArrayList();
		return broadcastTimes;
	}

	public void setRequiredTime(int requiredTime) {
		this.requiredTime = requiredTime;
	}

	public void setChatDelay(int chatDelay) {
		this.chatDelay = chatDelay;
	}

	public void setAsyncTaskID(int asyncTaskID) {
		this.asyncTaskID = asyncTaskID;
	}

	public void setRunTime(int runTime) {
		this.runTime = runTime;
	}

	public void setMaxRunTime(int maxRunTime) {
		this.maxRunTime = maxRunTime;
	}

	public void setMaxPoints(int maxPoints) {
		this.maxPoints = maxPoints;
	}

	public void setCaptureTime(int captureTime) {
		this.captureTime = captureTime;

		if (captureTime != 0) {
			float time = (float) ((double) captureTime / (double) requiredTime);
			if (time == 1.0) {
				Bukkit.getOnlinePlayers().stream().forEach(x -> BossBar.updateHealth(x, (float) 0.99));
			} else {
				Bukkit.getOnlinePlayers().stream().forEach(x -> BossBar.updateHealth(x, (float) ((double) captureTime / (double) requiredTime)));
			}
		}
	}

	public void setDisabledChat(boolean disabledChat) {
		this.disableChat = disabledChat;
	}

	public int getRunTime() {
		return runTime;
	}

	public int getMaxRunTime() {
		return maxRunTime;
	}

	public int getMaxPoints() {
		return maxPoints;
	}

	public int getCaptureTime() {
		return captureTime;
	}

	public int getRequiredTime() {
		return requiredTime;
	}

	public int getAsyncTaskID() {
		return asyncTaskID;
	}

	public int getChatDelay() {
		return chatDelay;
	}

	public boolean getDisabledChat() {
		return disableChat;
	}

}
