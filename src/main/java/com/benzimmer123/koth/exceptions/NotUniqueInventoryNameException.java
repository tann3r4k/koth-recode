package com.benzimmer123.koth.exceptions;

import org.bukkit.Bukkit;

import com.benzimmer123.koth.KOTH;

public class NotUniqueInventoryNameException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * This is used to prevent any overriding inventory issues if same name is
	 * used more than once in config file.
	 * 
	 */

	public NotUniqueInventoryNameException(String exception) {
		super(exception);
		Bukkit.getPluginManager().disablePlugin(KOTH.getInstance());
	}

}
