package org.bana.common.util.basic;

import org.junit.Test;

public class MapDistanceUtilsTest {

	@Test
	public void test() {
		// 120.3804,36.067326
		// 120.374867,36.076194
		
		//114.147306,22.28664
		//114.145509,22.286464
		
//		114.148213,22.286895
//		114.150872,22.287363
		double lant1 = 114.148213;
		double lng1 = 22.286895;
		
		double lant2 = 114.150872;
		double lng2 = 22.287363;
		
		double i = (2 * 6371 * 1000 * Math.PI) / 360 ;
		System.out.println(i);
		
		// 111319.5
		double getDistance = MapDistanceUtils.GetDistance(lant1, lng1,lant2,lng2);
		System.out.println(getDistance);
		
		double distance2 = Math.sqrt(Math.pow((lant1 - lant2),2)+Math.pow((lng1-lng2),2)) * i;
		System.out.println(distance2);
	}

}
