/**
* @Company weipu   
* @Title: BaseGeneratorConfig.java 
* @Package org.bana.common.util.code.impl 
* @author Liu Wenjie   
* @date 2014-10-27 上午11:08:47 
* @version V1.0   
*/ 
package org.bana.common.util.code.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.bana.common.util.basic.DateUtil;
import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.code.GeneratorConfig;
import org.bana.common.util.code.impl.CodeTemplateConfig.GeneratorFileType;
import org.bana.common.util.exception.BanaUtilException;
import org.bana.common.util.velocity.SimpleVelocityEngineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: BaseGeneratorConfig 
 * @Description: 基础的配置文件问题，包含所有基本的配置问题 
 *  
 */
public abstract class BaseGeneratorConfig implements GeneratorConfig {
	
	private static Logger LOG = LoggerFactory.getLogger(BaseGeneratorConfig.class);
	/** 
	* @Fields projectBasePath : 项目的基础路径，根路径
	*/ 
	protected String projectBasePath;
	
	/** 
	* @Fields codePath : 生成的代码所在的基础路径问题。约定路径都以“/”开头
	*/ 
	protected String codePath;
	
	/** 
	* @Fields default_code_path : 默认的代码生成基于项目路径的基础路径
	*/ 
	protected static String default_code_path = "/src/main/java";
	
	/** 
	* @Fields resourcesPath : 生成代码对应的资源基础路径，约定路径都以“/”开头
	*/ 
	protected String resourcesPath;
	
	/** 
	* @Fields default_resources_path : 默认的生成资源的基础路径，基于项目根路径
	*/ 
	protected static String default_resources_path =  "/src/main/resources";
	
	/** 
	* @Fields basePackage : 项目记录包路径。类似于com.haier 或 org.jbinfo等
	*/ 
	protected String basePackage;
	
	/** 
	* @Fields modules : 生成代码对应所属的模块名称 。例如系统管理（system），订单管理（order）
	*/ 
	protected String module;
	
	/** 
	* @Fields function : 生成代码对应所属的模块名称，例如系统管理-用户管理（user），是对modules的扩充，如果没有指定，默认继续使用module的名称
	*/ 
	protected String function;
	
	/** 
	* @Fields functionPacage : 功能级的function 的包名，如果没有，默认会使用function
	*/ 
	protected String functionPacage;
	
	/** 
	* @Fields isFunctionPackage : 功能是否作为一个单独的包名存在
	*/ 
	protected boolean isFunctionPackage = true;
	
	/** 
	* @Fields isFunctionPath : 是否将功能作为配置文件的一个子目录,创建文件夹
	*/ 
	protected boolean isFunctionPath = false;
	
	/** 
	* @Fields codeVelocities : 代码的模板位置，路径都是项目中classpath下的目录
	*/ 
	protected List<CodeTemplateConfig> codeVelocities;
	
	/** 
	* @Fields resourceVelocities : 资源的模板位置，路径都是项目中classpath下的目录，
	*/ 
	protected List<CodeTemplateConfig> resourceVelocities;
	
	/** 
	* @Fields generatorOptions : 生成文件的配置方法
	*/ 
	protected GeneratorOptions generatorOptions;
	
	/** 
	* @Fields userName : 当前执行的用户名
	*/ 
	private String userName;
	
	
	
	/** 
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2014-10-27 下午12:30:48  
	*/ 
	public BaseGeneratorConfig() {
		super();
		this.codePath = default_code_path;
		this.resourcesPath = default_resources_path;
	}
	
