/**
 * @Company JBINFO
 * @Title: MapDistanceUtils.java
 * @Package cn.jbinfo.oasis.common.utils
 * @author Chu Linpeng
 * @date 2018年3月17日 上午9:44:46
 * @version V1.0
 */
package org.bana.common.util.basic;

/**
 * @ClassName: MapDistanceUtils
 * @Description: 计算两个经纬度之间的距离
 * @author Chu Linpeng
 */
public class MapDistanceUtils {
	//地球半径
	//private static double EARTH_RADIUS = 6378.137;
    private static double EARTH_RADIUS = 6371.393;
    
    /**
     * Description: 角度弧度计算公式
     * 360度=2π π=Math.PI
     * x度 = x*π/360 弧度
     * @author Chu Linpeng
     * @date 2018年3月17日 上午9:49:37
     * @param degree
     * @return
     */
    private static double getRadian(double degree) {
       return degree * Math.PI / 180.0;
    }
    
    /**
     * Description: 依据经纬度计算两点之间的距离
     * @author Chu Linpeng
     * @date 2018年3月17日 上午9:48:40
     * @param lat1 1点的纬度
     * @param lng1 1点的经度
     * @param lat2 2点的纬度
     * @param lng2 2点的经度
     * @return 距离 单位 米
     */
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {  
       double radLat1 = getRadian(lat1);
       double radLat2 = getRadian(lat2);
       double a = radLat1 - radLat2;
       double b = getRadian(lng1) - getRadian(lng2);
       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
        Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
       s = s * EARTH_RADIUS;
       s = Math.round(s * 1000);
       return s;
    }
}
