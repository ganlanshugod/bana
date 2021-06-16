package org.bana.common.util.pdf.config.table;

/** 
* @ClassName: CellData 
* @Description: 对应一个表单数据
* @author liuwenjie   
*/ 
public class CellData {
	
	public CellData() {
	}

	public CellData(String title, String value) {
		this.title = title;
		this.value = value;
	}


	private String title;  // 对应列名数据
	
	private String value; //对应表格数据

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
