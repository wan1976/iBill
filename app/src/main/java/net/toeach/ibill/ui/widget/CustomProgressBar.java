package net.toeach.ibill.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import net.toeach.ibill.R;

/**
 * 自定义进度框
 */
public class CustomProgressBar extends Dialog {
    private Animation mRotateAnim;

    public CustomProgressBar(Context context) {
        super(context, R.style.progress_dialog);
        setCancelable(true);
        setCanceledOnTouchOutside(false);

        // 旋转动画
        mRotateAnim = AnimationUtils.loadAnimation(context, R.anim.anim_rotate);
        mRotateAnim.setInterpolator(new LinearInterpolator());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_progress_bar);
    }

    @Override
    public void show() {
        super.show();

        View circle = findViewById(R.id.img_circle);
        circle.startAnimation(mRotateAnim);
    }
}
