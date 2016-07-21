/**
* @Company 青鸟软通   
* @Title: ExcelConfig.java 
* @Package org.bana.common.util.poi 
* @author Liu Wenjie   
* @date 2015-7-2 上午11:03:06 
* @version V1.0   
*/ 
package org.bana.common.util.office.impl.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.basic.XmlLoader;
import org.bana.common.util.exception.BanaUtilException;
import org.bana.common.util.office.impl.config.RowConfig.RowType;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: ExcelConfig 
 * @Description: Excel的配置项
 *  
 */
public class ExcelConfig {
	/*========加载的配置实用的信息 begin ========*/
	private static final String XSD_FILE = "/office/excelConfig.xsd";
	
	private static final Logger LOG = LoggerFactory.getLogger(ExcelConfig.class);
	/** 
	* @Fields configFile : 配置文件的地址
	*/ 
	private String configFile;
	
	/** 
	* @Fields mutiTitleMap : 当一列支持多种列时，支持
	*/ 
	private Map<String,List<String>> mutiTitleMap;
	
	/** 
	* @Fields dicMap : 导出时使用的一个map值，根据如果使用了useMap参数，那么根据dicKey去查找对应的价值对去设置此值
	*/ 
	private Map<String,Map<String,Object>> dicMap;
	
	/** 
	* @Fields styleMap : 配置中所有用到的style集合
	*/ 
	private ThreadLocal<Map<String,CellStyle>> styleMapCollection = new ThreadLocal<Map<String,CellStyle>>();
	
	/*========加载的配置实用的信息 end ========*/
	
	/*========excel 中使用的配置 begin ========*/
	/** 
	* @Fields name : excel的文件名
	*/ 
	private String name;
	
	/** 
	* @Fields type : excel的类型，.xls 或者 是.xlsx
	*/ 
	private ExcelType type;
	
	/** 
    * @Fields style : 设置的默认样式
    */ 
    private Map<String,String> style;	
	
	/** 
	* @Fields sheetConfigList : sheet页的配置集合
	*/ 
	private List<SheetConfig> sheetConfigList;
	
	/** 
	* @Fields baseFile : excel的根组织名称，如果是基于某个文件来生成文件，需要指定路径
	*/ 
	private String baseFile;
	
	/*========excel 中使用的配置 begin ========*/
	
	/** 
	* @Description: 初始化加载配置
	* @author Liu Wenjie   
	* @date 2015-7-2 下午2:54:14   
	*/ 
	public void init(){
		//如果没有配置文件，提示报错
		if(StringUtils.isBlank(configFile)){
			throw new BanaUtilException("没有指定配置文件路径，或指定的配置文件路径为空白");
		}
		//加载当前配置文件
//		Document document = XmlLoader.getDocument(configFile,XSD_FILE);
		Document document = XmlLoader.getDocument(configFile);
		//获取当前配置信息
		Element excel = (Element)document.getRootElement();
		//初始化excelConfig属性
		this.name = excel.attributeValue("name");
		this.type = ExcelType.getInstance(excel.attributeValue("type"));
		this.style = StringUtils.parseStyleStr(excel.attributeValue("style"));
		this.baseFile = excel.attributeValue("baseFile");
		//init SheetConfigList
		this.sheetConfigList = initSheetConfigList(document);
	}
	
	/** 
	* @Description: 根据指定动态列名，获取动态列对应的配置内容
	* @author Liu Wenjie   
	* @date 2015-11-29 下午2:29:13 
	* @param colName
	* @return  
	*/ 
	public String getMutiConfigNameUseColName(String colName){
		if(this.mutiTitleMap != null){
			Set<Entry<String, List<String>>> entrySet = mutiTitleMap.entrySet();
			for (Entry<String, List<String>> entry : entrySet) {
				List<String> mutiTilte = entry.getValue();
				if(mutiTilte.contains(colName)){
					return entry.getKey();
				}
			}
		}
		return null;
	}
	
