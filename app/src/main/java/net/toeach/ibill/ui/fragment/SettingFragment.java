package net.toeach.ibill.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import net.toeach.ibill.R;
import net.toeach.ibill.ui.activity.BillCategoryListActivity;

/**
 * 设置界面
 */
public class SettingFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment_layout, container, false);
        ViewUtils.inject(this, view);

        initView(view);
        init();

        return view;
    }

    @Override
    public void handleMessage(Message message) {
    }

    private void init() {
        hideBackButton();// 隐藏返回按钮
        setTitleValue(R.string.setting_title);// 设置标题名称
        hideFuncButton();// 隐藏功能按钮
    }

    @Override
    public void doFunction() {
    }

    /**
     * Item点击事件
     *
     * @param view 点击对象
     */
    @OnClick({R.id.btn_category, R.id.btn_feedback, R.id.btn_help, R.id.btn_about})
    private void onPanelItemClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_category:
                Intent i = new Intent(getActivity(), BillCategoryListActivity.class);
                getActivity().startActivity(i);
                break;
            case R.id.btn_feedback:
                break;
            case R.id.btn_help:
                break;
            case R.id.btn_about:
                break;
        }
    }
}
