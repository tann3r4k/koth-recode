package com.benzimmer123.koth.exceptions;

import com.benzimmer123.koth.KOTH;

public class TeamHookException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * This is used to prevent any team hooks from causing the plugin to
	 * completely stop working. Set API to null after so it doesn't continously
	 * spam console.
	 * 
	 * Added to print throwable as we are covering a wide range of errors and
	 * exceptions.
	 * 
	 */

	public TeamHookException(String exception) {
		super(exception);
		KOTH.getInstance().getTeamManager().setAPI(null);
	}

	public TeamHookException(String exception, Throwable e) {
		super(exception);
		KOTH.getInstance().getTeamManager().setAPI(null);
		e.printStackTrace();
	}

}
