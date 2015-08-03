package net.toeach.ibill.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import net.toeach.ibill.R;

/**
 * 消息通知
 */
public class NotificationBox {
    private Context mContext;
    private PopupWindow mWindow = null;

    public NotificationBox(Context context) {
        mContext = context;
    }

    /**
     * 弹出菜单
     *
     * @param handler
     * @param anchor
     * @param logoResId
     * @param messageResId
     */
    public void showBox(Handler handler, View anchor, int logoResId, int messageResId) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.notification_box_layout, null);
        ImageView logo = (ImageView) root.findViewById(R.id.logo);
        TextView message = (TextView) root.findViewById(R.id.message);
        logo.setImageResource(logoResId);
        message.setText(messageResId);
        showWindow(handler, anchor, root);
    }

    /**
     * 弹出菜单
     *
     * @param handler
     * @param anchor
     * @param logoResId
     * @param content
     */
    public void showBox(Handler handler, View anchor, int logoResId, String content) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.notification_box_layout, null);
        ImageView logo = (ImageView) root.findViewById(R.id.logo);
        TextView message = (TextView) root.findViewById(R.id.message);
        logo.setImageResource(logoResId);
        message.setText(content);
        showWindow(handler, anchor, root);
    }

    /**
     * @param handler
     * @param anchor
     * @param root
     */
    private void showWindow(Handler handler, View anchor, View root) {
        mWindow = new PopupWindow(root, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mWindow.setOutsideTouchable(true);
        mWindow.setFocusable(true);
        mWindow.setAnimationStyle(R.style.AppTheme_UI_NotificationBox_Animation);

        if (anchor != null) {
            mWindow.update();
            mWindow.showAsDropDown(anchor);
            if (handler != null) {
                // 5秒后关闭
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mWindow.dismiss();
                        mWindow = null;
                    }
                }, 5000);
            }
        }
    }
}
