package net.toeach.ibill.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;

import net.toeach.base.TBaseActivity;
import net.toeach.base.TException;
import net.toeach.common.utils.BusProvider;
import net.toeach.ibill.Constants;
import net.toeach.ibill.R;
import net.toeach.ibill.model.BillEvent;
import net.toeach.ibill.ui.widget.NotificationBox;

import de.greenrobot.event.EventBus;

/**
 * Activity抽象基类，根据当前项目特点定义基础方法。
 */
public abstract class BaseActivity extends TBaseActivity {
    protected View mBtnFunc;// 功能按钮
    protected EventBus mBus;
    //    private CustomProgressBar mProgressDialog;// 进度对话框
    private View mBtnBack;// 返回按钮
    private TextView mTitle;// 标题文字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSharedPreferences(Constants.KEY_PREF_NAME);
        initProgressDialog();

        mTitle = (TextView) findViewById(R.id.txt_title);
        // 返回按钮点击事件
        mBtnBack = findViewById(R.id.btn_back);
        if (mBtnBack != null) {
            mBtnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doBack();
                }
            });
        }
        // 功能按钮点击事件
        mBtnFunc = findViewById(R.id.btn_func);
        if (mBtnFunc != null) {
            mBtnFunc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doFunction();
                }
            });
        }

        mBus = BusProvider.getInstance();
        mBus.register(this);//注册
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
        mBus.unregister(this);//注销
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 隐藏软键盘，直到使用者确实碰了编辑框
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
     * 隐藏功能按钮
     */
    protected void hideFuncButton() {
        if (mBtnFunc != null) {
            mBtnFunc.setVisibility(View.GONE);
        }
    }

    /**
     * 显示功能按钮
     */
    protected void showFuncButton() {
        if (mBtnFunc != null) {
            mBtnFunc.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化进度对话框
     *
     * @author 万云
     */
    protected void initProgressDialog() {
        // 初始化对话框
//        mProgressDialog = new CustomProgressBar(this);
//        mProgressDialog.setCancelable(true);
    }

    /**
     * 显示等待对话框
     */
    protected void showProgressDialog() {
        // 显示进度对话框
//        mProgressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    protected void dismissProgressDialog() {
        // 关闭进度对话框
//        if (mProgressDialog != null && mProgressDialog.isShowing()) {
//            mProgressDialog.dismiss();
//        }
    }

    /**
     * 显示错误信息
     *
     * @param logoResId    图标
     * @param messageResId 内容
     */
    protected void showNotificationBox(int anchorId, int logoResId, int messageResId) {
        View anchor = findViewById(anchorId);
        NotificationBox box = new NotificationBox(this);
        box.showBox(handler, anchor, logoResId, messageResId);
    }

    /**
     * 显示错误信息
     *
     * @param logoResId 图标
     * @param message   内容
     */
    protected void showNotificationBox(int anchorId, int logoResId, String message) {
        View anchor = findViewById(anchorId);
        NotificationBox box = new NotificationBox(this);
        box.showBox(handler, anchor, logoResId, message);
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

    /**
     * 返回按钮的点击事件
     */
    protected void doBack() {
        finish();
    }
    /**
     * 抽象方法，由子类来实现功能按钮的点击事件
     */
    public abstract void doFunction();
}
