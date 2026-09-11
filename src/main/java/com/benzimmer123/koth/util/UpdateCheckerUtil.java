package com.benzimmer123.koth.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import com.benzimmer123.koth.KOTH;

public class UpdateCheckerUtil {

	private String currentVersion;
	private int resourceId;

	public UpdateCheckerUtil(String currentVersion, int resourceId) {
		this.currentVersion = currentVersion;
		this.resourceId = resourceId;
	}

	public String getLatestVersion() {
		if (!KOTH.getInstance().getConfig().getBoolean("SEARCH_FOR_UPDATES", true)) {
			return null;
		}

		try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
				Scanner scanner = new Scanner(inputStream)) {
			if (scanner.hasNext()) {
				String updatedVersion = scanner.next();
				if (!currentVersion.equalsIgnoreCase(updatedVersion)) {
					return updatedVersion;
				}
			}
		} catch (IOException exception) {
		}

		return null;
	}
}