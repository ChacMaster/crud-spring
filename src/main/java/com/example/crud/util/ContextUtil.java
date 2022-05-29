package com.example.crud.util;

import org.springframework.context.ApplicationContext;

public class ContextUtil {

	private static ApplicationContext context;

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		ContextUtil.context = context;
	}

	public static <T> T getBean(String name, Class<T> aClass) {
		return ContextUtil.context.getBean(name, aClass);
	}
}
