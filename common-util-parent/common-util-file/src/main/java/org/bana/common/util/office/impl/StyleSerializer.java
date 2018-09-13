package org.bana.common.util.office.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.exception.BanaUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StyleSerializer {
	
	private static final Logger LOG = LoggerFactory.getLogger(StyleSerializer.class);

	/** 
	* @Description: 将形如 test:sdfg;test2:sdfwe的常用在样式定义中的写法字符串，转化为一个map对象
	* @author Liu Wenjie   
	* @date 2015-7-8 上午11:44:19 
	* @param style
	* @return  
	*/ 
	public static Map<String,String> parseStyleStr(String style){
		if(!StringUtils.isEmpty(style)){
			Map<String,String> styleMap = new HashMap<String, String>();
			String[] split = style.split(";");
			if(split != null){
				for (String str : split) {
					if(StringUtils.isEmpty(str)){
						continue;
					}
					int indexOf = str.indexOf(":");
					if(indexOf == -1){
						throw new BanaUtilException("不合法的样式定义方式 " + str);
					}else{
						
					}
					styleMap.put(str.substring(0,indexOf), str.substring(indexOf+1));
				}
			}
			return styleMap;
		}
		return null;
	}
	
	/** 
	* @Description: 创建一个cellMap 的集合
	* @author Liu Wenjie   
	 * @param workbook 
	* @date 2015-7-8 下午12:01:37 
	* @param cellMap
	* @return  
	*/ 
	public static CellStyle generatorCellStyle(Workbook workbook, Map<String, String> cellMap) {
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
//					createFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
					createFont.setBold(true);
				}else{
					createFont.setBold(false);
//					createFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
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
//					cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
					cellStyle.setBorderLeft(BorderStyle.THIN);
				}
				if(value.contains("top")){
//					cellStyle.setBorderTop(CellStyle.BORDER_THIN);
					cellStyle.setBorderTop(BorderStyle.THIN);
				}
				if(value.contains("right")){
//					cellStyle.setBorderRight(CellStyle.BORDER_THIN);
					cellStyle.setBorderRight(BorderStyle.THIN);
				}
				if(value.contains("bottom")){
//					cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
					cellStyle.setBorderBottom(BorderStyle.THIN);
				}
				if("all".equalsIgnoreCase(value)){
					cellStyle.setBorderLeft(BorderStyle.THIN);
					cellStyle.setBorderTop(BorderStyle.THIN);
					cellStyle.setBorderRight(BorderStyle.THIN);
					cellStyle.setBorderBottom(BorderStyle.THIN);
				}
			}else if("alignment".equalsIgnoreCase(entry.getKey())){////水平布局
				if("CENTER".equalsIgnoreCase(entry.getKey())){
					cellStyle.setAlignment(HorizontalAlignment.CENTER);
//					cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				}else if("RIGHT".equalsIgnoreCase(entry.getKey())){
					cellStyle.setAlignment(HorizontalAlignment.RIGHT);
				}else if("LEFT".equalsIgnoreCase(entry.getKey())){
					cellStyle.setAlignment(HorizontalAlignment.LEFT);
				}else if("FILL".equalsIgnoreCase(entry.getKey())){
					cellStyle.setAlignment(HorizontalAlignment.FILL);
				}else if("GENERAL".equalsIgnoreCase(entry.getKey())){
					cellStyle.setAlignment(HorizontalAlignment.GENERAL);
				}else if("JUSTIFY".equalsIgnoreCase(entry.getKey())){
					cellStyle.setAlignment(HorizontalAlignment.JUSTIFY);
				}
			}else if("wrapText".equalsIgnoreCase(entry.getKey())){//文本是否被包装，我也不知道是什么意思
				cellStyle.setWrapText("true".equalsIgnoreCase(entry.getValue()));
			}else if("dataFormat".equalsIgnoreCase(entry.getKey())){
				DataFormat dataFormat = workbook.createDataFormat();
	            cellStyle.setDataFormat(dataFormat.getFormat(entry.getValue()));
			}else if("fillForegroundColor".equalsIgnoreCase(entry.getKey())){//单元格背景颜色
				String colorValue = entry.getValue();
				cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//				cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//				cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
//				cellStyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
				if("RED".equalsIgnoreCase(colorValue)){
					cellStyle.setFillBackgroundColor(HSSFColorPredefined.RED.getIndex());
//					cellStyle.setFillForegroundColor(HSSFColor.RED.index);
				}else if("LIGHT_YELLOW".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColorPredefined.LIGHT_YELLOW.getIndex());
				}else if("DARK_YELLOW".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColorPredefined.DARK_YELLOW.getIndex());
				}else if("YELLOW".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
				}else if("BLUE".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColorPredefined.BLUE.getIndex());
				}else if("LIGHT_BLUE".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColorPredefined.LIGHT_BLUE.getIndex());
				}else if("DARK_BLUE".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColorPredefined.DARK_BLUE.getIndex());
				}else if("GREEN".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColorPredefined.GREEN.getIndex());
				}else if("LIGHT_GREEN".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColorPredefined.LIGHT_GREEN.getIndex());
				}else if("DARK_GREEN".equalsIgnoreCase(colorValue)){
					cellStyle.setFillForegroundColor(HSSFColorPredefined.DARK_GREEN.getIndex());
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
}
