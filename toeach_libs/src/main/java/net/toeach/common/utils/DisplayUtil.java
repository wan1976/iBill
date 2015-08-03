package net.toeach.common.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DisplayUtil {
	/**
	 * dp 到 px 转换
	 * @param context 上下文对象
	 * @param dp dp数值
	 * @return px数值
	 */
	public static int dpToPx(final Context context, final float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) ((dp * scale) + 0.5f);
	}

	/**
	 * 获取屏幕宽度
	 * @param context 上下文对象
	 * @return 屏幕宽度
	 */
	public static int getScreenWidth(final Context context) {
		if (context == null) {
			return 0;
		}
		return getDisplayMetrics(context).widthPixels;
	}

	/**
	 * 获取DisplayMetrics对象
	 * @param context 上下文对象
	 * @return DisplayMetrics对象
	 */
	public static DisplayMetrics getDisplayMetrics(final Context context) {
		final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		final DisplayMetrics metrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}
}
