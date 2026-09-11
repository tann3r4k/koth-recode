package com.benzimmer123.koth.util;

import java.lang.reflect.Method;

public class ReflectionUtil {

	public static boolean isPresent(String className) {
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}

	public static boolean exists(String className, String methodName) {
		Class<?> foundClass = null;

		try {
			foundClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (foundClass == null)
			return false;

		Method[] methods = foundClass.getMethods();
		boolean containsMethod = false;

		for (Method method : methods) {
			if (method.getName().equalsIgnoreCase(methodName)) {
				containsMethod = true;
				break;
			}
		}

		if (!containsMethod)
			return false;

		return true;
	}
	
}
