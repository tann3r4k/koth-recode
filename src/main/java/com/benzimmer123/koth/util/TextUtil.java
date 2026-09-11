package com.benzimmer123.koth.util;

public final class TextUtil {

	private TextUtil() {
	}

	public static String capitalize(String text) {
		if (text == null || text.isEmpty()) {
			return text;
		}
		String[] words = text.toLowerCase().split(" ");
		StringBuilder out = new StringBuilder();
		for (String word : words) {
			if (word.isEmpty()) {
				continue;
			}
			if (out.length() > 0) {
				out.append(' ');
			}
			out.append(Character.toUpperCase(word.charAt(0)));
			if (word.length() > 1) {
				out.append(word.substring(1));
			}
		}
		return out.toString();
	}

	public static String left(String text, int length) {
		if (text == null) {
			return null;
		}
		return text.length() <= length ? text : text.substring(0, length);
	}
}
