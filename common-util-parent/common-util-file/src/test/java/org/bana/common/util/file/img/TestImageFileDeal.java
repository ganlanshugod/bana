package org.bana.common.util.file.img;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.bana.common.util.basic.Cn2Spell;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TestImageFileDeal {
	
	private static String basePath = "D:/noNeedInstall/nginx-1.10.2/html/other/pingjia2017/img";
	
	@Test
	public void addPinyinToJson() throws IOException{
		JSONArray jsonArr = getJsonArray();
		HashMap<String,Integer> count = new HashMap<String,Integer>();
		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject json = jsonArr.getJSONObject(i);
			final String name = json.getString("name");
			String pingyin = Cn2Spell.converterToSpell(name);
			json.put("pinyin", pingyin);
			Integer integer = count.get(pingyin);
			if(integer == null){
				count.put(pingyin, 1);
			}else{
				count.put(pingyin,++integer);
			}
		}
		updateJsonFile(jsonArr);
		if(count.size() == jsonArr.size()){
			System.out.println("没有拼音重名的名字");
		}
		for (Entry<String, Integer> entry : count.entrySet()) {
			if(entry.getValue()>1){
				System.out.println("重复的拼音名称："+entry.getKey());
			}
		}
	}
	
	@Test
	public void renameImageName() throws IOException{
		File file = new File(basePath);
		List<String> list = new ArrayList<String>();
		JSONArray jsonArr = getJsonArray();
		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject json = jsonArr.getJSONObject(i);
			final String realName =json.getString("name");
			final String name = json.getString("pinyin");//用拼音去修改名称
//			System.out.println(Cn2Spell.converterToSpell(name));
			File[] equalFile = file.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					String fileName = pathname.getName().split("\\.")[0];
					return Cn2Spell.converterToSpell(fileName).equals(name);
				}
			});
			if(equalFile == null || equalFile.length == 0){
				list.add(realName);
//				System.out.println("没有图片的老师======"+name);
			}else if(equalFile.length == 1){
				File targetFile = equalFile[0];
				//System.out.println("老师" + name + "对应的图片为" + targetFile.getName());
				String[] split = targetFile.getName().split("\\.");
				if(!split[0].equals(name)){
					System.out.println("重命名此文件"+targetFile.getName());
					targetFile.renameTo(new File(basePath + File.separatorChar + name + "." + split[split.length-1]));
				}
			}else{
				System.out.println("老师" + name + "对应多个图片!!!!!!!!" + Arrays.toString(equalFile));
			}
		}
		System.out.println("没有图片的老师名字" + list);
	}
	
	/**
	 * 找出那些图片在json中没有对应人的图片
	 * @throws IOException
	 */
	@Test
	public void findFileNotInJson() throws IOException{
		File file = new File(basePath);
		JSONArray jsonArr = getJsonArray();
		File[] listFiles = file.listFiles();
		for (File file2 : listFiles) {
			String[] filePath = file2.getName().split("\\.");
			boolean hasUse = false;
			for (int i = 0; i < jsonArr.size(); i++) {
				JSONObject json = jsonArr.getJSONObject(i);
				String name = json.getString("name");
				if(filePath[0].contains(name)){
					hasUse = true;
					break;
				}
			}
			if(!hasUse){
				System.out.println(file2.getName() + "没有使用");
			}
		}
		
	}
	/**
	 * 根据实际的图片文件，覆盖对应的json字符串
	 * @throws IOException 
	 */
	@Test
	public void testRecoverJsonFile() throws IOException{
		File file = new File(basePath);
		List<String> list = new ArrayList<String>();
		JSONArray jsonArr = getJsonArray();
		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject json = jsonArr.getJSONObject(i);
			String imgUrl = json.getString("imgUrl");
//			if(StringUtils.isNotBlank(imgUrl)){
//				continue;
//			}
			final String name = json.getString("pinyin");
			File[] equalFile = file.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					String fileName = pathname.getName().split("\\.")[0];
					return Cn2Spell.converterToSpell(fileName).equals(name);
				}
			});
			if(equalFile == null || equalFile.length == 0){
				list.add(name);
//				System.out.println("没有图片的老师======"+name);
			}else if(equalFile.length == 1){
				File targetFile = equalFile[0];
				//System.out.println("老师" + name + "对应的图片为" + targetFile.getName());
//				String[] split = targetFile.getName().split("\\.");
				json.put("imgUrl", targetFile.getName());
			}else{
				System.out.println("老师" + name + "对应多个图片!!!!!!!!" + Arrays.toString(equalFile));
			}
		}
		System.out.println("没有图片的老师名字" + list);
		//重新覆盖json串
		updateJsonFile(jsonArr);
	}
	
	private void updateJsonFile(JSONArray jsonArr) throws IOException{
		String filePath = this.getClass().getResource("/file/image/all-2017.json").getFile();
		File file = new File(filePath); 
		System.out.println("新的json字符串" + jsonArr.toJSONString());
		System.out.println("新的json串元素数" + jsonArr.size());
		FileUtils.write(file, jsonArr.toJSONString(), "utf-8");
	}
	
	private JSONArray getJsonArray() throws IOException{
		String filePath = this.getClass().getResource("/file/image/all-2017.json").getFile();
		File file = new File(filePath);  
		System.out.println(file.getAbsolutePath());
        String content = FileUtils.readFileToString(file);
        System.out.println("Contents of file: " + content);  
        JSONArray jsonArray = JSON.parseArray(content);
        return jsonArray;
	}
	
	@Test
	public void testFindBigFile(){
		File file = new File(basePath);
		List<File> list = new ArrayList<File>();
		File[] subFiles = file.listFiles();
		for (File subFile : subFiles) {
			long length = subFile.length();
			if(length > 150 * 1024){
				list.add(subFile);
			}
		}
		
		System.out.println("查到" + list.size() + "个大文件");
		for (File file2 : list) {
			System.out.println(file2.getName() + "文件大小为：" + file2.length()/1024 + "kb");
		}
	}
}
