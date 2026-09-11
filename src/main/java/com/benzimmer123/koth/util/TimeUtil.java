package com.benzimmer123.koth.util;

import com.benzimmer123.koth.KOTH;

public class TimeUtil {

	private final String TIME_FORMAT_HOUR = KOTH.getInstance().getConfig().getString("TIME_FORMAT_HOUR", "%hours%:");
	private final String TIME_FORMAT_MINUTE = KOTH.getInstance().getConfig().getString("TIME_FORMAT_MINUTE", "%minutes%:");
	private final String TIME_FORMAT_SECOND = KOTH.getInstance().getConfig().getString("TIME_FORMAT_SECOND", "%seconds%");
	private final String hours;
	private final String minutes;
	private final String seconds;
	
	public TimeUtil(int time) {
		int hours = (int) (time / 3600);
		int remainder = time % 3600;
		int minutes = (int) (remainder / 60);
		int seconds = (int) (remainder % 60);
		String displayHours = (hours < 10 ? "0" : "") + hours;
		String displayMinutes = (minutes < 10 ? "0" : "") + minutes;
		String displaySeconds = (seconds < 10 ? "0" : "") + seconds;

		if (hours > 0) {
			this.hours = displayHours;
		} else {
			this.hours = null;
		}

		if (minutes > 0) {
			this.minutes = displayMinutes;
		} else {
			this.minutes = null;
		}

		this.seconds = displaySeconds;
	}

	public String getHours() {
		return hours;
	}

	public String getMinutes() {
		return minutes;
	}

	public String getSeconds() {
		return seconds;
	}

	public String formatTime() {
		StringBuffer SB = new StringBuffer();

		if (hours != null) {
			SB.append(TIME_FORMAT_HOUR.replaceAll("%hours%", hours));
		}

		if (minutes != null) {
			SB.append(TIME_FORMAT_MINUTE.replaceAll("%minutes%", minutes));
		}

		if (seconds != null) {
			if (hours == null && minutes == null && seconds.startsWith("0")) {
				SB.append(TIME_FORMAT_SECOND.replaceAll("%seconds%", seconds.replaceFirst("0", "")));
			} else {
				SB.append(TIME_FORMAT_SECOND.replaceAll("%seconds%", seconds));
			}
		}

		return SB.toString();
	}

}
