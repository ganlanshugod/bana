/**
* @Company 青鸟软通   
* @Title: ScriptUtil.java 
* @Package org.bana.common.util.basic 
* @author Liu Wenjie   
* @date 2015-8-11 下午12:55:27 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

/** 
 * @ClassName: ScriptUtil 
 * @Description: script引擎计算表达式
 *  
 */
import java.util.Date;
import java.util.List;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptUtilTest {

    public static void main(String[] args) throws ScriptException {
//    	list();
//    	runScript("(1+2+3)*2/0.5");
//    	runScript("print ('not a math script')");
    	//valueBind();
    	Date date = new Date(1439265600000l);
//    	System.out.println(date);
//    	System.out.println(DateUtil.toString(date, "yyyy-MM-dd HH:mm:ss"));
    	System.out.println(DateUtil.toString(DateUtil.getTodayBegin(),"yyyy-MM-dd HH:mm:ss"));
	}

    public static void list() {
        // create ScriptEngineManager
        ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> factoryList = manager.getEngineFactories();
        for (ScriptEngineFactory factory : factoryList) {
            System.out.println(factory.getEngineName());
            System.out.println(factory.getEngineVersion());
            System.out.println(factory.getLanguageName());
            System.out.println(factory.getLanguageVersion());
            System.out.println(factory.getExtensions());
            System.out.println(factory.getMimeTypes());
            System.out.println(factory.getNames());
        }
    }

    public static void runScript(String script) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        try {
            System.out.println(engine.eval(script));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public static void valueBind() throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        engine.put("$yesterdayMillisecond", new Date().getTime());
        engine.put("b", 5);

        Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
        Object a = bindings.get("$yesterdayMillisecond");
        Object b = bindings.get("b");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        Object result = engine.eval("c = $yesterdayMillisecond - (1000 * 60 * 60 * 24);");
        System.out.println(result.getClass());
        System.out.println("a + b = " + (Double)result);
        System.out.println(Math.round((Double)result));
        System.out.println(Long.parseLong(a.toString()) - (1000 * 60 * 60 * 24));
    }
    
}
