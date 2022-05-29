package com.example.crud.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.example.crud.util.ContextUtil;

public class MessageFactory {

	private static MessageSource messageSource;

	private MessageFactory() {
		throw new IllegalStateException("Utility class");
	}

	private static MessageSource getMessageSource() {
		if (messageSource == null)
			messageSource = ContextUtil.getBean("messageSource", MessageSource.class);
		return messageSource;
	}

	public static String getMessage(Messages message) {
		return getMessageSource().getMessage(message.toString(), null, LocaleContextHolder.getLocale());
	}

	public static String getMessage(Messages message, String... args) {
		return getMessageSource().getMessage(message.toString(), args, LocaleContextHolder.getLocale());
	}

	public static String getLabel(String label) {
		return getMessageSource().getMessage(label, null, LocaleContextHolder.getLocale());
	}
}