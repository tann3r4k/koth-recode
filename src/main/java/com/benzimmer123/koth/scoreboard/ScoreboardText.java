package com.benzimmer123.koth.scoreboard;

import com.benzimmer123.koth.KOTH;

public class ScoreboardText {

	private String text;
	private String extendedText;

	private boolean extended = false;

	public ScoreboardText(String text) {
		int max = KOTH.getInstance().getScoreboardManager().getMaxLineLength() / 2;
		if (text.length() > max) {
			this.extended = true;

			this.extendedText = text.substring(16, text.length());
			this.text = text.substring(0, 16);
		} else {
			this.text = text;
			this.extendedText = "";
		}
	}

	public String getText() {
		return this.text;
	}

	public String getExtendedText() {
		return this.extendedText;
	}

	public boolean isExtended() {
		return this.extended;
	}

}