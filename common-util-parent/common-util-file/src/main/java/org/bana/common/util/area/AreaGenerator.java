package org.bana.common.util.area;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.exception.BanaUtilException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 全国省市区编码的统一获取，数据来源使用
 * http://www.stats.gov.cn 网站的数据
 * @author Liu Wenjie
 *
 */
public class AreaGenerator {
	
	private static final Logger LOG = LoggerFactory.getLogger(AreaGenerator.class);

	private static String baseUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/";
	
	private static int count = 0;
	
	
	public static void generatorSqlFromJsonFile(String rootId,String sqlFilePath,String jsonFilePath) throws IOException{
		JSONArray jsonArray = JSONArray.parseArray(FileUtils.readFileToString(new File(jsonFilePath),"UTF-8"));
		File sqlFile = new File(sqlFilePath);
		if(!sqlFile.getParentFile().exists()){
			sqlFile.getParentFile().mkdirs();
		}
		FileUtils.write(sqlFile, "","UTF-8");
		List<String> sqlStrList = generatorSqlFromJsonArray(rootId, jsonArray);
		for (String sqlStr : sqlStrList) {
			FileUtils.write(sqlFile, sqlStr,"UTF-8",true);
		}
	}
	
	public static List<Map<String,String>> generatorMapFromJsonArray(String rootId, JSONArray jsonArray){
		List<Map<String,String>> result = new ArrayList<>();
		rootId = compute(rootId);
		String sql = "INSERT INTO t_bi_area (id ,name ,parent_id) VALUES(";
		for (Object object : jsonArray) {
			Map<String,String> map = new HashMap<>();
			JSONObject jsonObject = (JSONObject)object;
			String code = compute(jsonObject.getString("code"));
			String name = jsonObject.getString("name");
			String sqlStr = sql + code +",'"+name+"','"+rootId+"')\n";
			map.put("code", code);
			map.put("name", name);
			map.put("parentId", rootId);
			result.add(map);
			JSONArray subArr = jsonObject.getJSONArray("subArea");
			if(subArr != null && subArr.size() > 0){
				result.addAll(generatorMapFromJsonArray(code, subArr));
			}
		}
		return result;
	}
	public static List<String> generatorSqlFromJson(String rootId,JSONArray jsonArray) {
		return generatorSqlFromJsonArray(rootId, jsonArray);
	}

	private static List<String> generatorSqlFromJsonArray(String rootId, JSONArray jsonArray) {
		rootId = compute(rootId);
		List<String> sqlList = new ArrayList<>();
		String sql = "INSERT INTO t_bi_area (id ,name ,parent_id) VALUES(";
		for (Object object : jsonArray) {
			JSONObject jsonObject = (JSONObject)object;
			String code = compute(jsonObject.getString("code"));
			String name = jsonObject.getString("name");
			String sqlStr = sql + code +",'"+name+"','"+rootId+"')\n";
			sqlList.add(sqlStr);
			JSONArray subArr = jsonObject.getJSONArray("subArea");
			if(subArr != null && subArr.size() > 0){
				sqlList.addAll(generatorSqlFromJsonArray(code, subArr));
			}
		}
		return sqlList;
	}
	
	private static String compute(String code) {
		code = code.replace("000000", "");
		if(code.length() > 6){
			return StringUtils.rightPad(code, 12,'0');
		}else{
			return StringUtils.rightPad(code, 6,'0');
		}
	}

	/**
	 * 
	 * @param parentCode http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/65.html
	 */
	public static void generatorJsonFile(String parentUrl,String filePath){
		String parentId = parentUrl.substring(parentUrl.lastIndexOf("/")+1,parentUrl.lastIndexOf("."));
		parentId = StringUtils.rightPad(parentId, 6, '0');
		LOG.info("加载的根编码是" + parentId);
		count = 0;
		JSONArray jsonFromUrl = getJsonFromUrl(parentUrl,true);
		File file = new File(filePath);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		try {
			FileUtils.write(file, jsonFromUrl.toJSONString(),"UTF-8");
			LOG.info("保存文件成功===" + file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error("保存文件失败",e);
		}
	}
	
	public static JSONArray getJsonFromUrl(String url,boolean includeSub){
		JSONArray areaArr = new JSONArray();
		if(StringUtils.isBlank(url)){
			return null;
		}
		String selectStr = getSelect(url);
		if(StringUtils.isBlank(selectStr)){
			return null;
		}
		Document document = null;
		int index = 0;
		while(true){
			try {
				document = Jsoup.connect(url).timeout(5000).get();
				count++;
				break;
			} catch (IOException e) {
				index ++;
				LOG.error("读取链接"+url+"，失败" + index +"次", e.getMessage());
				if(index > 3){
					throw new BanaUtilException("读取链接"+url+"，失败", e);
				}
			}
		}
		
		System.out.println(count);
		Elements select = document.select(selectStr);
//			System.out.println("选中的元素数" + select.size());
		
		select.parallelStream().forEach(tr -> {
			Elements tds = tr.select("td");
			Element td = tds.first();
			String code = td.text();
			String subUrl = td.select("a").attr("href");
			if(StringUtils.isNotBlank(subUrl)){
				subUrl = getSubUrl(url,subUrl);
			}
			Element nameA = tds.last();
			String name = nameA.text();
			Map<String,Object> mapResult = new HashMap<String,Object>();
			mapResult.put("code", code);
			mapResult.put("name", name);
			mapResult.put("subUrl", subUrl);
			if(includeSub){
				JSONArray subArea = getJsonFromUrl(subUrl,true);
				if(subArea != null && subArea.size() > 0){
					mapResult.put("subArea", subArea);
				}
			}
			areaArr.add(new JSONObject(mapResult));
		});
//		Iterator<Element> iterator = select.iterator();
//		while(iterator.hasNext()){
//			Element tr = iterator.next();
//		}
		return areaArr;
	}

	/**
	 * 根据subUrl拼接
	 * @param subUrl
	 * @param subUrl 
	 * @return
	 */
	private static String getSubUrl(String parentUrl, String subUrl) {
		return parentUrl.substring(0,parentUrl.lastIndexOf("/")+1)+subUrl;
	}

	private static String getSelect(String url) {
		String parentId = url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."));
//		parentId = StringUtils.rightPad(parentId, 6, '0');
		int parentLength = parentId.length();
		if(parentId.length() == 2){
			return "table.citytable tr.citytr";
		}else if(parentLength == 4){
			return "table.countytable tr.countytr";
		}else if(parentLength == 6){
			return "table.towntable tr.towntr";
		}else if(parentLength == 9){
			return "table.villagetable tr.villagetr";
		}else {
			return null;
		}
	}
}
