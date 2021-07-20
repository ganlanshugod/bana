/**
* @Company weipu   
* @Title: SimpleTransformTest.java 
* @Package org.bana.common.util.etl 
* @author Liu Wenjie   
* @date 2015-1-8 下午10:10:07 
* @version V1.0   
*/ 
package org.bana.common.util.etl;

import org.junit.Ignore;
import org.junit.Test;

/** 
 * @ClassName: SimpleTransformTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
public class SimpleTransformTest {

	/**
	 * Test method for {@link org.bana.common.util.etl.SimpleTransform#execute()}.
	 * @throws Exception 
	 */
	@Test
	@Ignore
	public void testExecute() throws Exception {
		SimpleTransform tf = new SimpleTransform();
		tf.setConfigPath("/etl/testConfig.xml");
		tf.execute();
	}

}
