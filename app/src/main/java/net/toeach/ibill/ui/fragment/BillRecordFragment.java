package net.toeach.ibill.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;

import net.toeach.ibill.R;

/**
 * 账单清单界面
 */
public class BillRecordFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_fragment_layout, container, false);
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
        setTitleValue(R.string.record_title);// 设置标题名称
        setFuncButton(R.drawable.button_add);// 设置功能按钮
    }

    @Override
    public void doFunction() {

    }
}
