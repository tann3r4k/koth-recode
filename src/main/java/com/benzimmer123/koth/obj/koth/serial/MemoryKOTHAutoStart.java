package com.benzimmer123.koth.obj.koth.serial;

import java.io.Serializable;

import com.benzimmer123.koth.api.objects.KOTHAutoStart;

public class MemoryKOTHAutoStart implements KOTHAutoStart, Serializable {

	private static final long serialVersionUID = -3297427313543544966L;
	private int players;
	private int runTime;

	public int getPlayers() {
		return players;
	}

	public int getRunTime() {
		return runTime;
	}

	public void resetAutoStart() {
		this.players = 0;
		this.runTime = 0;
	}

	public void setAutoStart(int players, int runTime) {
		this.players = players;
		this.runTime = runTime;
	}

	public boolean checkAutoStart(int players) {
		if (hasAutoStart() && players >= getPlayers()) {
			return true;
		}
		return false;
	}

	public boolean hasAutoStart() {
		if (players != 0 && runTime != 0) {
			return true;
		}
		return false;
	}
}