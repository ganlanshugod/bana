package org.bana.core.exception;

import java.util.Locale;

public enum LangCode {
	英语(Locale.ENGLISH), 中文(Locale.CHINA);

	private String country;
	private String language;
	private Locale locale;

	private LangCode(Locale locale) {
		this.country = locale.getCountry();
		this.language = locale.getLanguage();
		this.locale = locale;
	}

	public String getCountry() {
		return this.country;
	}

	public String getLanguage() {
		return this.language;
	}

	public String getLangCode() {
		return this.language + "_" + this.country;
	}

	public static void main(String[] args) {
		Locale defaultLocale = Locale.getDefault();
		System.out.println("country=" + defaultLocale.getCountry());
		System.out.println("language=" + defaultLocale.getLanguage());
		System.out.println(中文.getLangCode());
		System.out.println(英语.getLangCode());
	}

	public static LangCode getInstance(Locale locale) {
		for (LangCode langCode : values()) {
			if (langCode.locale.equals(locale)) {
				return langCode;
			}
		}
		return 中文;
	}
}