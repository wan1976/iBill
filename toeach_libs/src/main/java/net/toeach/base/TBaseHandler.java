package net.toeach.base;

import java.lang.ref.WeakReference;

import com.lidroid.xutils.util.LogUtils;

import android.os.Handler;
import android.os.Message;

/**
 * handler基类
 * com.chengfang.base.TBaseHandler
 * @author 万云  <br/>
 * @version 1.0
 */
public class TBaseHandler extends Handler {
	private WeakReference<IMethod> iMethods;
	
    public TBaseHandler(IMethod iMethod) {
    	iMethods = new WeakReference<IMethod>(iMethod);
    }
    
    @Override 
    public void handleMessage(Message message){ 
    	int what = message.what;
    	LogUtils.d("what:" + what);
    	IMethod iMethod = iMethods.get();
		if (iMethod == null) {
			return;
		}
		iMethod.handleMessage(message);
    }
}
