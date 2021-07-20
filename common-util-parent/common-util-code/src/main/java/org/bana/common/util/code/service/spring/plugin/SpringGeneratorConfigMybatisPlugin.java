/**
* @Company weipu   
* @Title: SpringGeneratorConfigUseMybatisPlugin.java 
* @Package org.bana.common.util.code.service.spring.plugin 
* @author Liu Wenjie   
* @date 2014-11-3 下午7:09:40 
* @version V1.0   
*/ 
package org.bana.common.util.code.service.spring.plugin;

import java.util.ArrayList;

import org.apache.velocity.VelocityContext;
import org.bana.common.util.code.impl.CodeTemplateConfig;
import org.bana.common.util.code.impl.CodeTemplateConfig.GeneratorFileType;
import org.bana.common.util.code.service.spring.SpringGeneratorConfig;

/** 
 * @ClassName: SpringGeneratorConfigUseMybatisPlugin 
 * @Description: 使用Mybatis关联生成的spring 代码生成器实现类 
 *  
 */
public class SpringGeneratorConfigMybatisPlugin extends SpringGeneratorConfig{

	/** 
	* @Fields moduleArr : 关联使用的Dao模块集合，使用的功能名称
	*/ 
	protected String[] daoArr;
	protected String baseDaoName = "baseDAO";
	
	
	private static CodeTemplateConfig default_Service = new CodeTemplateConfig(GeneratorFileType.Service, "", "code/service/spring/mybatis_default/defaultService.vm");
	private static CodeTemplateConfig default_Service_Impl = new CodeTemplateConfig(GeneratorFileType.ServiceImpl, "", "code/service/spring/mybatis_default/defaultServiceImpl.vm");
	private static CodeTemplateConfig default_Spring_Xml = new CodeTemplateConfig(GeneratorFileType.Spring_xml, "", "code/service/spring/mybatis_default/defaultXmlConfig.vm");
	
	public SpringGeneratorConfigMybatisPlugin(){
		initSpringTemplate();
	}
	
	/** 
	* @Description: 舒适化spring的模板文件
	* @author Liu Wenjie   
	* @date 2014-11-3 下午7:31:57   
	*/ 
	private void initSpringTemplate() {
		if(this.codeVelocities == null){
			this.codeVelocities = new ArrayList<CodeTemplateConfig>();
		}
		this.codeVelocities.add(default_Service);
		this.codeVelocities.add(default_Service_Impl);
		if(this.resourceVelocities == null){
			this.resourceVelocities = new ArrayList<CodeTemplateConfig>();
		}
		this.resourceVelocities.add(default_Spring_Xml);
	}
	
	
	/**
	* <p>Description: 加载模板中用到的上下文</p> 
	* @author Liu Wenjie   
	* @date 2014-11-3 下午8:52:53 
	* @param context 
	* @see org.bana.common.util.code.service.spring.SpringGeneratorConfig#doInitTemplateContext(org.apache.velocity.VelocityContext) 
	*/ 
	protected void doInitTemplateContext(VelocityContext context) {
		//保证父级中的参数加载
		super.doInitTemplateContext(context);
		
	}
	
	/*================getter and setter ================*/

	/**
	 * @Description: 属性 daoArr 的get方法 
	 * @return daoArr
	 */
	public String[] getDaoArr() {
		return daoArr;
	}

	/**
	 * @Description: 属性 daoArr 的set方法 
	 * @param daoArr 
	 */
	public void setDaoArr(String[] daoArr) {
		this.daoArr = daoArr;
	}

	/**
	 * @Description: 属性 baseDaoName 的get方法 
	 * @return baseDaoName
	 */
	public String getBaseDaoName() {
		return baseDaoName;
	}

	/**
	 * @Description: 属性 baseDaoName 的set方法 
	 * @param baseDaoName 
	 */
	public void setBaseDaoName(String baseDaoName) {
		this.baseDaoName = baseDaoName;
	}
	
	
}
