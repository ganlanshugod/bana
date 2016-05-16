/**
* @Company 青鸟软通   
* @Title: WsGeneratorConfigWithSpring.java 
* @Package org.bana.common.util.code.webservice 
* @author Liu Wenjie   
* @date 2015-1-16 下午1:38:29 
* @version V1.0   
*/ 
package org.bana.common.util.code.webservice;

import java.util.ArrayList;

import org.apache.velocity.VelocityContext;
import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.code.impl.CodeTemplateConfig;

/** 
 * @ClassName: WsGeneratorConfigWithSpring 
 * @Description: 带spring 访问webservice的生成器方法
 *  
 */
public abstract class WsGeneratorConfigWithSpring extends WSGeneratorCofing{

	/** 
	* @Fields wsServiceName : 配置指定生成的客户端调用的spring配置文件需要的类名，也可以在生成后手动指定
	*/ 
	private String wsServiceName;
	
	
	/** 
	 * <p>Description: </p> 
	 * @author Liu Wenjie   
	 * @date 2015-1-16 下午1:34:45  
	 */
	public WsGeneratorConfigWithSpring() {
		initWsTemplates();
	}
	
	/** 
	* @Description: 初始化spring配置文件的生成
	* @author Liu Wenjie   
	* @date 2015-1-16 下午1:35:04   
	*/ 
	private void initWsTemplates() {
		if(this.resourceVelocities == null){
			this.resourceVelocities = new ArrayList<CodeTemplateConfig>();
		}
		this.resourceVelocities.add(getDefaultWsFactoryConfig());
	}
	
	/** 
	* @Description: 获取默认的spring配置模板
	* @author Liu Wenjie   
	* @date 2015-1-16 下午5:19:23 
	* @return  
	*/ 
	protected abstract CodeTemplateConfig getDefaultWsFactoryConfig();
	
	
	@Override
	protected void doInitTemplateContext(VelocityContext context) {
		if(!StringUtils.isBlank(this.wsServiceName)){
			context.put("wsServiceName", this.wsServiceName);
		}
		context.put("packageName", getBaseWsPackage());
	}
	
	/** 
	* @Description: 获取基础的ws包名
	* @author Liu Wenjie   
	* @date 2014-11-10 上午10:16:33   
	*/ 
	protected String getBaseWsPackage(){
		String unionAsFilePath = StringUtils.unionAsFilePath(this.basePackage,DEFAULT_WS_MODULE,this.module.toLowerCase(),DEFAULT_WS_CLIENT_PACKAGE,this.function.toLowerCase());
		return filePathToPackageName(unionAsFilePath);
	}
	
	/*=======================getter and setter =================*/
	
	/**
	 * @Description: 属性 wsServiceName 的get方法 
	 * @return wsServiceName
	 */
	public String getWsServiceName() {
		return wsServiceName;
	}

	/**
	 * @Description: 属性 wsServiceName 的set方法 
	 * @param wsServiceName 
	 */
	public void setWsServiceName(String wsServiceName) {
		this.wsServiceName = wsServiceName;
	}
}
