package net.toeach.ibill.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.view.annotation.ContentView;
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
import net.toeach.ibill.ui.fragment.BillFormFragment;
import net.toeach.ibill.ui.fragment.BillRecordFragment;
import net.toeach.ibill.ui.fragment.SettingFragment;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 应用主界面
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewInject(R.id.tab_1)
    private ImageView mTabItem1;// tab1
    @ViewInject(R.id.tab_2)
    private ImageView mTabItem2;// tab2
    @ViewInject(R.id.tab_3)
    private ImageView mTabItem3;// tab3

    private BillRecordFragment mFragment1;
    private BillFormFragment mFragment2;
    private SettingFragment mFragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void handleMessage(Message message) {
        super.handleMessage(message);
    }

    @Override
    public void doFunction() {
        // we do nothing here
    }

    /**
     * 初始化工作
     */
    private void init() {
        mTabItem1.setSelected(true);

        mFragment1 = new BillRecordFragment();
        mFragment2 = new BillFormFragment();
        mFragment3 = new SettingFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, mFragment1).commit();

        UmengUpdateAgent.update(this);// 检查新版本
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int status, UpdateResponse updateResponse) {
                if (UpdateStatus.Yes == status) {// 有更新
                    dismissProgressDialog();
                    setPreferenceValue(Constants.KEY_NEW_VERSION, true);

                    // 通知升级标志显示
                    Map<String, String> data = new HashMap<>();
                    data.put("show_flag", "true");
                    BillEvent event = new BillEvent(BillEvent.EVENT_UPDATE, data);
                    EventBus.getDefault().post(event);
                }
            }
        });
        UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {
            @Override
            public void onClick(int status) {
                if (UpdateStatus.Update == status) {
                    setPreferenceValue(Constants.KEY_NEW_VERSION, false);

                    // 通知升级标志隐藏
                    Map<String, String> data = new HashMap<>();
                    data.put("show_flag", "false");
                    BillEvent event = new BillEvent(BillEvent.EVENT_UPDATE, data);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    /**
     * 重置TabItem状态
     */
    private void resetTabItem() {
        mTabItem1.setSelected(false);
        mTabItem2.setSelected(false);
        mTabItem3.setSelected(false);
    }

    /**
     * TabItem点击事件
     *
     * @param view 点击对象
     */
    @OnClick({R.id.tab_1, R.id.tab_2, R.id.tab_3})
    private void onTabItemClicked(View view) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        resetTabItem();
        switch (view.getId()) {
            case R.id.tab_1:
                mTabItem1.setSelected(true);
                ft.replace(R.id.fragment_content, mFragment1);
                break;
            case R.id.tab_2:
                mTabItem2.setSelected(true);
                ft.replace(R.id.fragment_content, mFragment2);
                break;
            case R.id.tab_3:
                mTabItem3.setSelected(true);
                ft.replace(R.id.fragment_content, mFragment3);
                break;
        }
        ft.commit();
    }
}
