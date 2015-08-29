package net.toeach.ibill.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

import com.lidroid.xutils.util.LogUtils;

import net.toeach.common.utils.BusProvider;
import net.toeach.ibill.model.BillEvent;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class NetworkStateService extends Service {
    private ConnectivityManager mConnMgr;
    private EventBus mBus;
    /**
     * 广播接收器
     */
    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            BillEvent event = getNetworkState();
            mBus.post(event);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        LogUtils.d("onCreate");
        super.onCreate();

        mBus = BusProvider.getInstance();
        mBus.register(this);//注册EventBus服务

        mConnMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // 动态注册广播
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
    }

    @Override
    public void onDestroy() {
        LogUtils.d("onDestroy");
        super.onDestroy();
        mBus.unregister(this);//注销EventBus服务
    }

    /**
     * 收到网络状态查询请求事件
     *
     * @param event 消息事件
     */
    public void onEvent(BillEvent event) {
        if (event == null) {
            return;
        }
        LogUtils.d(event.toString());
        // 获取事件类型
        int eventType = event.getEventType();
        // 网络状态查询
        if (eventType == BillEvent.EVENT_NET_STAT_QUERY) {
            BillEvent event1 = getNetworkState();
            mBus.post(event1);
        }
    }

    /**
     * 获取当前网络状态
     *
     * @return
     */
    private BillEvent getNetworkState() {
        NetworkInfo netInfo = mConnMgr.getActiveNetworkInfo();
        Map<String, Integer> data = new HashMap<>();
        if (netInfo != null && netInfo.isAvailable()) {// 网络连接
            data.put("state", 0);
        } else {// 网络断开
            data.put("state", 1);
        }

        return new BillEvent(BillEvent.EVENT_NET_STAT_RESULT, data);
    }
}
