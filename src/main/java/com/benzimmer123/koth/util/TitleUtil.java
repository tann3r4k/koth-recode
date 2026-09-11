package com.benzimmer123.koth.util;

import org.bukkit.entity.Player;

import com.benzimmer123.koth.KOTH;

public enum TitleUtil {

	START_TITLE(LangUtil.replaceString(KOTH.getInstance().getConfig().getString("TITLES.KOTH_START.TITLE"))),
	START_SUBTITLE(LangUtil.replaceString(KOTH.getInstance().getConfig().getString("TITLES.KOTH_START.SUBTITLE"))),

	CAPTURE_TITLE(LangUtil.replaceString(KOTH.getInstance().getConfig().getString("TITLES.KOTH_CAPTURE.TITLE"))),
	CAPTURE_SUBTITLE(LangUtil.replaceString(KOTH.getInstance().getConfig().getString("TITLES.KOTH_CAPTURE.SUBTITLE"))),

	END_TITLE(LangUtil.replaceString(KOTH.getInstance().getConfig().getString("TITLES.KOTH_END.TITLE"))),
	END_SUBTITLE(LangUtil.replaceString(KOTH.getInstance().getConfig().getString("TITLES.KOTH_END.SUBTITLE")));

	private final String text;

	TitleUtil(String text) {
		this.text = text;
	};

	public String getText() {
		return text;
	}

	@SuppressWarnings("deprecation")
	public static void send(Player player, TitleType type, String koth, String team, String capper) {
		if (team == null)
			team = "";

		if (capper == null)
			capper = "";

		switch (type) {

		case START:
			if (KOTH.getInstance().getConfig().getBoolean("TITLES.KOTH_START.ENABLED")) {
				player.sendTitle(START_TITLE.getText().replaceAll("%koth%", koth), START_SUBTITLE.getText().replaceAll("%koth%", koth));
			}
			break;
		case END:
			if (KOTH.getInstance().getConfig().getBoolean("TITLES.KOTH_END.ENABLED")) {
				player.sendTitle(END_TITLE.getText().replaceAll("%koth%", koth), END_SUBTITLE.getText().replaceAll("%koth%", koth));
			}
			break;
		case CAPTURE:
			if (KOTH.getInstance().getConfig().getBoolean("TITLES.KOTH_CAPTURE.ENABLED")) {
				player.sendTitle(CAPTURE_TITLE.getText().replaceAll("%koth%", koth).replaceAll("%team%", team).replaceAll("%player%", capper),
						CAPTURE_SUBTITLE.getText().replaceAll("%koth%", koth).replaceAll("%team%", team).replaceAll("%player%", capper));
			}
			break;
		}
	}

	public enum TitleType {

		START,
		END,
		CAPTURE;

	}
}
