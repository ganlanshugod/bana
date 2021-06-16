package org.bana.common.util.office.doc.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.bana.common.util.exception.BanaUtilException;
import org.bana.common.util.office.doc.DocGenerator;
import org.bana.common.util.office.doc.config.DocObject;

/** 
* @ClassName: BaseDocGenerator 
* @Description: 一个基础的Doc生成器的类
* @author liuwenjie   
*/ 
public class BaseDocGenerator implements DocGenerator {

	@Override
	public void generatorDoc(OutputStream outputStream, DocObject docObject) {
//		Document doc =  HWPFDocument
		XWPFDocument doc =  new XWPFDocument();
		
		//创建一个段落
		XWPFParagraph para = doc.createParagraph();
		 
		//一个XWPFRun代表具有相同属性的一个区域：一段文本
		XWPFRun run = para.createRun();
		run.setBold(true); //加粗
		run.setText("加粗的内容");
		run = para.createRun();
		run.setColor("FF0000");
		run.setText("红色的字。");
		
		//把doc输出到输出流
		try {
			doc.write(outputStream);
		} catch (IOException e) {
			throw new BanaUtilException("将生成的Excel写入到指定的流时出现读写错误" + docObject);
		}finally{
		}
		
	}

}
