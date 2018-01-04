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
import org.bana.common.util.code.dao.config.mysql.JpaGeneratorConfig4Mysql;
import org.bana.common.util.code.impl.CodeTemplateConfig;
import org.bana.common.util.code.impl.GeneratorOptions;
import org.bana.common.util.code.impl.SimpleCodeGenerator;
import org.junit.Ignore;
import org.junit.Test;

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
	
	//项目路径 需要修改为本地绝对路径
    private String baseProjectPath = "D:/c_work/ruantong/oasis-system/oasis-system-core"; 
    //所在包 根据实际情况修改
    private String basePackage = "cn/jbinfo/oasis/system/core";
    //生成代码中注释的用户名 改为提交人姓名
    private String userName = "Huang Nana"; 
    boolean generatorEntity;
    boolean generatorRepository;
    boolean generatorMapper;
    boolean generatorService;
    boolean generatorController;
    boolean generatorModel;
    boolean generatorCommonMapper;
    boolean isCover;

    @Test
    @Ignore
    public void generatorCode(){
        Map<String,Object> config = new HashMap<String,Object>();
        //是否生成entity
        generatorEntity = true;
        //是否生成repository
        generatorRepository = true;
        //是否生成dao和mapper
        generatorMapper = true;    
        //是否生成service
        generatorService = true;
        //是否生成controller
        generatorController = true;
        //是否生成全字段的业务实体
        generatorModel = true;
        //是否生成commonmapper,仅做参考返回resultMap使用
        generatorCommonMapper = false;
        //是否覆盖文件 true为覆盖，谨慎使用
        isCover = false;

        //数据结构配置
        config.put("database", "oasis_sys");//数据库名称
        config.put("table", "t_bi_menu");//表名
        config.put("module", "test");//模块名称
//        config.put("function", "function1"); //功能级别的名称，没有则不需要设置
//        config.put("functionPackage", false);//是否将function属性生成一层目录,模式是true

        //entity继承的父类，根据实际情况设置；没有需要继承的则不使用这个参数
//        config.put("baseEntity", AbstractEntity.class.getName());
        //repository继承的父类，根据实际情况设置；没有需要继承的则不使用这个参数
//        config.put("baseRepository", OasisRepository.class.getName());


        //执行
                generatorEntity(config);
    }

        //以下代码通常情况不需要修改
    private void generatorEntity(Map<String, Object> config){
        String database = (String)config.get("database");
        String table = (String)config.get("table");
        boolean hasBase = false;
        Map<String, String> baseMap = new HashMap<String, String>();
        if(StringUtils.isNotBlank((String)config.get("baseEntity"))){
            baseMap.put("baseEntityName", (String)config.get("baseEntity"));
            hasBase = true;
        }
        if(StringUtils.isNotBlank((String)config.get("baseRepository"))){
            baseMap.put("baseRepositoryName", (String)config.get("baseRepository"));
            hasBase = true;
        }

        JpaGeneratorConfig4Mysql jpaGeneratorConfig;
        if(hasBase){
            jpaGeneratorConfig = new JpaGeneratorConfig4Mysql(table, database, baseMap);
        }else{
            jpaGeneratorConfig = new JpaGeneratorConfig4Mysql(table, database);
        }

        jpaGeneratorConfig.setProjectBasePath(baseProjectPath);
        jpaGeneratorConfig.setBasePackage(basePackage);
        jpaGeneratorConfig.setModule((String)config.get("module"));
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
        option.setCoverCodeFile(isCover);
        jpaGeneratorConfig.setGeneratorOptions(option);

        List<CodeTemplateConfig> codeList;
        if(generatorEntity){
            codeList = jpaGeneratorConfig.getCodeVelocities();
        }else{
            codeList = new ArrayList<CodeTemplateConfig>();
        }
        if(generatorRepository){
            codeList.add(JpaGeneratorConfig4Mysql.default_repository);
        }
        if(generatorMapper){
            codeList.add(JpaGeneratorConfig4Mysql.default_dao);
            codeList.add(JpaGeneratorConfig4Mysql.default_mapper);
        }
        if(generatorCommonMapper){
        	codeList.add(JpaGeneratorConfig4Mysql.default_common_mapper);
        }
        if(generatorService){
            codeList.add(JpaGeneratorConfig4Mysql.default_service);
            codeList.add(JpaGeneratorConfig4Mysql.default_serviceImpl);
        }
        if(generatorController){
            codeList.add(JpaGeneratorConfig4Mysql.default_controller);
        }
        if(generatorModel){
        	codeList.add(JpaGeneratorConfig4Mysql.default_model);
        }
        jpaGeneratorConfig.setCodeVelocities(codeList);

//        配置制定的配置文件模板
        CodeGenerator codeGenerator = new SimpleCodeGenerator();
        codeGenerator.setGeneratorConfig(jpaGeneratorConfig);
        codeGenerator.generate();
    }

}

