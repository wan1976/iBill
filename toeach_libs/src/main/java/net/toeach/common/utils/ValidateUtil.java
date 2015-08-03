package net.toeach.common.utils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具类<br/>
 * net.toeach.common.utils.ValidateUtil
 * @author 万云  <br/>
 * @version 1.0
 * @date 2015-3-5 下午3:12:49
 */
public class ValidateUtil {
	/**
	 * 验证Email地址<br/>
	 * @param s Email地址
	 * @return 返回判断结果
	 */
	public static boolean isEmail(String s) {
		String regex = "[a-zA-Z][\\w_]+@\\w+(\\.\\w+)+";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		return m.matches();
	}
	
	/**
	 * 验证手机号<br/>
	 * @param s 手机号码
	 * @return 返回判断结果
	 */
	public static boolean isMobile(String s) {
		String regex = "^[1][3,4,5,7,8][0-9]{9}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		return m.matches();
	}
	
	/**
	 * 验证电话号码 <br/>
	 * @param s 电话号码
	 * @return 返回判断结果
	 */
	public static boolean isPhone(String s) {
		if (s.length() > 9) {
			String regex = "\\d{2,5}-\\d{7,8}";// 验证带区号的
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(s);
			return m.matches();
		} else {
			String regex = "^[1-9]{1}[0-9]{5,8}$";// 验证没有区号的
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(s);
			return m.matches();
		}
	}
	
	/**
	 * 验证日期格式 <br/>
	 * @param s 日期字符
	 * @return 返回判断结果
	 */
	public static boolean isDate(String s) {
		try {
			Date d = DateUtil.parseDate(s);
			String date = DateUtil.formatDate(d);
			return s.equals(date);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 验证身份证格式 <br/>
	 * @param s 身份证号码
	 * @return 返回判断结果
	 */
	public static boolean isIDCard(String s) {
		String regex = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		return m.matches();
	}
	
	/**
	 * 验证银行卡 <br/>
	 * 用于验证现行 16 位银联卡现行卡号开头 6 位是 622126～622925 之间的，
	 * 7 到 15位是银行自定义的，可能是发卡分行，发卡网点，发卡序号，第 16 位是校验码。
	 * 16 位卡号校验位采用 Luhm 校验方法计算：1，将未带校验位的 15位卡号从右依次编号 1 到 15，
	 * 位于奇数位号上的数字乘以 22，将奇位乘积的个十位全部相加，再加上所有偶数位上的数字3，
	 * 将加法和加上校验位能被 10整除。
	 * @param s 银行卡号码
	 * @return 返回判断结果
	 */
    public static boolean isBankCard(String s) {
		char bit = getBankCardCheckCode(s.substring(0, s.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return s.charAt(s.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位。
     * @param s 银行卡号码
     * @return 校验位
     */
    private static char getBankCardCheckCode(String s){
        if(s == null || s.trim().length() == 0 || !s.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = s.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;           
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }
}