	/**
	* <p>Description: 获取准备生成的代码文件和内容</p> 
	* @author Liu Wenjie   
	* @date 2014-10-27 下午12:35:23 
	* @return 
	* @see org.bana.common.util.code.GeneratorConfig#getCodeFiles() 
	*/ 
	public List<FileConfig> getCodeFiles(){
		//检查参数的有效性
		checkParams();
		//构建代码文件目录
		if(this.codeVelocities == null || this.codeVelocities.isEmpty()){
			return new ArrayList<FileConfig>();
		}
		
		List<FileConfig> codeFiles = new ArrayList<FileConfig>();
		for (CodeTemplateConfig templateFile : this.codeVelocities) {
			FileConfig fileConfig = new FileConfig();
			//模板文件信息
			Template template = SimpleVelocityEngineFactory.getTemplate(templateFile.getTemplateFilePath());
			VelocityContext context = initTemplateContext(templateFile);
			StringWriter writer = new StringWriter();  
			try {
				template.merge(context, writer);
				String fileContent = writer.toString();
				fileConfig.setFileContent(fileContent);
			} catch (Exception e) {
				throw new BanaUtilException("获取模板对应的文件 " + templateFile + "内容时出现错误",e);
			} 
			//构建文件路径
			String fileAbsolutePath = this.getFileAbsolutePath(templateFile);
			fileConfig.setFileAbsolutePath(fileAbsolutePath);
			
			codeFiles.add(fileConfig);
		}
		
		return codeFiles;
	}
	
	/** 
	* @Description: 获取资源文件的文件生成配置集合
	* @author Liu Wenjie   
	* @date 2014-10-29 上午9:37:41 
	* @return  
	*/ 
	public List<FileConfig> getResourcesFiles(){
		//检查参数的有效性
		checkParams();
		//构建代码文件目录
		if(this.resourceVelocities == null || this.resourceVelocities.isEmpty()){
			return new ArrayList<FileConfig>();
		}
		
		List<FileConfig> resourceFiles = new ArrayList<FileConfig>();
		for (CodeTemplateConfig templateFile : this.resourceVelocities) {
			FileConfig fileConfig = new FileConfig();
			//模板文件信息
			Template template = SimpleVelocityEngineFactory.getTemplate(templateFile.getTemplateFilePath());
			VelocityContext context = initTemplateContext(templateFile);
			StringWriter writer = new StringWriter();  
			try {
				template.merge(context, writer);
				String fileContent = writer.toString();
				fileConfig.setFileContent(fileContent);
			} catch (Exception e) {
				throw new BanaUtilException("获取模板对应的文件 " + templateFile + "内容时出现错误",e);
			} 
			//构建文件路径
			String fileAbsolutePath = this.getFileAbsolutePath(templateFile);
			fileConfig.setFileAbsolutePath(fileAbsolutePath);
			
			resourceFiles.add(fileConfig);
		}
		return resourceFiles;
		
	}
	
	/** 
	* @Description: 获取文件的保存路径
	* @author Liu Wenjie   
	* @date 2014-10-28 下午8:50:28 
	* @return  
	*/ 
	protected String getFileAbsolutePath(CodeTemplateConfig templateFile){
		
		GeneratorFileType generatorFileType = templateFile.getGeneratorFileType();
		if(generatorFileType == null){
			throw new BanaUtilException("没有指定生成文件对应的模板类型,无法确定生成到哪个文件中" + templateFile);
		}
		String baseCodePath = "";
		if(CodeTemplateConfig.TYPE_RESOURCE.equals(generatorFileType.getType())){
			baseCodePath = this.getBaseResourcePath();
		}else{
			baseCodePath = this.getBaseCodePath(generatorFileType);
		}
		String classTemplate = templateFile.getClassNameTemplate();
		
		Map<String,String> context = new HashMap<String,String>();
		context.put("function", this.function);
		context.put("Function", StringUtils.upcaseFirstChar(this.function));
		context.put("functionPackage", this.functionPacage);
		context.put("module", this.module);
		return StringUtils.replaceAll(StringUtils.unionAsFilePath(baseCodePath,generatorFileType.getPackageName(this.isFunctionPath),classTemplate + generatorFileType.getClassExtName()),context);
	}
	
