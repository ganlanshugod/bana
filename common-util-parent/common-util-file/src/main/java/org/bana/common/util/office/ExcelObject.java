/**
* @Company weipu   
* @Title: ExcelObject.java 
* @Package org.bana.common.util.poi 
* @author Liu Wenjie   
* @date 2015-7-2 上午10:46:07 
* @version V1.0   
*/ 
package org.bana.common.util.office;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.office.config.ExcelDownloadConfig;
import org.bana.common.util.office.config.ExcelType;

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
	 * 如果没有指定sheet页的名字的话，使用list来保存
	 */
	private List<List<? extends Object>> dataList;
	
	/** 
	* @Fields type : 指定excel的生成類型，xlxs或xls類型
	*/ 
	private ExcelType type;
	
	/**
	 * @Fields type : excel的配置类
	 */
	private ExcelDownloadConfig excelConfig;
	
	/** 
	* @Description: 添加一条数据记录，key值为sheet页名称
	* @author Liu Wenjie   
	* @date 2015-7-7 下午6:42:06 
	* @param sheetName
	* @param dataList  
	*/ 
	public void putData(String sheetName,List<? extends Object> dataList){
		if(StringUtils.isNotBlank(sheetName)){
			if(dataMap == null){
				dataMap = new HashMap<String, List<? extends Object>>();
			}
			dataMap.put(sheetName,dataList);
		}else{
			if(this.dataList == null){
				this.dataList = new ArrayList<List<? extends Object>>();
			}
			this.dataList.add(dataList);
		}
	}
	
	public void putData(List<? extends Object> dataList){
		putData(null,dataList);
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
	
	public List<? extends Object> getData(int index){
		if(dataList != null){
			return dataList.get(index);
		}
		return null;
	}
	
	/*=================getter and setter ===============*/

	public ExcelDownloadConfig getExcelConfig() {
		return excelConfig;
	}

	public void setExcelConfig(ExcelDownloadConfig excelConfig) {
		this.excelConfig = excelConfig;
	}
	
}
