package net.toeach.base;

import android.os.Message;

/**
 * 定义基类接口方法
 * com.chengfang.base.IMethod
 * @author 万云  <br/>
 * @version 1.0
 */
public interface IMethod {
	/**
	 * 处理消息
	 * @param message 消息对象
	 */
	void handleMessage(Message message);
	/**
     * 显示toast
     * @param text toast文字内容
     */
	void showToast(String text);
    
    /**
     * 显示toast
     * @param resId toast文字内容资源id
     */
	void showToast(int resId);
    
    /**
     * 判断是否有效网络
     * @return 返回结果
     */
	boolean isAvailableNetWork();
	/**
	 * 初始化SharedPreferences对象 <br/>
	 * @param name 文件名
	 */
	void initSharedPreferences(String name);
	/**
	 * 获取参数值
	 * @param name 参数名称
	 * @param defaultValue 缺省值
	 * @return 返回结果
	 */
	String getPreferenceValue(String name, String defaultValue);
	/**
	 * 设置参数值
	 * @param name 参数名称
	 * @param value 参数值
	 */
	void setPreferenceValue(String name, String value);
	/**
	 * 获取参数值
	 * @param name 参数名称
	 * @param defaultValue 缺省值
	 * @return 返回结果
	 */
	int getPreferenceValue(String name, int defaultValue);
	/**
	 * 设置参数值
	 * @param name 参数名称
	 * @param value 参数值
	 */
	void setPreferenceValue(String name, int value);
	/**
	 * 获取参数值
	 * @param name 参数名称
	 * @param defaultValue 缺省值
	 * @return 返回结果
	 */
	long getPreferenceValue(String name, long defaultValue);
	/**
	 * 设置参数值
	 * @param name 参数名称
	 * @param value 参数值
	 */
	void setPreferenceValue(String name, long value);
	/**
	 * 获取参数值
	 * @param name 参数名称
	 * @param defaultValue 缺省值
	 * @return 返回结果
	 */
	float getPreferenceValue(String name, float defaultValue);
	/**
	 * 设置参数值
	 * @param name 参数名称
	 * @param value 参数值
	 */
	void setPreferenceValue(String name, float value);
	/**
	 * 获取参数值
	 * @param name 参数名称
	 * @param defaultValue 缺省值
	 * @return 返回结果
	 */
	boolean getPreferenceValue(String name, boolean defaultValue);
	/**
	 * 设置参数值
	 * @param name 参数名称
	 * @param value 参数值
	 */
	void setPreferenceValue(String name, boolean value);
	/**
	 * 执行异步任务<br/>
	 * @param command
	 */
	void execute(Runnable command);
}