	/** 
	* @Description: 获取资源文件的方法
	* @author Liu Wenjie   
	* @date 2014-10-29 上午10:06:27   
	*/ 
	private String getBaseResourcePath() {
		if(StringUtils.isBlank(this.projectBasePath,this.resourcesPath)){
			throw new BanaUtilException("generatorConfig  exist blank attribute ..." + this.toString());
		}
		String basePath = StringUtils.unionAsFilePath(this.projectBasePath,this.resourcesPath);
		return basePath;
	}

	/** 
	* @Description: 获取项目代码的路径
	* @author Liu Wenjie   
	 * @param generatorFileType 
	* @date 2014-10-28 下午7:24:28 
	* @return  
	*/ 
	private String getBaseCodePath(GeneratorFileType generatorFileType){
		if(StringUtils.isBlank(this.projectBasePath,this.codePath)){
			throw new BanaUtilException("generatorConfig  exist blank attribute ..." + this.toString());
		}
		
		//项目跟路径
		String basePath = StringUtils.unionAsFilePath(this.projectBasePath,this.codePath,getCodeBasePackagePath(generatorFileType));
		return basePath;
	}
	
	/** 
	* @Description: 获取code的包的路径
	* @author Liu Wenjie   
	 * @param generatorFileType 
	* @date 2014-10-29 下午1:24:43 
	* @return  
	*/ 
	protected String getCodeBasePackagePath(GeneratorFileType generatorFileType){
		if(StringUtils.isBlank(this.basePackage,this.module)){
			throw new BanaUtilException("generatorConfig  exist blank attribute ..." + this.toString());
		}
		
		//项目跟路径
		if(CodeTemplateConfig.TYPE_CODE_COMMON.equals(generatorFileType.getType())){
			return StringUtils.unionAsFilePath(this.basePackage);
		}
		String basePath = StringUtils.unionAsFilePath(this.basePackage,this.module);
		if(this.isFunctionPackage){
			if(!StringUtils.isBlank(this.functionPacage)){
				basePath = StringUtils.unionAsFilePath(basePath,this.functionPacage);
			}
		}
		return basePath;
	}
	
	/** 
	* @Description: 初始化各配置特有的上下文参数
	* @author Liu Wenjie   
	* @date 2014-10-27 下午3:37:28   
	*/ 
	protected abstract void doInitTemplateContext(VelocityContext context);

	
	/** 
	* @Description: 初始化模板需要的上下文信息，主要包含一些公共参数、代码参数等
	* @author Liu Wenjie   
	* @date 2014-10-27 下午3:22:21   
	*/ 
	private VelocityContext initTemplateContext(CodeTemplateConfig templateFile){
		VelocityContext context = new VelocityContext();
		//构建基础注释信息
		//当前的配置对象
		context.put("this", this);
		//传入工具类
		context.put("StringUtils", StringUtils.class);
		
		GeneratorFileType generatorFileType = templateFile.getGeneratorFileType();
		context.put("Function", StringUtils.upcaseFirstChar(this.function));
		context.put("function", this.function);
		context.put("serialVersionUID", "-" + System.currentTimeMillis() + "L");
		context.put("packageName",getCodePackageName(generatorFileType));
		//初始化所有类型用到的packageName信息
		initGeneratorTypeContext(context);
		//当前的环境信息
		initEnvProperty(context);
		
		doInitTemplateContext(context);
		return context;
	}
	
	/** 
	* @Description: 加载各种类型的基础数据
	* @author Liu Wenjie   
	* @date 2014-10-30 下午10:42:19   
	*/ 
	private void initGeneratorTypeContext(VelocityContext context){
		GeneratorFileType[] generatorFileTypes = GeneratorFileType.values();
		for (GeneratorFileType generatorFileType : generatorFileTypes) {
			//代码保存代码的报名
			if(generatorFileType != null){
				String packageName = getCodePackageName(generatorFileType);
				context.put(generatorFileType.toString() + "_packageName", packageName);
			}
		}
	}
	
	
	/** 
	* @Description: 获取指定类型的代码的报名
	* @author Liu Wenjie   
	* @date 2014-10-30 下午10:34:24 
	* @param generatorFileType
	* @return  
	*/ 
	protected String getCodePackageName(GeneratorFileType generatorFileType){
		Map<String,String> contextTemp = new HashMap<String,String>();
		contextTemp.put("function", StringUtils.upcaseFirstChar(this.function));
		contextTemp.put("module", StringUtils.upcaseFirstChar(this.module));
		String packagePath = StringUtils.unionAsFilePath(getCodeBasePackagePath(generatorFileType),generatorFileType.getPackageName(this.isFunctionPath));
		String packageName = filePathToPackageName(packagePath);
		return StringUtils.replaceAll(packageName, contextTemp);
	}
	
