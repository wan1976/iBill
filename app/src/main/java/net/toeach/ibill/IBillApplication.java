package net.toeach.ibill;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.util.LogUtils;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;
import java.util.HashMap;

/**
 * 全局对象
 * <br/>
 * com.xiesi.demo.XSApplication
 *
 * @author 万云  <br/>
 * @version 1.0
 */
public class IBillApplication extends Application {
    private static IBillApplication instance;// Application实例引用
    private static DbUtils dbUtils;// 数据库对象

    /**
     * the main context of the Application
     *
     * @return
     */
    public static Context getAppContext() {
        if (instance == null) {
            throw new IllegalStateException("Application has not been created");
        }
        return instance;
    }

    /**
     * 获取数据库对象实例
     *
     * @return
     */
    public final static DbUtils getDbInstance() {
        if (dbUtils == null) {
            dbUtils = DbUtils.create(instance, "ibill.db");
            dbUtils.configAllowTransaction(true);// 事物处理
            dbUtils.configDebug(true);
            dbUtils.getDaoConfig().setDbVersion(1);// 设置数据库版本
            // 用于数据库升级
            dbUtils.getDaoConfig().setDbUpgradeListener(new DbUtils.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbUtils db, int oldVersion, int newVersion) {
                }
            });
        }
        return dbUtils;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d("Init starting...");

        instance = this;
        try {
            // 设置应用缓存目录
            File cacheDir = new File(Environment.getExternalStorageDirectory() + File.separator + "ibill");
            if (cacheDir != null && !cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            LogUtils.d("cache dir:" + cacheDir);

            // 图片加载对象
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                    .denyCacheImageMultipleSizesInMemory()
                    .defaultDisplayImageOptions(options)
                    .diskCache(new LimitedAgeDiskCache(cacheDir, 3 * 24 * 3600))
                    .memoryCache(new UsingFreqLimitedMemoryCache(8 * 1024 * 1024))
                    .threadPoolSize(5)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .writeDebugLogs()
                    .build();
            // Initialize ImageLoader with configuration.
            ImageLoader.getInstance().init(config);

            initCatIcons();
            initErrors();
        } catch (Exception e) {
            LogUtils.e("error", e);
        }
        LogUtils.d("Init finished.");
    }

    /**
     * 当系统内存过低时触发
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogUtils.d("System is running low on memory");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ImageLoader.getInstance().destroy();
        Constants.CAT_ICONS = null;
        Constants.ERRORS = null;
    }

    /**
     * 初始化分类图标数组
     */
    private void initCatIcons() {
        Constants.CAT_ICONS = new HashMap<String, Integer>();
        Constants.CAT_ICONS.put("ic_cat_add", R.mipmap.ic_cat_add);
        Constants.CAT_ICONS.put("ic_cat_001", R.mipmap.ic_cat_1);
        Constants.CAT_ICONS.put("ic_cat_002", R.mipmap.ic_cat_2);
        Constants.CAT_ICONS.put("ic_cat_003", R.mipmap.ic_cat_3);
        Constants.CAT_ICONS.put("ic_cat_004", R.mipmap.ic_cat_4);
        Constants.CAT_ICONS.put("ic_cat_005", R.mipmap.ic_cat_5);
        Constants.CAT_ICONS.put("ic_cat_006", R.mipmap.ic_cat_6);
        Constants.CAT_ICONS.put("ic_cat_007", R.mipmap.ic_cat_7);
        Constants.CAT_ICONS.put("ic_cat_008", R.mipmap.ic_cat_8);
        Constants.CAT_ICONS.put("ic_cat_009", R.mipmap.ic_cat_9);
        Constants.CAT_ICONS.put("ic_cat_010", R.mipmap.ic_cat_10);
        Constants.CAT_ICONS.put("ic_cat_011", R.mipmap.ic_cat_11);
        Constants.CAT_ICONS.put("ic_cat_012", R.mipmap.ic_cat_12);
        Constants.CAT_ICONS.put("ic_cat_013", R.mipmap.ic_cat_13);
        Constants.CAT_ICONS.put("ic_cat_014", R.mipmap.ic_cat_14);
        Constants.CAT_ICONS.put("ic_cat_015", R.mipmap.ic_cat_15);
        Constants.CAT_ICONS.put("ic_cat_016", R.mipmap.ic_cat_16);
        Constants.CAT_ICONS.put("ic_cat_017", R.mipmap.ic_cat_17);
        Constants.CAT_ICONS.put("ic_cat_018", R.mipmap.ic_cat_18);
        Constants.CAT_ICONS.put("ic_cat_019", R.mipmap.ic_cat_19);
        Constants.CAT_ICONS.put("ic_cat_020", R.mipmap.ic_cat_20);
    }

    /**
     * 初始化系统错误数组
     */
    private void initErrors() {
        Constants.ERRORS = new HashMap<Integer, String>();
        Constants.ERRORS.put(1001, getString(R.string.err_1001));
        Constants.ERRORS.put(1002, getString(R.string.err_1002));
        Constants.ERRORS.put(1003, getString(R.string.err_1003));
        Constants.ERRORS.put(1004, getString(R.string.err_1004));
        Constants.ERRORS.put(1005, getString(R.string.err_1005));
        Constants.ERRORS.put(1006, getString(R.string.err_1006));
        Constants.ERRORS.put(1007, getString(R.string.err_1007));
        Constants.ERRORS.put(1008, getString(R.string.err_1008));
        Constants.ERRORS.put(1009, getString(R.string.err_1009));
        Constants.ERRORS.put(1010, getString(R.string.err_1010));
    }
}
