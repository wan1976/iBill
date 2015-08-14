package net.toeach.ibill.ui.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.EditText;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.toeach.ibill.R;

/**
 * 关于界面
 */
@ContentView(R.layout.about_layout)
public class AboutActivity extends BaseActivity {
    @ViewInject(R.id.version)
    private EditText mTxtVersion;// 当前应用版本名称

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
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            mTxtVersion.setText(info.versionName);
        } catch (Exception e) {
        }
    }
}
