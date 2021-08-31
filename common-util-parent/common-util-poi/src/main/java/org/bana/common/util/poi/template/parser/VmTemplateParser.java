package org.bana.common.util.poi.template.parser;

import java.io.StringWriter;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.bana.common.util.basic.BeanToMapUtil;
import org.bana.common.util.poi.BanaPoiException;
import org.bana.common.util.velocity.SimpleVelocityEngineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: TextTemplateParser
 * @Description: 解析器
 * @author liuwenjie
 */
public class VmTemplateParser implements TemplateParser {

	private static final Logger LOG = LoggerFactory.getLogger(VmTemplateParser.class);
	

	@Override
	public void parserTemplate(Cell cell, Row row, Object data) {
		CellType cellType = cell.getCellType();
		switch (cellType) {
		case _NONE:
		case BLANK:
			break;
		case BOOLEAN:
			LOG.warn("BOOLEAN类型的单元格，不支持解析 {} ,{},{}",cell.getBooleanCellValue() , row.getRowNum(),cell.getColumnIndex());
			break;
		case STRING:
			String stringCellValue = cell.getStringCellValue();
			String parserString = parserString(stringCellValue, data);
			if(!parserString.equals(stringCellValue)) {
				cell.setCellValue(parserString);
			}
			break;
		case NUMERIC:
			LOG.warn("数字类型的单元格，不支持解析 {} ,{},{}",cell.getNumericCellValue() , row.getRowNum(),cell.getColumnIndex());
			break;
		case FORMULA:
			LOG.warn("公式类型的单元格，不支持解析 {} ,{},{}",cell.getCellFormula() , row.getRowNum(),cell.getColumnIndex());
			break;
		case ERROR:
			String info = String.format("这是个奇怪的解析错误单元格，第 %s 行，第 %s 列", row.getRowNum(), cell.getColumnIndex());
			LOG.warn(info);
			break;
		default:
			throw new BanaPoiException("奇怪的事情，这个单元格不在计划解析的类型中");
		}

	}
	
	
	private String parserString(String template,Object data) {
		VelocityEngine velocityEngine = SimpleVelocityEngineFactory.getVelocityEngine();
		Map<String, Object> contextMap;
		if(data instanceof Map) {
			contextMap = (Map<String, Object>)data;
		}else {
			contextMap = BeanToMapUtil.convertBean(data);
		}
		VelocityContext context = new VelocityContext(contextMap);
		StringWriter writer = new StringWriter();
		velocityEngine.evaluate(context, writer, "", template); 
		return writer.toString();
	}

}
