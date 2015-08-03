package net.toeach.ibill.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.toeach.base.TBaseFragment;
import net.toeach.ibill.Constants;
import net.toeach.ibill.R;

/**
 * Fragment基类
 */
public abstract class BaseFragment extends TBaseFragment {
    //    private CustomProgressBar mProgressDialog;// 进度对话框
    protected View mBtnBack;// 返回按钮
    protected TextView mTitle;// 标题文字
    protected View mBtnFunc;// 功能按钮

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSharedPreferences(Constants.KEY_PREF_NAME);
        initProgressDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
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
//        mProgressDialog = new CustomProgressBar(getActivity());
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
     * 抽象方法，由子类来实现功能按钮的点击事件
     */
    public abstract void doFunction();
}
