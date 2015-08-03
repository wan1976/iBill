package net.toeach.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络工具类<br/>
 * net.toeach.common.utils.ImageUtil
 * @author 万云  <br/>
 * @version 1.0
 * @date 2015-3-9 上午10:38:08
 */
public class NetworkUtil {
	/** Network type is unknown */
	public static final int NETWORK_TYPE_UNKNOWN = 0;
	/** Current network is GPRS */
	public static final int NETWORK_TYPE_GPRS = 1;
	/** Current network is EDGE */
	public static final int NETWORK_TYPE_EDGE = 2;
	/** Current network is UMTS */
	public static final int NETWORK_TYPE_UMTS = 3;
	/** Current network is CDMA: Either IS95A or IS95B*/
	public static final int NETWORK_TYPE_CDMA = 4;
	/** Current network is EVDO revision 0*/
	public static final int NETWORK_TYPE_EVDO_0 = 5;
	/** Current network is EVDO revision A*/
	public static final int NETWORK_TYPE_EVDO_A = 6;
	/** Current network is 1xRTT*/
	public static final int NETWORK_TYPE_1xRTT = 7;
	/** Current network is HSDPA */
	public static final int NETWORK_TYPE_HSDPA = 8;
	/** Current network is HSUPA */
	public static final int NETWORK_TYPE_HSUPA = 9;
	/** Current network is HSPA */
	public static final int NETWORK_TYPE_HSPA = 10;
	/** Current network is iDen */
	public static final int NETWORK_TYPE_IDEN = 11;
	/** Current network is EVDO revision B*/
	public static final int NETWORK_TYPE_EVDO_B = 12;
	/** Current network is LTE */
	public static final int NETWORK_TYPE_LTE = 13;
	/** Current network is eHRPD */
	public static final int NETWORK_TYPE_EHRPD = 14;
	/** Current network is HSPA+ */
	public static final int NETWORK_TYPE_HSPAP = 15;
	
	public static final int NETWORK_UNKNOWN = 0;
	public static final int NETWORK_WIFI = 1;
	public static final int NETWORK_2_G = 2;
	public static final int NETWORK_3_G = 3;
	public static final int NETWORK_4_G = 4;
	
	/**
	 * 获取当前网络类型
	 * @param context 上下文对象
	 * @return 网络类型
	 */
	public static int getNetworkClass(Context context) {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = mConnectivityManager.getActiveNetworkInfo();
        if (ni == null || !ni.isConnectedOrConnecting()) {
        	return NETWORK_UNKNOWN;
        }
        
        if (ni.getType() == ConnectivityManager.TYPE_WIFI) {
        	return NETWORK_WIFI;
        }
        
        if(ni.getType() == ConnectivityManager.TYPE_MOBILE) {
    		switch (ni.getSubtype()) {
    		case NETWORK_TYPE_GPRS:
    		case NETWORK_TYPE_EDGE:
    		case NETWORK_TYPE_CDMA:
    		case NETWORK_TYPE_1xRTT:
    		case NETWORK_TYPE_IDEN:
    			return NETWORK_2_G;
    		case NETWORK_TYPE_UMTS:
    		case NETWORK_TYPE_EVDO_0:
    		case NETWORK_TYPE_EVDO_A:
    		case NETWORK_TYPE_HSDPA:
    		case NETWORK_TYPE_HSUPA:
    		case NETWORK_TYPE_HSPA:
    		case NETWORK_TYPE_EVDO_B:
    		case NETWORK_TYPE_EHRPD:
    		case NETWORK_TYPE_HSPAP:
    			return NETWORK_3_G;
    		case NETWORK_TYPE_LTE:
    			return NETWORK_4_G;
    		}
        }
        
        return NETWORK_UNKNOWN;
	}
	
	/**
	 * 获取当前网络类型名称
	 * @param networkClass 网络类型
	 * @return 网络类型名称
	 */
	public static String getNetworkClassName(int networkClass) {
		switch (networkClass) {
		case NETWORK_WIFI:
			return "WIFI";
		case NETWORK_2_G:
			return "2G";
		case NETWORK_3_G:
			return "3G";
		case NETWORK_4_G:
			return "4G";
		default:
			return "未知";
		}
	}
}
