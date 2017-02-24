/**
* @Company 青鸟软通   
* @Title: ExcelObject.java 
* @Package org.bana.common.util.poi 
* @author Liu Wenjie   
* @date 2015-7-2 上午10:46:07 
* @version V1.0   
*/ 
package org.bana.common.util.office;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bana.common.util.office.impl.config.ExcelConfig.ExcelType;

/** 
 * @ClassName: ExcelObject 
 * @Description: 保存一个Excel文档内容的对象
 *  
 */
public class ExcelObject implements Serializable{

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 4671839919888062015L;
	
	
	private Map<String,List<? extends Object>> dataMap;
	
	/** 
	* @Fields type : 指定excel的生成類型，xlxs或xls類型
	*/ 
	private ExcelType type;
	
	/** 
	* @Description: 添加一条数据记录，key值为sheet页名称
	* @author Liu Wenjie   
	* @date 2015-7-7 下午6:42:06 
	* @param sheetName
	* @param dataList  
	*/ 
	public void putData(String sheetName,List<? extends Object> dataList){
		if(dataMap == null){
			dataMap = new HashMap<String, List<? extends Object>>();
		}
		dataMap.put(sheetName,dataList);
	}
	
	/** 
	* @Description: 根据sheetname 获取指定的数据
	* @author Liu Wenjie   
	* @date 2015-7-8 上午9:29:02 
	* @param sheetName  
	*/ 
	public List<? extends Object> getData(String sheetName){
		if(dataMap == null){
			return null;
		}
		return dataMap.get(sheetName);
	}
	
	
	/*=================getter and setter ===============*/

}