	/** 
	* @Description: 获取单元格的默认样式
	* @author Liu Wenjie   
	* @date 2015-7-8 上午10:48:07 
	* @param workbook
	* @param sheetConfig
	* @param rowConfig
	* @param columnConfig  
	*/ 
	public CellStyle getCellStyle(Workbook workbook, SheetConfig sheetConfig, RowConfig rowConfig, ColumnConfig columnConfig) {
		//构建当前map的样式类型
		Map<String,String> cellMap = new HashMap<String, String>();
		if(this.style != null){
			cellMap.putAll(this.style);
		}
		if(sheetConfig.getStyle() != null){
			cellMap.putAll(sheetConfig.getStyle());
		}
		if(rowConfig.getStyle() != null){
			cellMap.putAll(rowConfig.getStyle());
		}
		if(columnConfig.getStyle() != null){
			cellMap.putAll(columnConfig.getStyle());
		}
		if(!cellMap.isEmpty()){
			String styleKey = JSONObject.fromObject(cellMap).toString();
			Map<String, CellStyle> styleMap = styleMapCollection.get();
			if(styleMap == null){
				styleMap = new HashMap<String, CellStyle>();
				styleMapCollection.set(styleMap);
			}
			CellStyle cellStyle = styleMap.get(styleKey);
			if(cellStyle == null){
				cellStyle = generatorCellStyle(workbook,cellMap);
				styleMap.put(styleKey, cellStyle);
				return cellStyle;
			}else{
				return cellStyle;
			}
		}
		return null;
	}
	
	/** 
	* @Description: 获取执行过程中总共使用了几个样式
	* @author Liu Wenjie   
	* @date 2015-7-9 下午1:25:29 
	* @return  
	*/ 
	public int getCellStyleSize(){
		Map<String, CellStyle> styleMap = styleMapCollection.get();
		return styleMap == null ? 0 :styleMap.size();
	}
	
	/** 
	* @Description: 清除excelObject的当前线程内容
	* @author Liu Wenjie   
	* @date 2015-7-24 下午12:09:29   
	*/ 
	public void clear() {
		styleMapCollection.set(null);
	}
	
