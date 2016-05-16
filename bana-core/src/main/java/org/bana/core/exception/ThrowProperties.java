package org.bana.core.exception;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ThrowProperties implements Serializable{
	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 155293397291419150L;
	private String key;
	private ThrowType throwType;
	private Map<String, Object> context;

	private ThrowProperties(String key, Object[] context) {
		this.key = key;
		this.context = new HashMap<String,Object>();
		if ((context != null) && (context.length > 1)){
			for (int i = 0; i < context.length; i += 2){
				if (i + 1 < context.length){
					this.context.put(String.valueOf(context[i]), context[(i + 1)]);
				}
			}
		}
			
	}

	public String getMessage(LangCode langCode) {
		if (langCode == null) {
			Locale locale = Locale.getDefault();
			langCode = LangCode.getInstance(locale);
		}
		return ExceptionProperties.getMessage(this.key, this.context, langCode);
	}

	public String getMessage() {
		return getMessage(null);
	}

	public String getErrorCode() {
		return this.throwType.getTypeCode() + "-" + this.key;
	}

	public static ThrowProperties getInstance(String key, Object... context) {
		return new ThrowProperties(key, context);
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public ThrowType getThrowType() {
		return this.throwType;
	}

	public void setThrowType(ThrowType throwType) {
		this.throwType = throwType;
	}

	public static enum ThrowType {
		业务异常("JB"), 系统异常("JS"), 运行异常("JR"), 特殊异常("JSPE"), 未知异常("JE");

		private String typeCode;

		private ThrowType(String typeCode) {
			this.typeCode = typeCode;
		}

		public String getTypeCode() {
			return this.typeCode;
		}
	}
}