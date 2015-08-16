package net.toeach.ibill.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuPopup;

import net.toeach.ibill.R;

/**
 * 账单界面
 */
public class BillFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bill_fragment_layout, container, false);
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
        setTitleValue(R.string.bill_title);// 设置标题名称
        setFuncButton(R.drawable.button_more);// 设置功能按钮
    }

    @Override
    public void doFunction() {
        DroppyMenuPopup.Builder builder = new DroppyMenuPopup.Builder(getActivity(), mBtnFunc);
        DroppyMenuPopup mMenu = builder.fromMenu(R.menu.bill_menu)
                .triggerOnAnchorClick(false)
                .setOnClick(new DroppyClickCallbackInterface() {
                    @Override
                    public void call(View v, int id) {
                        switch (id) {
                            case R.id.menu_1:// 月账单
                                break;
                            case R.id.menu_2:// 自定义账单
                                break;
                        }
                    }
                })
                .build();
        mMenu.show();
    }
}