	/** 
	* @Description: 创建一个cellMap 的集合
	* @author Liu Wenjie   
	 * @param workbook 
	* @date 2015-7-8 下午12:01:37 
	* @param cellMap
	* @return  
	*/ 
	private CellStyle generatorCellStyle(Workbook workbook, Map<String, String> cellMap) {
		LOG.info("构造一个样式文件，内容为" + cellMap);
		CellStyle cellStyle = workbook.createCellStyle();
		Font createFont = workbook.createFont();
		Set<Entry<String, String>> entrySet = cellMap.entrySet();
		for (Entry<String, String> entry : entrySet) {
			/*=====font样式======*/
			if("fontName".equalsIgnoreCase(entry.getKey())){//字体名称
				createFont.setFontName(entry.getValue());
			}else if("fontHeightInPoints".equalsIgnoreCase(entry.getKey())){//字体大小
				createFont.setFontHeightInPoints(Short.parseShort(entry.getValue()));
			}else if("fontWeight".equalsIgnoreCase(entry.getKey())){//字体是否加粗
				if("BOLD".equalsIgnoreCase(entry.getValue())){
					createFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
				}else{
					createFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
				}
			}else if("color".equalsIgnoreCase(entry.getKey())){//字体颜色
				if("RED".equalsIgnoreCase(entry.getKey())){
					createFont.setColor(Font.COLOR_RED);
				}else if("NORMAL".equalsIgnoreCase(entry.getKey())){
					createFont.setColor(Font.COLOR_NORMAL);
				}else{
					createFont.setColor(Short.parseShort(entry.getKey()));
				}
			}else if("italic".equalsIgnoreCase(entry.getKey())){//是否使用斜体
				createFont.setItalic("true".equalsIgnoreCase(entry.getKey()));
			}else if("strikeout".equalsIgnoreCase(entry.getKey())){//是否使用划线
				createFont.setStrikeout("true".equalsIgnoreCase(entry.getKey()));
			}
			/*=====单元格样式======*/
			else if("border".equalsIgnoreCase(entry.getKey())){//外框
				String value = entry.getValue();
				if(value.contains("left")){
					cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
				}
				if(value.contains("top")){
					cellStyle.setBorderTop(CellStyle.BORDER_THIN);
				}
				if(value.contains("right")){
					cellStyle.setBorderRight(CellStyle.BORDER_THIN);
				}
				if(value.contains("bottom")){
					cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
				}
				if("all".equalsIgnoreCase(value)){
					cellStyle.setBorderTop(CellStyle.BORDER_THIN);
					cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
					cellStyle.setBorderRight(CellStyle.BORDER_THIN);
					cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
				}
			}else if("alignment".equalsIgnoreCase(entry.getKey())){////水平布局
				if("CENTER".equalsIgnoreCase(entry.getKey())){
					cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				}else if("RIGHT".equalsIgnoreCase(entry.getKey())){
					cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
				}else if("LEFT".equalsIgnoreCase(entry.getKey())){
					cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
				}else if("FILL".equalsIgnoreCase(entry.getKey())){
					cellStyle.setAlignment(CellStyle.ALIGN_FILL);
				}else if("GENERAL".equalsIgnoreCase(entry.getKey())){
					cellStyle.setAlignment(CellStyle.ALIGN_GENERAL);
				}else if("JUSTIFY".equalsIgnoreCase(entry.getKey())){
					cellStyle.setAlignment(CellStyle.ALIGN_JUSTIFY);
				}
			}else if("wrapText".equalsIgnoreCase(entry.getKey())){//文本是否被包装，我也不知道是什么意思
				cellStyle.setWrapText("true".equalsIgnoreCase(entry.getValue()));
			}else if("dataFormat".equalsIgnoreCase(entry.getKey())){
				DataFormat dataFormat = workbook.createDataFormat();
	            cellStyle.setDataFormat(dataFormat.getFormat(entry.getValue()));
			}else if("fillForegroundColor".equalsIgnoreCase(entry.getKey())){//单元格背景颜色
				String colorValue = entry.getValue();
				cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//				cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//				cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
//				cellStyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
				if("RED".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColor.RED.index);
				}else if("LIGHT_YELLOW".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
				}else if("DARK_YELLOW".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColor.DARK_YELLOW.index);
				}else if("YELLOW".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
				}else if("BLUE".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
				}else if("LIGHT_BLUE".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
				}else if("DARK_BLUE".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColor.DARK_BLUE.index);
				}else if("GREEN".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
				}else if("LIGHT_GREEN".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
				}else if("DARK_GREEN".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColor.DARK_GREEN.index);
				}else{
//					cellStyle.setFillForegroundColor(Short.parseShort(colorValue));
				}
			}else{
				throw new BanaUtilException("不支持的样式属性" + entry.getKey());
			}
		}
		cellStyle.setFont(createFont);
		return cellStyle;
	}

	/** 
	* @Description: 初始化加载sheetConfig对象
	* @author Liu Wenjie   
	* @date 2015-7-7 下午4:24:57 
	* @param document
	* @return  
	*/ 
	private List<SheetConfig> initSheetConfigList(Document document) {
		List<SheetConfig> sheetConfigList = new ArrayList<SheetConfig>();
		//获取sheet集合
		String sheetPath = "/excel/sheet";
		@SuppressWarnings("unchecked")
		List<Element> selectNodes = (List<Element>)document.selectNodes(sheetPath);
		for (Element sheet : selectNodes) {
			//加载每个sheet配置
			SheetConfig sheetConfig = new SheetConfig();
			sheetConfig.setName(sheet.attributeValue("name"));
			sheetConfig.setStyle(StringUtils.parseStyleStr(sheet.attributeValue("style")));
			sheetConfig.setCheckTitle(Boolean.parseBoolean(sheet.attributeValue("checkTitle")));
			sheetConfig.setRowConfigList(initRowConfigList(sheet));
			sheetConfigList.add(sheetConfig);
		}
		return sheetConfigList;
	}

	/** 
	* @Description: 初始化row的配置信息
	* @author Liu Wenjie   
	* @date 2015-7-7 下午4:31:13 
	* @param sheet
	* @return  
	*/ 
	private List<RowConfig> initRowConfigList(Element sheet) {
		List<RowConfig> rowConfigList = new ArrayList<RowConfig>(); 
		@SuppressWarnings("unchecked")
		List<Element> elements = (List<Element>)sheet.elements();
		for (Element row : elements) {
			RowConfig rowConfig = new RowConfig();
			rowConfig.setClassName(row.attributeValue("class"));
			rowConfig.setRowIndex(Integer.parseInt(row.attributeValue("rowIndex")));
			rowConfig.setType(RowType.getInstance(row.attributeValue("type")));
			rowConfig.setStyle(StringUtils.parseStyleStr(row.attributeValue("style")));
			rowConfig.setMutiTitle(Boolean.parseBoolean(row.attributeValue("mutiTitle")));
			rowConfig.setColumnConfigList(initColumnConfigList(row));
			rowConfigList.add(rowConfig);
		}
		return rowConfigList;
	}

	/** 
	* @Description: 初始化列配置
	* @author Liu Wenjie   
	* @date 2015-7-7 下午4:50:06 
	* @param row
	* @return  
	*/ 
	private List<ColumnConfig> initColumnConfigList(Element row) {
		List<ColumnConfig> columnConfigList = new ArrayList<ColumnConfig>(); 
		@SuppressWarnings("unchecked")
		List<Element> elements = (List<Element>)row.elements();
		for (Element column : elements) {
			ColumnConfig columnConfig = new ColumnConfig();
			String colspan = column.attributeValue("colspan");
			if(!StringUtils.isBlank(colspan)){
				columnConfig.setColspan(Integer.parseInt(colspan));
			}
			columnConfig.setName(column.attributeValue("name"));
			columnConfig.setMappedBy(column.attributeValue("mappedBy"));
			columnConfig.setStyle(StringUtils.parseStyleStr(column.attributeValue("style")));
			columnConfig.setType(column.attributeValue("type"));
			columnConfig.setMutiMap(column.attributeValue("mutiMap"));
			columnConfig.setUseDic(Boolean.parseBoolean(column.attributeValue("useDic")));
			columnConfig.setDicType(column.attributeValue("dicType"));
			columnConfigList.add(columnConfig);
		}
		return columnConfigList;
	}
	
	
	/** 
	* @Description: 根据字典Map获取对应的字典对应的值
	* @author liuwenjie   
	* @date 2016-2-20 上午11:38:41 
	* @param value
	* @param columnConfig
	* @return  
	*/ 
	public Object getDicValue(String key, ColumnConfig columnConfig) {
		if(this.dicMap == null || this.dicMap.isEmpty()){
			return key;
		}
		String dicType = columnConfig.getDicType();
		Map<String, Object> dic = dicMap.get(dicType);
		if(dic == null || dic.isEmpty()){
			return key;
		}
		Object value = dic.get(key);
		if(value == null){
			return key;
		}
		return value;
	}

	/*===============getter and setter =================*/
	/**
	 * @Description: 属性 configFile 的set方法 
	 * @param configFile 
	 */
	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}
	
	/**
	 * @Description: 属性 name 的get方法 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @Description: 属性 name 的set方法 
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @Description: 属性 type 的get方法 
	 * @return type
	 */
	public ExcelType getType() {
		return type;
	}

	/**
	 * @Description: 属性 type 的set方法 
	 * @param type 
	 */
	public void setType(ExcelType type) {
		this.type = type;
	}

	/**
	 * @Description: 属性 sheetConfigList 的get方法 
	 * @return sheetConfigList
	 */
	public List<SheetConfig> getSheetConfigList() {
		return sheetConfigList;
	}

	/**
	 * @Description: 属性 sheetConfigList 的set方法 
	 * @param sheetConfigList 
	 */
	public void setSheetConfigList(List<SheetConfig> sheetConfigList) {
		this.sheetConfigList = sheetConfigList;
	}


	/**
	 * @Description: 属性 style 的set方法 
	 * @param style 
	 */
	public void setStyle(Map<String, String> style) {
		this.style = style;
	}
	

	/**
	 * @Description: 属性 mutiTitleMap 的set方法 
	 * @param mutiTitleMap 
	 */
	public void setMutiTitleMap(Map<String,List<String>> mutiTitleMap) {
		this.mutiTitleMap = mutiTitleMap;
	}
	
	/**
	 * @Description: 属性 mutiTitleMap 的get方法 
	 * @return mutiTitleMap
	 */
	public Map<String, List<String>> getMutiTitleMap() {
		return mutiTitleMap;
	}
	

	/**
	 * @Description: 属性 dicMap 的get方法 
	 * @return dicMap
	 */
	public Map<String, Map<String, Object>> getDicMap() {
		return dicMap;
	}

	/**
	 * @Description: 属性 dicMap 的set方法 
	 * @param dicMap 
	 */
	public void setDicMap(Map<String, Map<String, Object>> dicMap) {
		this.dicMap = dicMap;
	}

	/**
	 * @Description: 属性 baseFile 的get方法 
	 * @return baseFile
	 */
	public String getBaseFile() {
		return baseFile;
	}

	/**
	 * @Description: 属性 baseFile 的set方法 
	 * @param baseFile 
	 */
	public void setBaseFile(String baseFile) {
		this.baseFile = baseFile;
	}

	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-7-8 上午11:27:12 
	* @return 
	* @see java.lang.Object#toString() 
	*/ 
	@Override
	public String toString() {
		return "ExcelConfig [configFile=" + configFile + ", name=" + name + ", type=" + type + ", style=" + style + ", sheetConfigList=" + sheetConfigList + "]";
	}





	/*==============其他本类中实用的配置信息 =============*/
	public static enum ExcelType{
		XLS("xls"),XLSX("xlsx");
		private String extName ; //后缀名称
		private ExcelType(String extName){
			this.extName = extName;
		}
		public String getExtName(){
			return this.extName;
		}
		public static ExcelType getInstance(String extName){
			for (ExcelType excelType : values()) {
				if(excelType.getExtName().equals(extName)){
					return excelType;
				}
			}
			return XLS;
		}
	}


}
