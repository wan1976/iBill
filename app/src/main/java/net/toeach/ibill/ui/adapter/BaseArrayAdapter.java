package net.toeach.ibill.ui.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import net.toeach.ibill.R;

/**
 * Adapter基类
 * <br/>
 * com.xiesi.demo.ui.adapter.BaseArrayAdapter
 *
 * @param <T>
 * @author 万云  <br/>
 * @version 1.0
 * @callLogDate 2014-10-28 上午10:32:21
 */
public abstract class BaseArrayAdapter<T> extends ArrayAdapter<T> {
    protected DisplayImageOptions.Builder mOptions;

    public BaseArrayAdapter(Context context, int resource) {
        super(context, resource);

        // ImageLoader 的图片显示参数
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .showImageOnFail(R.drawable.ic_launcher)
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher);
    }

}
