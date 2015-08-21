package net.toeach.ibill;

import java.util.Map;

/**
 * 定义常用静态变量
 */
public class Constants {
    // 系统消息代码
    public final static int MSG_EXCEPTION = 100;// 系统异常错误
    public final static int MSG_ERROR = 200;// 应用错误
    public final static int PAGE_SIZE = 20;// 分页记录数据

    // Preference 参数变量名
    public final static String KEY_PREF_NAME = "ibill_pref";
    public final static String KEY_RUN_GUIDE = "key_run_guide";
    public final static String KEY_MOBILE = "key_mobile";
    public final static String KEY_NEW_VERSION = "key_new_version";
    public final static String KEY_SECRET_KEY = "secretkey";
    public final static String SESSION_ID = "session_id";

    // 分类图标
    public static Map<String, Integer> CAT_ICONS;
    // 系统错误
    public static Map<Integer, String> ERRORS;
}
