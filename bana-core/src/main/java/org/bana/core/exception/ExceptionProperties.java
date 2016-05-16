package org.bana.core.exception;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.bana.common.util.basic.StringUtils;
import org.bana.core.resource.ResourceLoader;
import org.springframework.core.io.Resource;

public class ExceptionProperties {
	private static Map<String, Properties> exceptionProperties = new HashMap<String, Properties>();
	private static final String ROOT_PATH = "classpath*:/exception/**/*_";
	private static String encoding = "utf-8";

	static {
		init();
	}

	public static synchronized void init() {
		for (LangCode langCode : LangCode.values()) {
			Resource[] resources = ResourceLoader.getResource(ROOT_PATH + langCode.getLangCode() + ".properties");
			Properties prop = new Properties();
			if (resources != null) {
				for (Resource resource : resources) {
					try {
						prop.load(resource.getInputStream());
					} catch (IOException e) {
						throw new BanaCoreException(e);
					}
				}
			}
			exceptionProperties.put(langCode.getLangCode(), prop);
		}
	}

	public static String getMessage(String key, Map<String, Object> context, LangCode langCode) {
		if (langCode == null) {
			throw new BanaCoreException("langCode 参数不能为空");
		}
		if (StringUtils.isBlank(key)) {
			throw new BanaCoreException("Exceptinon key 不能为空");
		}

		Properties properties = (Properties) exceptionProperties.get(langCode.getLangCode());
		if (properties == null) {
			throw new BanaCoreException("异常加载时，没有找到langCode 为  " + langCode.getLangCode() + " 的消息文件");
		}

		String msgTemplate = properties.getProperty(key);

		if (StringUtils.isBlank(msgTemplate))
			throw new BanaCoreException("异常加载时，无法找到langCode 为  " + langCode.getLangCode() + " key值为 " + key + " 的消息模板");
		try {
			String message = StringUtils.replaceAllObject(new String(msgTemplate.getBytes("ISO8859-1"), encoding), context);
			return message;
		} catch (UnsupportedEncodingException e) {
			throw new BanaCoreException(e);
		}

	}

	public static String getMessage(String key, Map<String, Object> context) {
		return getMessage(key, context, LangCode.中文);
	}

	public static String getMessage(String key, LangCode langCode) {
		return getMessage(key, new HashMap<String, Object>(), langCode);
	}

	public static String getMessage(String key) {
		return getMessage(key, new HashMap<String, Object>(), LangCode.中文);
	}

	public static void setEncoding(String encoding) {
		ExceptionProperties.encoding = encoding;
	}
}