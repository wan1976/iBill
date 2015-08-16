package net.toeach.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Fragment基类，定义了通用变量和常用方法，所有Fragment都必须继承它。
 * com.chengfang.base.TBaseFragment
 *
 * @author 万云  <br/>
 * @version 1.0
 */
public abstract class TBaseFragment extends Fragment implements IMethod {
    protected TBaseHandler handler;// handler对象
    private IProxy proxy;// 代理对象

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new TBaseHandler(this);// 初始化Handler对象
        proxy = new ProxyImpl(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        handler = null;
        proxy = null;
    }

    /**
     * 显示toast
     *
     * @param text toast文字内容
     */
    public void showToast(String text) {
        proxy.showToast(text);
    }

    /**
     * 显示toast
     *
     * @param resId toast文字内容资源id
     */
    public void showToast(int resId) {
        proxy.showToast(resId);
    }

    /**
     * 判断是否有效网络
     *
     * @return boolean
     */
    public boolean isAvailableNetWork() {
        return proxy.isAvailableNetWork();
    }

    /**
     * 初始化SharedPreferences对象 <br/>
     */
    public void initSharedPreferences(String name) {
        proxy.initSharedPreferences(name);
    }

    /**
     * 获取参数值
     *
     * @param name         参数名称
     * @param defaultValue 缺省值
     * @return 返回String结果值
     */
    public String getPreferenceValue(String name, String defaultValue) {
        return proxy.getPreferenceValue(name, defaultValue);
    }

    /**
     * 设置参数值
     *
     * @param name  参数名称
     * @param value 参数值
     */
    public void setPreferenceValue(String name, String value) {
        proxy.setPreferenceValue(name, value);
    }

    /**
     * 获取参数值
     *
     * @param name         参数名称
     * @param defaultValue 缺省值
     * @return 返回int结果值
     */
    public int getPreferenceValue(String name, int defaultValue) {
        return proxy.getPreferenceValue(name, defaultValue);
    }

    /**
     * 设置参数值
     *
     * @param name  参数名称
     * @param value 参数值
     */
    public void setPreferenceValue(String name, int value) {
        proxy.setPreferenceValue(name, value);
    }

    /**
     * 获取参数值
     *
     * @param name         参数名称
     * @param defaultValue 缺省值
     * @return 返回long结果值
     */
    public long getPreferenceValue(String name, long defaultValue) {
        return proxy.getPreferenceValue(name, defaultValue);
    }

    /**
     * 设置参数值
     *
     * @param name  参数名称
     * @param value 参数值
     */
    public void setPreferenceValue(String name, long value) {
        proxy.setPreferenceValue(name, value);
    }

    /**
     * 获取参数值
     *
     * @param name         参数名称
     * @param defaultValue 缺省值
     * @return 返回float结果值
     */
    public float getPreferenceValue(String name, float defaultValue) {
        return proxy.getPreferenceValue(name, defaultValue);
    }

    /**
     * 设置参数值
     *
     * @param name  参数名称
     * @param value 参数值
     */
    public void setPreferenceValue(String name, float value) {
        proxy.setPreferenceValue(name, value);
    }

    /**
     * 获取参数值
     *
     * @param name         参数名称
     * @param defaultValue 缺省值
     * @return 返回boolean结果值
     */
    public boolean getPreferenceValue(String name, boolean defaultValue) {
        return proxy.getPreferenceValue(name, defaultValue);
    }

    /**
     * 设置参数值
     *
     * @param name  参数名称
     * @param value 参数值
     */
    public void setPreferenceValue(String name, boolean value) {
        proxy.setPreferenceValue(name, value);
    }

    /**
     * 执行异步任务<br/>
     *
     * @param command Runnable子任务
     */
    public void execute(Runnable command) {
        proxy.execute(command);
    }
}
