package com.example.crud.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextProvider implements ApplicationContextAware {

	private static ApplicationContext context;

	private ContextProvider() {
	}

	private static void setContext(ApplicationContext context) {
		ContextProvider.context = context;
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		setContext(context);
	}

	public static <T> T getBean(String name, Class<T> aClass) {
		return context.getBean(name, aClass);
	}
}
