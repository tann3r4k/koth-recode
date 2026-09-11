package cc.javajobs.factionsbridge;

import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;

public class FactionsBridge {
	public static FactionsBridge get() {
		return new FactionsBridge();
	}

	public static FactionsAPI getFactionsAPI() {
		return null;
	}
}
