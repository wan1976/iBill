package net.toeach.ibill.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;

import net.toeach.base.TBaseFragment;
import net.toeach.base.TException;
import net.toeach.common.utils.BusProvider;
import net.toeach.ibill.Constants;
import net.toeach.ibill.R;
import net.toeach.ibill.model.BillEvent;
import net.toeach.ibill.ui.widget.CustomProgressBar;

import de.greenrobot.event.EventBus;

/**
 * Fragment基类
 */
public abstract class BaseFragment extends TBaseFragment {
    protected View mBtnBack;// 返回按钮
    protected TextView mTitle;// 标题文字
    protected View mBtnFunc;// 功能按钮
    protected EventBus mBus;
    private CustomProgressBar mProgressDialog;// 进度对话框

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSharedPreferences(Constants.KEY_PREF_NAME);
        initProgressDialog();

        mBus = BusProvider.getInstance();
        mBus.register(this);//注册
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
        mBus.unregister(this);//注销
    }

    /**
     * 初始化View对象
     *
     * @param view View对象
     */
    protected void initView(View view) {
        mTitle = (TextView) view.findViewById(R.id.txt_title);
        // 返回按钮点击事件
        mBtnBack = view.findViewById(R.id.btn_back);
        if (mBtnBack != null) {
            mBtnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }
        // 功能按钮点击事件
        mBtnFunc = view.findViewById(R.id.btn_func);
        if (mBtnFunc != null) {
            mBtnFunc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doFunction();
                }
            });
        }
    }

    /**
     * 设置标题文字
     *
     * @param title 标题文字
     */
    protected void setTitleValue(String title) {
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    /**
     * 设置标题文字
     *
     * @param resId 资源id
     */
    protected void setTitleValue(int resId) {
        if (mTitle != null) {
            mTitle.setText(resId);
        }
    }

    /**
     * 隐藏返回按钮
     */
    protected void hideBackButton() {
        if (mBtnBack != null) {
            mBtnBack.setVisibility(View.GONE);
        }
    }

    /**
     * 设置功能按钮图标
     *
     * @param resId 资源id
     */
    protected void setFuncButton(int resId) {
        if (mBtnFunc != null) {
            mBtnFunc.setBackgroundResource(resId);
        }
    }

    /**
     * 隐藏返回按钮
     */
    protected void hideFuncButton() {
        if (mBtnFunc != null) {
            mBtnFunc.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化进度对话框
     *
     * @author 万云
     */
    protected void initProgressDialog() {
        // 初始化对话框
        mProgressDialog = new CustomProgressBar(getActivity());
        mProgressDialog.setCancelable(true);
    }

    /**
     * 显示等待对话框
     */
    protected void showProgressDialog() {
        // 显示进度对话框
        mProgressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    protected void dismissProgressDialog() {
        // 关闭进度对话框
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 弹出对话框
     *
     * @param title   标题
     * @param message 信息提示
     */
    protected void showDialog(String title, String message) {
        LogUtils.d("title:" + title + ", message:" + message);
        showToast(message);
    }

    public void onEvent(BillEvent event) {
        if (event == null) {
            return;
        }
        LogUtils.d(event.toString());

        // 获取事件类型
        BillEvent.EventType eventType = event.getEventType();
        // 网络状态查询结果
        if (eventType.equals(BillEvent.EventType.EVENT_NET_STAT_RESULT)) {
            // 网络状态
            int state = (Integer) event.getData().get("state");
            if (state == 1) {// 网络断开
                onDisconnect();
            } else {
                onConnect();
            }
        }
    }

    public void onConnect() {
    }

    public void onDisconnect() {
    }


    @Override
    public void handleMessage(Message message) {
        switch (message.what) {
            case Constants.MSG_EXCEPTION:// 系统异常信息
                /* 提示错误 */
                TException e = (TException) message.obj;
                showDialog("错误", e.getMessage());
                break;
            case Constants.MSG_ERROR:// 应用错误信息
                int errCode = (int) message.obj;
                String errMsg = Constants.ERRORS.get(errCode);
                showDialog("错误", errMsg);
                break;
        }
    }

    /**
     * 抽象方法，由子类来实现功能按钮的点击事件
     */
    public abstract void doFunction();
}
