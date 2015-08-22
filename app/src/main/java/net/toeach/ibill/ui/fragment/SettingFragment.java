package net.toeach.ibill.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import net.toeach.ibill.Constants;
import net.toeach.ibill.R;
import net.toeach.ibill.model.BillEvent;
import net.toeach.ibill.ui.activity.AboutActivity;
import net.toeach.ibill.ui.activity.BillCategoryListActivity;
import net.toeach.ibill.ui.activity.FeedbackActivity;
import net.toeach.ibill.ui.activity.HelpActivity;

import java.util.Map;

/**
 * 设置界面
 */
public class SettingFragment extends BaseFragment {
    @ViewInject(R.id.new_version)
    private ImageView mNewVersion;// 新版本提示

    private boolean mNetworkAvailable;// 网络是否可用

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment_layout, container, false);
        ViewUtils.inject(this, view);

        initView(view);
        init();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 查询网络状态
        mBus.post(new BillEvent(BillEvent.EventType.EVENT_NET_STAT_QUERY, null));
    }

    @Override
    public void handleMessage(Message message) {
    }

    /**
     * 接收到事件通知
     *
     * @param event
     */
    @Override
    public void onEvent(BillEvent event) {
        if (event == null) {
            return;
        }
        LogUtils.d(event.toString());

        // 获取事件类型
        BillEvent.EventType eventType = event.getEventType();
        // 重新加载数据
        if (eventType.equals(BillEvent.EventType.EVENT_UPDATE)) {
            Map<String, String> data = (Map<String, String>) event.getData();
            if (data != null) {
                String flag = data.get("show_flag");
                if ("true".equals(flag)) {
                    mNewVersion.setVisibility(View.VISIBLE);
                } else {
                    mNewVersion.setVisibility(View.GONE);
                }
            }
        }

        super.onEvent(event);
    }

    @Override
    public void doFunction() {
    }

    @Override
    public void onConnect() {
        mNetworkAvailable = true;
    }

    @Override
    public void onDisconnect() {
        mNetworkAvailable = false;
    }

    private void init() {
        hideBackButton();// 隐藏返回按钮
        setTitleValue(R.string.setting_title);// 设置标题名称
        hideFuncButton();// 隐藏功能按钮
        if (getPreferenceValue(Constants.KEY_NEW_VERSION, false)) {
            mNewVersion.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Item点击事件
     *
     * @param view 点击对象
     */
    @OnClick({R.id.btn_category, R.id.btn_feedback, R.id.btn_help, R.id.btn_update, R.id.btn_about})
    private void onPanelItemClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_category: {
                Intent i = new Intent(getActivity(), BillCategoryListActivity.class);
                getActivity().startActivity(i);
                break;
            }
            case R.id.btn_feedback: {
                Intent i = new Intent(getActivity(), FeedbackActivity.class);
                getActivity().startActivity(i);
                break;
            }
            case R.id.btn_help: {
                Intent i = new Intent(getActivity(), HelpActivity.class);
                getActivity().startActivity(i);
                break;
            }
            case R.id.btn_update: {
                doUpdate();
                break;
            }
            case R.id.btn_about: {
                Intent i = new Intent(getActivity(), AboutActivity.class);
                getActivity().startActivity(i);
                break;
            }
        }
    }

    /**
     * 升级
     */
    private void doUpdate() {
        if (!mNetworkAvailable) {
            showToast(R.string.network_not_available);
            return;
        }
        showProgressDialog();

        UmengUpdateAgent.update(getActivity());// 检查新版本
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int status, UpdateResponse updateResponse) {
                dismissProgressDialog();
                if (UpdateStatus.Yes == status) {// 有更新
                    setPreferenceValue(Constants.KEY_NEW_VERSION, true);
                    mNewVersion.setVisibility(View.VISIBLE);
                }
            }
        });
        UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {
            @Override
            public void onClick(int status) {
                if (UpdateStatus.Update == status) {
                    setPreferenceValue(Constants.KEY_NEW_VERSION, false);
                    mNewVersion.setVisibility(View.GONE);
                }
            }
        });
    }
}
