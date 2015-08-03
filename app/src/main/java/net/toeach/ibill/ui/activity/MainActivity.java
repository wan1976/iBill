package net.toeach.ibill.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import net.toeach.ibill.R;
import net.toeach.ibill.ui.fragment.BillFragment;
import net.toeach.ibill.ui.fragment.BillRecordFragment;
import net.toeach.ibill.ui.fragment.SettingFragment;

/**
 * 应用主界面
 */
@ContentView(R.layout.main_layout)
public class MainActivity extends BaseActivity {
    @ViewInject(R.id.tab_1)
    private ImageView mTabItem1;// tab1
    @ViewInject(R.id.tab_2)
    private ImageView mTabItem2;// tab2
    @ViewInject(R.id.tab_3)
    private ImageView mTabItem3;// tab3

    private BillRecordFragment mFragment1;
    private BillFragment mFragment2;
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
        mFragment2 = new BillFragment();
        mFragment3 = new SettingFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, mFragment1).commit();
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
        resetTabItem();
        switch (view.getId()) {
            case R.id.tab_1:
                mTabItem1.setSelected(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, mFragment1).commit();
                break;
            case R.id.tab_2:
                mTabItem2.setSelected(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, mFragment2).commit();
                break;
            case R.id.tab_3:
                mTabItem3.setSelected(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, mFragment3).commit();
                break;
        }
    }
}
