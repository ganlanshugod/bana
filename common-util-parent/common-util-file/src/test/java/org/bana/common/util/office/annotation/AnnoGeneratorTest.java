package org.bana.common.util.office.annotation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.bana.common.util.office.ExcelGenerator;
import org.bana.common.util.office.ExcelObject;
import org.bana.common.util.office.impl.BasicExcelGenerator;
import org.bana.common.util.office.impl.annotation.AnnotationExcelUploadConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnnoGeneratorTest {

	private ExcelGenerator excelGenerator;
	
	@Before
	public void init(){
		excelGenerator = new BasicExcelGenerator();
	}
	
	@Test
	public void testSimpleUpload() throws FileNotFoundException{
		FileInputStream inputStream = new FileInputStream(new File("D:/test.xls"));
		
		AnnotationExcelUploadConfig excelConfig = new AnnotationExcelUploadConfig(TestData.class);
		ExcelObject generatorObject = excelGenerator.generatorObject(inputStream,excelConfig);
		
		Assert.assertNotNull(generatorObject);
//		List<? extends Object> data = generatorObject.getData("sheet1-test");
		List<? extends Object> data = generatorObject.getData(0);
		Assert.assertEquals(1000, data.size());
		System.out.println(data.get(500));
//		excelGenerator.generatorExcel(outputStream, excelObject);
	}
}
