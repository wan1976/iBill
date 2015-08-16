package net.toeach.base;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.lidroid.xutils.util.LogUtils;

/**
 * Loader基类
 * com.chengfang.base.TBaseTaskLoader
 *
 * @author 万云  <br/>
 * @version 1.0
 */
public abstract class TBaseTaskLoader<T> extends AsyncTaskLoader<T> {
    protected T mRecords;// 数据对象列表

    /**
     * 构造函数
     *
     * @param context
     */
    public TBaseTaskLoader(Context context) {
        super(context);
    }

    @Override
    public void deliverResult(T data) {
        LogUtils.d("deliverResult()");
        if (isReset()) {
            // An async query came in while the loader is stopped
            if (data != null) {
                data = null;
            }
            return;
        }

        mRecords = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        LogUtils.d("onStartLoading()");
        if (mRecords != null) {
            deliverResult(mRecords);
        }
        if (takeContentChanged() || mRecords == null) {
            forceLoad();
        }
        super.onStartLoading();
    }

    @Override
    protected void onStopLoading() {
        LogUtils.d("onStopLoading()");
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        LogUtils.d("onReset()");
        // Ensure the loader is stopped
        onStopLoading();
        mRecords = null;
    }
}
