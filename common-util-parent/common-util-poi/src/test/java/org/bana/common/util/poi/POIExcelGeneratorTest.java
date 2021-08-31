package org.bana.common.util.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bana.common.util.poi.template.param.SimpleTemplateExcelObject;
import org.junit.Before;
import org.junit.Test;

public class POIExcelGeneratorTest {
	
	private POIExcelGenerator excelGenerator;
	
	@Before
	public void init() {
		excelGenerator = new CustomTemplatePOIExcelGenerator();
	}

	@Test
	public void testGenerator() throws FileNotFoundException {
		// 输出文件
		File outFile = new File("office/generator.xlsx");
		if(!outFile.exists()) {
			if(!outFile.getParentFile().exists()) {
				outFile.getParentFile().mkdirs();
			}
		}
		FileOutputStream outputStream = new FileOutputStream(outFile);
		// 模板输入文件
		FileInputStream templateFile = new FileInputStream("office/template.xlsx");
		
		SimpleTemplateExcelObject simpleObj = new SimpleTemplateExcelObject();
		simpleObj.setTemplateInputStream(templateFile);
//		Map<String,Object> data = new HashMap<>();
//		data.put("name", "liuwenjie");
		Data data = new Data();
		data.setName("liuwenjie");
		Item item = new Item();
		item.setCode("hello code");
		item.setValue("hello,value");
		data.setItem(item);
		data.setItemList(createItem(3));
		simpleObj.setExcelData(data);
		
		excelGenerator.generatorExcel(outputStream, simpleObj);
		
	}
	
	private List<Item> createItem(int size){
		List<Item> itemList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Item item = new Item();
			item.setCode("itemcode"+i);
			item.setValue("itemValue"+i);
			item.setItemList2(createItem2(size));
			itemList.add(item);
		}
		return itemList;
	}
	
	private List<Item2> createItem2(int size) {
		List<Item2> itemList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Item2 item = new Item2();
			item.setCode("item2code"+i);
			item.setValue("item2Value"+i);
			itemList.add(item);
		}
		return itemList;
	}

	public static class Data{
		private String name;
		private Item item;
		private List<Item> itemList;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Item getItem() {
			return item;
		}
		public void setItem(Item item) {
			this.item = item;
		}
		public List<Item> getItemList() {
			return itemList;
		}
		public void setItemList(List<Item> itemList) {
			this.itemList = itemList;
		}
		
	}
	
	public static class Item {
		private String code;
		private String value;
		
		private List<Item2> itemList2; 
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public List<Item2> getItemList2() {
			return itemList2;
		}
		public void setItemList2(List<Item2> itemList2) {
			this.itemList2 = itemList2;
		}
		
	}
	
	public static class Item2{
		private String code;
		private String value;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
}