	/** 
	* @Description: filePath 转化为包路径
	* @author Liu Wenjie   
	* @date 2014-10-29 下午1:28:25 
	* @param filePath
	* @return  
	*/ 
	protected String filePathToPackageName(String filePath){
		if(StringUtils.isBlank(filePath)){
			return filePath;
		}
		return filePath.replaceAll("/", "\\.");
	}
	/** 
	* @Description: 初始化环境变量
	* @author Liu Wenjie   
	* @date 2014-10-28 下午9:42:23   
	*/ 
	private void initEnvProperty(VelocityContext context){
		//环境变量，用户和日期
		Map<String, String> getenv = System.getenv();
		context.put("user", StringUtils.isBlank(this.userName)?getenv.get("USERNAME"):this.userName);
		context.put("date", DateUtil.getNowString());
		//增加环境变量
		context.put("ENV",getenv);
	}
	
	
	
	/** 
	* @Description: 检查目前所有的代码参数，是否符合要求，
	* @author Liu Wenjie   
	* @date 2014-10-27 下午12:39:59   
	*/ 
	private void checkParams() {
		if(StringUtils.isBlank(this.projectBasePath)){
			throw new BanaUtilException("projectBasePath is blank......." + this.projectBasePath);
		}
		this.projectBasePath = this.projectBasePath.toLowerCase();
		if(StringUtils.isBlank(this.basePackage)){
			throw new BanaUtilException("base package is blank......." + this.basePackage);
		}
		this.basePackage = this.basePackage.toLowerCase();
		if(StringUtils.isBlank(this.codePath)){
			throw new BanaUtilException("code Path is blank......." + this.codePath);
		}
		this.codePath = this.codePath.toLowerCase();
		if(StringUtils.isBlank(this.resourcesPath)){
			throw new BanaUtilException("resources Path is blank......." + this.codePath);
		}
		
		if(StringUtils.isBlank(this.module)){
			throw new BanaUtilException("moudle is blank......." + this.codePath);
		}
		this.module = this.module.toLowerCase();
		
		if(StringUtils.isBlank(this.function)){
			LOG.warn("param this.function is blank , will use modules to repleace className ,but no function package will create");
			this.function = this.module;
			this.isFunctionPackage = false;
			this.isFunctionPath = false;
		}
		
		if(StringUtils.isBlank(this.functionPacage)){
			this.functionPacage = this.function;
		}
		this.functionPacage = this.functionPacage.toLowerCase();
	}
	
	
	
	/*=================getter and setter ====================*/
	/**
	 * @Description: 属性 projectBasePath 的get方法 
	 * @return projectBasePath
	 */
	public String getProjectBasePath() {
		return projectBasePath;
	}

	/**
	 * @Description: 属性 projectBasePath 的set方法 
	 * @param projectBasePath 
	 */
	public void setProjectBasePath(String projectBasePath) {
		this.projectBasePath = projectBasePath;
	}

	/**
	 * @Description: 属性 codePath 的get方法 
	 * @return codePath
	 */
	public String getCodePath() {
		return codePath;
	}

	/**
	 * @Description: 属性 codePath 的set方法 
	 * @param codePath 
	 */
	public void setCodePath(String codePath) {
		this.codePath = codePath;
	}

	/**
	 * @Description: 属性 resourcesPath 的get方法 
	 * @return resourcesPath
	 */
	public String getResourcesPath() {
		return resourcesPath;
	}

