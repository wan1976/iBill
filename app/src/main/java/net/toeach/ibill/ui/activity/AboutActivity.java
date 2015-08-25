package net.toeach.ibill.ui.activity;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.toeach.ibill.R;

/**
 * 关于界面
 */
@ContentView(R.layout.activity_about)
public class AboutActivity extends BaseActivity {
    @ViewInject(R.id.version)
    private TextView mTxtVersion;// 当前应用版本名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void doFunction() {
    }

    /**
     * 初始化工作
     */
    private void init() {
        // 设置标题名称
        setTitleValue(R.string.about_title);

        // 设置版本名
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            mTxtVersion.setText("V" + info.versionName);
            LogUtils.d("code:" + info.versionCode + ", version:" + info.versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
