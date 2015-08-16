package net.toeach.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 代理类实现
 * com.chengfang.base.ProxyImpl
 *
 * @author 万云  <br/>
 * @version 1.0
 */
public class ProxyImpl implements IProxy {
    private Context context;// 上下文对象
    private SharedPreferences pref;// 配置文件对象
    private ExecutorService threadPool;// 线程池

    /**
     * 构造函数
     *
     * @param context 上下文对象
     */
    public ProxyImpl(Context context) {
        this.context = context;
        threadPool = Executors.newCachedThreadPool();
    }

    /**
     * 显示toast
     *
     * @param text toast文字内容
     */
    public void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示toast
     *
     * @param resId toast文字内容资源id
     */
    public void showToast(int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 判断是否有效网络
     *
     * @return 返回结果
     */
    public boolean isAvailableNetWork() {
        boolean flag = false;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null) {
            NetworkInfo[] infos = connManager.getAllNetworkInfo();
            if (infos != null) {
                for (NetworkInfo info : infos) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 初始化SharedPreferences对象 <br/>
     *
     * @param name 文件名
     */
    public void initSharedPreferences(String name) {
        // 初始化getSharedPreferences对象
        pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * 获取参数值
     *
     * @param name         参数名称
     * @param defaultValue 缺省值
     * @return 返回结果
     */
    public String getPreferenceValue(String name, String defaultValue) {
        return pref.getString(name, defaultValue);
    }

    /**
     * 设置参数值
     *
     * @param name  参数名称
     * @param value 参数值
     */
    public void setPreferenceValue(String name, String value) {
        Editor editor = pref.edit();
        editor.putString(name, value);
        editor.apply();
    }

    /**
     * 获取参数值
     *
     * @param name         参数名称
     * @param defaultValue 缺省值
     * @return 返回结果
     */
    public int getPreferenceValue(String name, int defaultValue) {
        return pref.getInt(name, defaultValue);
    }

    /**
     * 设置参数值
     *
     * @param name  参数名称
     * @param value 参数值
     */
    public void setPreferenceValue(String name, int value) {
        Editor editor = pref.edit();
        editor.putInt(name, value);
        editor.apply();
    }

    /**
     * 获取参数值
     *
     * @param name         参数名称
     * @param defaultValue 缺省值
     * @return 返回结果
     */
    public long getPreferenceValue(String name, long defaultValue) {
        return pref.getLong(name, defaultValue);
    }

    /**
     * 设置参数值
     *
     * @param name  参数名称
     * @param value 参数值
     */
    public void setPreferenceValue(String name, long value) {
        Editor editor = pref.edit();
        editor.putLong(name, value);
        editor.apply();
    }

    /**
     * 获取参数值
     *
     * @param name         参数名称
     * @param defaultValue 缺省值
     * @return 返回结果
     */
    public float getPreferenceValue(String name, float defaultValue) {
        return pref.getFloat(name, defaultValue);
    }

    /**
     * 设置参数值
     *
     * @param name  参数名称
     * @param value 参数值
     */
    public void setPreferenceValue(String name, float value) {
        Editor editor = pref.edit();
        editor.putFloat(name, value);
        editor.apply();
    }

    /**
     * 获取参数值
     *
     * @param name         参数名称
     * @param defaultValue 缺省值
     * @return 返回结果
     */
    public boolean getPreferenceValue(String name, boolean defaultValue) {
        return pref.getBoolean(name, defaultValue);
    }

    /**
     * 设置参数值
     *
     * @param name  参数名称
     * @param value 参数值
     */
    public void setPreferenceValue(String name, boolean value) {
        Editor editor = pref.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    /**
     * 执行异步任务<br/>
     *
     * @param command 异步子任务
     */
    public void execute(Runnable command) {
        threadPool.execute(command);
    }
}
