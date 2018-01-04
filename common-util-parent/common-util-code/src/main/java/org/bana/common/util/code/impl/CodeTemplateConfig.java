/**
* @Company 青鸟软通   
* @Title: CodeTemplateConfig.java 
* @Package org.bana.common.util.code.impl 
* @author Liu Wenjie   
* @date 2014-10-27 下午4:59:31 
* @version V1.0   
*/ 
package org.bana.common.util.code.impl;

import org.apache.commons.lang.StringUtils;

/** 
 * @ClassName: CodeTemplateConfig 
 * @Description: 一个代码文件生成的配置
 *  
 */
public class CodeTemplateConfig {
	
	public static final String TYPE_CODE = "code";
	public static final String TYPE_CODE_COMMON = "common_code";
	public static final String TYPE_RESOURCE = "resource";

	/** 
	* @Fields generatorFileType : 生成文件的类型
	*/ 
	private GeneratorFileType generatorFileType;
	
	/** 
	* @Fields classNameTemplate : 生成文件的名称的模板
	*/ 
	private String classNameTemplate; 
	
	/** 
	* @Fields templateFilePath : 生成文件的模板文件路径
	*/ 
	private String templateFilePath;
	
	public CodeTemplateConfig(){
		super();
	}
	/** 
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2014-11-2 下午11:22:35 
	* @param generatorFileType
	* @param classNameTemplate
	* @param templateFilePath 
	*/ 
	public CodeTemplateConfig(GeneratorFileType generatorFileType,
			String classNameTemplate, String templateFilePath) {
		super();
		this.generatorFileType = generatorFileType;
		this.classNameTemplate = classNameTemplate;
		this.templateFilePath = templateFilePath;
	}

	/** 
	* @ClassName: GeneratorFileType 
	* @Description: 生成文件的类型，决定到底产生的包名是什么
	*  
	*/ 
	public enum GeneratorFileType{
		Service("service","${Function}Service",".java",TYPE_CODE),
		ServiceImpl("service/impl","${Function}ServiceImpl",".java",TYPE_CODE),
		Dao("dao","${Function}DAO",".java",TYPE_CODE),
		Dao_Common("/common/dao","CommonDAO",".java",TYPE_CODE_COMMON),
		Action("action","${Function}Action",".java",TYPE_CODE),
		Domain("domain","${Function}Domain",".java",TYPE_CODE),
		DomainColumn("domain","${Function}Column",".java",TYPE_CODE),
		PKDomain("domain/pk","${Function}DomainPK",".java",TYPE_CODE),
		
		Spring_xml("/spring/${module}","spring-${function}",".xml",TYPE_RESOURCE),
		Mybatis_Common_xml("/mybatis/mappers/${module}","${Function}4CommonMapper",".xml",TYPE_RESOURCE),
		Mybatis_xml("/mybatis/mappers/${module}","${Function}Mapper",".xml",TYPE_RESOURCE),
		
		WS_Spring_Factory_xml("/spring_ws/${module}","spring-${function}",".xml",TYPE_RESOURCE),
		
		//javaconfig方式的jpa实体和接口
		Entity("entity","${Function}Entity",".java",TYPE_CODE),
		PKEntity("entity","${Function}PKEntity",".java",TYPE_CODE),
		Repository("dao","${Function}Repository",".java",TYPE_CODE),
		Config_Dao("dao","${Function}Mapper",".java",TYPE_CODE),
		Config_Mybatis_xml("dao","${Function}Mapper",".xml",TYPE_CODE),
		Config_Mybatis_Common_xml("dao","${Function}CommonMapper",".xml",TYPE_CODE),
		Config_Service("service","I${Function}Service",".java",TYPE_CODE),
		Config_ServiceImpl("service/impl","${Function}ServiceImpl",".java",TYPE_CODE),
		Config_Controller("controller","${Function}Controller",".java",TYPE_CODE),
		Model("model","${Function}Model",".java",TYPE_CODE);
		
		
		private String packageName;//包名
		private String classConfig;//类名
		private String classExtName;//扩展名
		private String type;
		private GeneratorFileType(String packageName,String classConfig,String classExtName,String type){
			this.packageName = packageName;
			this.classConfig = classConfig;
			this.classExtName = classExtName;
			this.type = type;
		}
		/**
		 * @Description: 属性 packageName 的get方法 
		 * @return packageName
		 */
		public String getPackageName(boolean isFunctionPath) {
			if(isFunctionPath && TYPE_RESOURCE.equalsIgnoreCase(this.type)){
				return this.packageName+"/${function}";
			}
			return this.packageName;
		}
		/**
		 * @Description: 属性 classConfig 的get方法 
		 * @return classConfig
		 */
		public String getClassConfig() {
			return classConfig;
		}
		/**
		 * @Description: 属性 classExtName 的get方法 
		 * @return classExtName
		 */
		public String getClassExtName() {
			return classExtName;
		}
		/**
		 * @Description: 属性 type 的get方法 
		 * @return type
		 */
		public String getType() {
			return type;
		}
		
	}
	
	
	/*==================getter and setter ================*/

	/**
	 * @Description: 属性 generatorFileType 的get方法 
	 * @return generatorFileType
	 */
	public GeneratorFileType getGeneratorFileType() {
		return generatorFileType;
	}

	/**
	 * @Description: 属性 generatorFileType 的set方法 
	 * @param generatorFileType 
	 */
	public void setGeneratorFileType(GeneratorFileType generatorFileType) {
		this.generatorFileType = generatorFileType;
	}

	/**
	 * @Description: 属性 classNameTemplate 的get方法 
	 * @return classNameTemplate
	 */
	public String getClassNameTemplate() {
		if(StringUtils.isBlank(classNameTemplate) && generatorFileType != null){
			return generatorFileType.getClassConfig();
		}
		return classNameTemplate;
	}

	/**
	 * @Description: 属性 classNameTemplate 的set方法 
	 * @param classNameTemplate 
	 */
	public void setClassNameTemplate(String classNameTemplate) {
		this.classNameTemplate = classNameTemplate;
	}

	/**
	 * @Description: 属性 templateFilePath 的get方法 
	 * @return templateFilePath
	 */
	public String getTemplateFilePath() {
		return templateFilePath;
	}

	/**
	 * @Description: 属性 templateFilePath 的set方法 
	 * @param templateFilePath 
	 */
	public void setTemplateFilePath(String templateFilePath) {
		this.templateFilePath = templateFilePath;
	}

	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2014-10-28 下午9:14:54 
	* @return 
	* @see java.lang.Object#toString() 
	*/ 
	@Override
	public String toString() {
		return "CodeTemplateConfig [generatorFileType=" + generatorFileType
				+ ", classNameTemplate=" + classNameTemplate
				+ ", templateFilePath=" + templateFilePath + "]";
	}
	
	
}
