package org.bana.common.util.office.annotation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bana.common.util.office.ExcelGenerator;
import org.bana.common.util.office.ExcelObject;
import org.bana.common.util.office.config.ColumnConfig;
import org.bana.common.util.office.impl.BasicExcelGenerator;
import org.bana.common.util.office.impl.annotation.AnnotationExcelDownloadConfig;
import org.bana.common.util.office.impl.annotation.AnnotationExcelUploadConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnnoGeneratorTest {

	private ExcelGenerator excelGenerator;
	
	private String basePath = "testexcel";
	
	@Before
	public void init(){
		excelGenerator = new BasicExcelGenerator();
	}
	
	private Map<String,List<ColumnConfig>> getMutiMap(){
		Map<String,List<ColumnConfig>> mutiMap = new HashMap<String,List<ColumnConfig>>();
		mutiMap.put("额外配置", 
				Arrays.asList(
						ColumnConfig.parseString("身份证(dicType:idCard)"),
						ColumnConfig.parseString("联系电话")
						));
		return mutiMap;
	}
	
	@Test
	public void testSimpleUpload() throws FileNotFoundException{
		FileInputStream inputStream = new FileInputStream(new File("D:/test.xls"));
		
		AnnotationExcelUploadConfig excelConfig = new AnnotationExcelUploadConfig(TestData.class);
		excelConfig.setMutiTitleMap(getMutiMap());
		ExcelObject generatorObject = excelGenerator.generatorObject(inputStream,excelConfig.toSimpleExcelUploadConfig());
		
		Assert.assertNotNull(generatorObject);
//		List<? extends Object> data = generatorObject.getData("sheet1-test");
		List<? extends Object> data = generatorObject.getData(0);
		Assert.assertEquals(1000, data.size());
		System.out.println(data.get(500));
//		excelGenerator.generatorExcel(outputStream, excelObject);
	}
	
	@Test
	public void testSimpleDownload() throws IOException {
		// 第四种： D:\git\daotie\daotie
		AnnotationExcelDownloadConfig excelConfig = new AnnotationExcelDownloadConfig(TestDownData.class);
//        System.out.println(System.getProperty("user.dir"));
		excelConfig.setBaseFile("/office/学生成绩单模版-科目横版-v1.1.xls");
		
		Map<String,Map<String,Object>> dicMap = new HashMap<String, Map<String,Object>>();
		Map<String,Object> sexMap = new HashMap<String, Object>();
		sexMap.put("1", "男");
		sexMap.put("2", "女");
		sexMap.put("0", "未知");
		dicMap.put("sex", sexMap);
		Map<String,Object> idCard = new HashMap<String, Object>();
		idCard.put("18263906395:0", "刘文杰");
		dicMap.put("idCard", idCard);
		excelConfig.setMutiTitleMap(getMutiMap());
		Set<String> dicKey = excelConfig.getDicKey();
		System.out.println("配置的字典值是" + dicKey);
		excelConfig.setDicMap(dicMap);
        File file = new File(basePath + "/test2.xls");
        if(!file.getParentFile().exists()){
        	file.getParentFile().mkdirs();
        }
		FileOutputStream outputStream = new FileOutputStream(file);
		ExcelObject excelObject = new ExcelObject();
		List<TestDownData> dataList = new ArrayList<TestDownData>();
		addDate(dataList);
		excelObject.putData(dataList);
		excelObject.setExcelConfig(excelConfig);
		excelGenerator.generatorExcel(outputStream, excelObject);
		outputStream.close();
		System.out.println("样式数量为" + excelConfig.getCellStyleSize());
	}
	
	
	@Test
	public void testAddErrors()throws IOException{
		//输入流（源文件）
//		File file1 = new File(basePath + "/test2.xls");
		File file1 = new File("D:/test.xls");
		FileInputStream inputStream = new FileInputStream(file1);
		//输出流（保存位置）
		File file = new File(basePath + "/test3.xls");
		if(!file.getParentFile().exists()){
        	file.getParentFile().mkdirs();
        }
		FileOutputStream outputStream = new FileOutputStream(file);
		//读取配置信息
		AnnotationExcelUploadConfig excelConfig = new AnnotationExcelUploadConfig(TestData.class);
		excelConfig.setMutiTitleMap(getMutiMap());
		//错误信息
		List<Map<Integer,String>> errorRecords = new ArrayList<Map<Integer,String>>();
		errorRecords.add(getErrorMessage());
		//执行方法
		excelGenerator.addErrorResult(inputStream, outputStream, excelConfig, errorRecords, false);
	}
	
	private Map<Integer,String> getErrorMessage(){
		Random random = new Random();
		Map<Integer,String> errors = new HashMap<Integer,String>();
		for (int i = 0; i < 1000; i++) {
			if(random.nextInt(100)< 30){
				errors.put(i, "在第" +i + "有错误");
			}
		}
		return errors;
	}
	
	
	private void addDate(List<TestDownData> dataList){
		Random random = new Random();
		for (int i = 0; i < 1000; i++) {
			TestDownData testData = new TestDownData();
			testData.setAddress("地址内容，看看"+i+":" + random.nextInt(100));
			testData.setAge(random.nextInt(100));
			testData.setName("name"+i+":" + random.nextInt(100));
			testData.setSex(random.nextInt(2)+"");
			testData.setDate(new Date());
			Map<String,Object> mutiMap = new HashMap<String,Object>();
			mutiMap.put("身份证", "18263906395:"+i);
			mutiMap.put("联系电话","372928198708098136:"+i);
			testData.setMutiMap(mutiMap);
			dataList.add(testData);
		}
	}
}
