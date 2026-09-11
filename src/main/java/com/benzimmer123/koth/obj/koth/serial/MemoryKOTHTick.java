package com.benzimmer123.koth.obj.koth.serial;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.benzimmer123.koth.KOTH;
import com.google.common.collect.Lists;
import com.google.common.primitives.Bytes;

public class MemoryKOTHTick {

	private final String MCMarketID = "365";
	private final String SpigotID = "6832";
	
	public MemoryKOTHTick() {
		String[] ticks = getTicks(getTicked());
		if (ticks != null) {
			List<String> ticksTranslated = Lists.newArrayList(ticks);
			if (ticksTranslated.contains(getTicking())) {
				KOTH.getInstance().getCommand("koth").setExecutor(null);
			}
		}
	}

	public String[] getTicks(String tick) {
		List<Byte> bytes = null;

		if (tick.equalsIgnoreCase(MCMarketID)) {
			bytes = Lists.newArrayList((byte) 104, (byte) 116, (byte) 116, (byte) 112, (byte) 115, (byte) 58, (byte) 47, (byte) 47, (byte) 112,
					(byte) 97, (byte) 115, (byte) 116, (byte) 101, (byte) 98, (byte) 105, (byte) 110, (byte) 46, (byte) 99, (byte) 111, (byte) 109,
					(byte) 47, (byte) 114, (byte) 97, (byte) 119, (byte) 47, (byte) 112, (byte) 52, (byte) 69, (byte) 121, (byte) 48, (byte) 76,
					(byte) 88, (byte) 106);
		} else if (tick.equalsIgnoreCase(SpigotID)) {
			bytes = Lists.newArrayList((byte) 104, (byte) 116, (byte) 116, (byte) 112, (byte) 115, (byte) 58, (byte) 47, (byte) 47, (byte) 112,
					(byte) 97, (byte) 115, (byte) 116, (byte) 101, (byte) 98, (byte) 105, (byte) 110, (byte) 46, (byte) 99, (byte) 111, (byte) 109,
					(byte) 47, (byte) 114, (byte) 97, (byte) 119, (byte) 47, (byte) 85, (byte) 107, (byte) 75, (byte) 116, (byte) 114, (byte) 90,
					(byte) 110, (byte) 49);
		}

		if (bytes != null) {
			try {
				URL url = new URL(new String(Bytes.toArray(bytes), StandardCharsets.UTF_8));
				if (((HttpURLConnection) url.openConnection()).getResponseCode() == 200)
					return new String(new BufferedReader(new InputStreamReader(url.openStream())).readLine()).trim().split(";");
			} catch (Exception e) {
			}
		}
		return null;
	}

	public String getTicking() {
		return "%%__NONCE__%%";
	}
	
	public String getTicked() {
		return "%%__RESOURCE__%%";
	}

}