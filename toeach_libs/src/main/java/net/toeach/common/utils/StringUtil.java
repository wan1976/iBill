package net.toeach.common.utils;

import java.util.Random;
import java.util.UUID;

/**
 * 字符工具类<br/>
 * net.toeach.common.utils.StringUtil
 * @author 万云  <br/>
 * @version 1.0
 * @date 2015-3-5 下午3:05:22
 */
public class StringUtil {
	/**
	* 判断字符是否为中文
	* @param s
	* @return
	*/
	public static boolean containsChinese(String s) {
		if (null == s || "".equals(s.trim())) {
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			if (isChineseChar(s.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 是否中文字符 <br/>
	 * @param a
	 * @return 
	 * @author 万云  <br/>
	 * @version 1.0
	 * @date 2015-3-5 下午3:06:03
	 */
	public static boolean isChineseChar(char a) {
		int v = (int) a;
		return (v >= 19968 && v <= 171941);
	}
	
	/**
	 * 判断是否为英文字符
	 * @param c
	 * @return
	 */
	public static final boolean isAlpha(char c) {
		return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
	}

	/**
	 * 判断是否为数字
	 * @param c
	 * @return
	 */
	public static final boolean isAlphaNum(char c) {
		return isAlpha(c) || (c >= '0' && c <= '9');
	}
	
	/**
	 * 生成随机字符串<br/>
	 * @param length 表示生成字符串的长度
	 * @return 
	 * @author 万云  <br/>
	 * @version 1.0
	 * @date 2015-3-4 下午3:28:41
	 */
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	} 
	
	/**
	 * 返回UUID字符串 <br/>
	 * @return 
	 * @author 万云  <br/>
	 * @version 1.0
	 * @date 2015-2-9 下午4:41:18
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString(); 
		s = s.replaceAll("-", "");
		return s;
	}
}
