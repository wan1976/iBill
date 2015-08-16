package net.toeach.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * ScrollView中嵌套GridView的解决方案
 *
 * @author Administrator
 */
public class CustomGridView extends GridView {
    /**
     * 构造函数
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 构造函数
     *
     * @param context
     * @param attrs
     */
    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 构造函数
     *
     * @param context
     */
    public CustomGridView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
