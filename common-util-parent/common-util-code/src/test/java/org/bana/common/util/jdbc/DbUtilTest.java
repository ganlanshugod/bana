/**
* @Company 艾美伴行   
* @Title: DbUtilTest.java 
* @Package org.bana.common.util.jdbc 
* @author liuwenjie   
* @date 2016-12-14 下午8:37:27 
* @version V1.0   
*/ 
package org.bana.common.util.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.code.CodeGenerator;
import org.bana.common.util.code.dao.mybatis.mysql.MybatisGeneratorConfig4Mysql;
import org.bana.common.util.code.impl.GeneratorOptions;
import org.bana.common.util.code.impl.GeneratorOptions.CoverResourceFile;
import org.bana.common.util.code.impl.SimpleCodeGenerator;
import org.bana.common.util.code.jpa.mysql.JpaGeneratorConfig4Mysql;
import org.junit.Test;
import org.bana.common.util.code.impl.CodeTemplateConfig;

/** 
 * @ClassName: DbUtilTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
public class DbUtilTest {

	@Test
	public void test() throws SQLException {
		Connection connection = DbUtil.getConnection();
		connection.getAutoCommit();
		DbUtil.closeConnection();
	}
	
	private String baseProjectPath = "D:/workspace/ruantong_i3618/i3618/i3618-biz";
	private String basePackage = "com/jbinfo/i3618";
	private String userName = "TEST";
	boolean generatorEntity;
	boolean generatorRepository;

	@Test
//	@Ignore
	public void generatorCode(){
		Map<String,Object> config = new HashMap<String,Object>();
		//是否生成entity
		generatorEntity = true;
		//是否生成repository
		generatorRepository = true;
		
		//数据结构配置
		config.put("database", "oasis");//数据库名称
		config.put("table", "tb_simple");//表名
		config.put("module", "simple");//模块名称
		
		//mybatis生成内容配置
		config.put("functionPackage", false);//是否将function属性生成一层目录,模式是true
//		config.put("functionPath", false);//设置生成配置文件的文件夹，是否包含function。默认是false，即不包含function路径
//		config.put("includeCommonDao", true);//是否生成CommonDao，第一次生成时使用它，之后就不要使用这个参数
		config.put("defaultOnly", true); //是否只生成默认的Domain类和CommonMapper
		
		if(generatorEntity || generatorRepository){
			generatorEntity(config);
		}

	}
	
	private void generatorEntity(Map<String, Object> config){
		String database = (String)config.get("database");
		String table = (String)config.get("table");
		JpaGeneratorConfig4Mysql jpaGeneratorConfig = new JpaGeneratorConfig4Mysql(table, database,true);
		
		jpaGeneratorConfig.setProjectBasePath(baseProjectPath);
		jpaGeneratorConfig.setBasePackage(basePackage);
		jpaGeneratorConfig.setModule((String)config.get("module"));
//		mybatisGeneratorConfig.setFunctionPacage("monitor");
		if(StringUtils.isNotBlank((String)config.get("function"))){
			jpaGeneratorConfig.setFunction((String)config.get("function"));
		}
		
		//设置生成代码中注释的用户名
		jpaGeneratorConfig.setUserName(userName);
		//设置是否将功能名称作为生成功能代码的报名,默认是true.即会将functions作为包名的一部分,如果function为null则默认会是false
		if(BooleanUtils.isFalse((Boolean)config.get("functionPackage"))){
			jpaGeneratorConfig.setFunctionPackage(false);
		}
		//设置生成配置文件的文件夹，是否包含function。默认是false，即不包含function路径
		if(BooleanUtils.isTrue((Boolean)config.get("functionPath"))){
			jpaGeneratorConfig.setFunctionPath(true);
		}
		//生成器的调用
		//生成时的选项，是否覆盖已有的文件，请慎用
		GeneratorOptions option = new GeneratorOptions();
		option.setCoverCodeFile(true);
		option.setCoverResourceFile(CoverResourceFile.覆盖);
		jpaGeneratorConfig.setGeneratorOptions(option);
		
		List<CodeTemplateConfig> codeList = new ArrayList<CodeTemplateConfig>();
		if(generatorEntity){
			codeList.add(JpaGeneratorConfig4Mysql.default_entity);
		}
		if(generatorRepository){
			codeList.add(JpaGeneratorConfig4Mysql.default_repository);
		}
		
		jpaGeneratorConfig.setCodeVelocities(codeList);
		
		
//		不生成代码文件
//		mybatisGeneratorConfig.setCodeVelocities(null);
//		配置制定的配置文件模板
		CodeGenerator codeGenerator = new SimpleCodeGenerator();
		codeGenerator.setGeneratorConfig(jpaGeneratorConfig);
		codeGenerator.generate();
	}

}
