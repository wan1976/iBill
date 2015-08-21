package net.toeach.ibill.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.toeach.ibill.R;

/**
 * 帮助界面
 */
@ContentView(R.layout.help_layout)
public class HelpActivity extends BaseActivity {
    @ViewInject(R.id.webview)
    private WebView mWebView;

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
        setTitleValue(R.string.help_title);

        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("utf8");
        mWebView.loadUrl("file:///android_asset/help.html");

//        mWebView.setWebViewClient(new WebViewClient() {
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                handler.proceed();  // 接受所有网站的证书
//            }
//        });
//        mWebView.loadUrl("https://honglinktech.com/shareApp/LBS.php?Address=广东省东莞市厚街镇博民街");
    }
}