	/**
	 * @Description: 属性 resourcesPath 的set方法 
	 * @param resourcesPath 
	 */
	public void setResourcesPath(String resourcesPath) {
		this.resourcesPath = resourcesPath;
	}
	

	/**
	 * @Description: 属性 basePackage 的get方法 
	 * @return basePackage
	 */
	public String getBasePackage() {
		return basePackage;
	}

	/**
	 * @Description: 属性 basePackage 的set方法 
	 * @param basePackage 
	 */
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	/**
	 * @Description: 属性 module 的get方法 
	 * @return module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @Description: 属性 module 的set方法 
	 * @param module 
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @Description: 属性 function 的get方法 
	 * @return function
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * @Description: 属性 function 的set方法 
	 * @param function 
	 */
	public void setFunction(String function) {
		this.function = function;
	}

	/**
	 * @Description: 属性 codeVelocities 的get方法 
	 * @return codeVelocities
	 */
	public List<CodeTemplateConfig> getCodeVelocities() {
		return codeVelocities;
	}

	/**
	 * @Description: 属性 codeVelocities 的set方法 
	 * @param codeVelocities 
	 */
	public void setCodeVelocities(List<CodeTemplateConfig> codeVelocities) {
		this.codeVelocities = codeVelocities;
	}

	/**
	 * @Description: 属性 resourceVelocities 的get方法 
	 * @return resourceVelocities
	 */
	public List<CodeTemplateConfig> getResourceVelocities() {
		return resourceVelocities;
	}

	/**
	 * @Description: 属性 resourceVelocities 的set方法 
	 * @param resourceVelocities 
	 */
	public void setResourceVelocities(List<CodeTemplateConfig> resourceVelocities) {
		this.resourceVelocities = resourceVelocities;
	}
	
	/**
	 * @Description: 属性 isFunctionPackage 的get方法 
	 * @return isFunctionPackage
	 */
	public boolean isFunctionPackage() {
		return isFunctionPackage;
	}

	/**
	 * @Description: 属性 isFunctionPackage 的set方法 
	 * @param isFunctionPackage 
	 */
	public void setFunctionPackage(boolean isFunctionPackage) {
		this.isFunctionPackage = isFunctionPackage;
	}
	
	/**
	 * @Description: 属性 generatorOptions 的get方法 
	 * @return generatorOptions
	 */
	public GeneratorOptions getGeneratorOptions() {
		return generatorOptions;
	}

	/**
	 * @Description: 属性 generatorOptions 的set方法 
	 * @param generatorOptions 
	 */
	public void setGeneratorOptions(GeneratorOptions generatorOptions) {
		this.generatorOptions = generatorOptions;
	}

	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2014-10-27 下午1:30:08 
	* @return 
	* @see java.lang.Object#toString() 
	*/ 
	@Override
	public String toString() {
		return "BaseGeneratorConfig [projectBasePath=" + projectBasePath
				+ ", codePath=" + codePath + ", resourcesPath=" + resourcesPath
				+ ", basePackage=" + basePackage + ", module=" + module
				+ ", function=" + function + "]";
	}

	/**
	 * @Description: 属性 userName 的get方法 
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @Description: 属性 userName 的set方法 
	 * @param userName 
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @Description: 属性 isFunctionPath 的get方法 
	 * @return isFunctionPath
	 */
	public boolean isFunctionPath() {
		return isFunctionPath;
	}

	/**
	 * @Description: 属性 isFunctionPath 的set方法 
	 * @param isFunctionPath 
	 */
	public void setFunctionPath(boolean isFunctionPath) {
		this.isFunctionPath = isFunctionPath;
	}

	/**
	 * @Description: 属性 functionPacage 的get方法 
	 * @return functionPacage
	 */
	public String getFunctionPacage() {
		return functionPacage;
	}

	/**
	 * @Description: 属性 functionPacage 的set方法 
	 * @param functionPacage 
	 */
	public void setFunctionPacage(String functionPacage) {
		this.functionPacage = functionPacage;
	}
	
}
