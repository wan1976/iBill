package net.toeach.ibill.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.toeach.ibill.R;
import net.toeach.ibill.business.BillCategoryManager;
import net.toeach.ibill.model.BillCategory;
import net.toeach.ibill.model.BillEvent;
import net.toeach.ibill.ui.adapter.BillCategoryListItemAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 分类列表界面
 */
@ContentView(R.layout.bill_category_list_layout)
public class BillCategoryListActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    @ViewInject(R.id.cat_list)
    private ListView mListView;// 分类列表
    @ViewInject(R.id.empty_view)
    private TextView mEmptyView;// 无数据提示

    private BillCategoryListItemAdapter mAdapter;// 适配器
    private int mMode;// 0:浏览模式，1:删除模式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BillCategory bean = mAdapter.getItem(position);
        if (bean != null) {
            if (mMode == 0) {// 浏览模式，点击进入编辑界面
                Parcelable wrapped = Parcels.wrap(bean);
                // 跳转到编辑界面
                Intent intent = new Intent(this, BillCategoryModifyActivity.class);
                intent.putExtra("cat", wrapped);
                startActivity(intent);
            } else {// 删除模式，点击勾选记录
                BillCategoryListItemAdapter.ViewHolder viewHolder = (BillCategoryListItemAdapter.ViewHolder) view.getTag();
                viewHolder.checkBox.toggle();
                bean.setChecked(viewHolder.checkBox.isChecked());
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        BillCategory bean = mAdapter.getItem(position);
        if (bean != null) {
            bean.setChecked(true);// 设置选中当前记录
            enterDeleteMode();// 进入删除模式
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {// 返回按键
            if (mMode == 1) {// 当前是删除模式，则进入浏览模式
                enterViewMode();
            } else {// 否则退出当前界面
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void handleMessage(Message message) {
        super.handleMessage(message);
        switch (message.what) {
            case BillCategoryManager.MSG_LIST_SUCCESS:
                List<BillCategory> list = (List<BillCategory>) message.obj;
                if (list != null && list.size() > 0) {
                    mAdapter.setMode(mMode);
                    mAdapter.clear();
                    mAdapter.addAll(list);
                    mAdapter.notifyDataSetChanged();

                    mEmptyView.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);
                } else {
                    mEmptyView.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                }
                break;
            case BillCategoryManager.MSG_DEL_SUCCESS:
                showToast(R.string.category_list_del_success);
                mMode = 0;// 进入浏览模式
                setFuncButton(R.drawable.button_add);
                loadData();
                break;
        }
    }

    @Override
    public void doBack() {
        if (mMode == 1) {// 当前是删除模式，则进入浏览模式
            enterViewMode();
        } else {
            finish();
        }
    }

    @Override
    public void doFunction() {
        if (mMode == 1) {// 当前是删除模式
            new SweetAlertDialog(this)
                    .setTitleText(getString(R.string.category_list_confirm_del))
                    .setConfirmText(getString(R.string.button_yes))
                    .setCancelText(getString(R.string.button_no))
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            doDelete();
                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.cancel();
                        }
                    })
                    .show();
        } else {
            Intent i = new Intent(this, BillCategoryModifyActivity.class);
            startActivity(i);
        }
    }

    /**
     * 接收到事件通知
     *
     * @param event
     */
    public void onEvent(BillEvent event) {
        if (event == null) {
            return;
        }
        LogUtils.d(event.toString());

        // 获取事件类型
        BillEvent.EventType eventType = event.getEventType();
        // 重新加载数据
        if (eventType.equals(BillEvent.EventType.EVENT_RELOAD_CAT)) {
            loadData();
        }

        super.onEvent(event);
    }

    /**
     * 初始化工作
     */
    private void init() {
        setTitleValue(R.string.category_list_title);// 设置标题名称
        setFuncButton(R.drawable.button_add);// 设置功能按钮

        mAdapter = new BillCategoryListItemAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BillCategoryManager.getInstance().getList(handler);
            }
        }).start();
    }

    /**
     * 进入删除模式
     */
    private void enterDeleteMode() {
        mMode = 1;// 进入删除模式
        setFuncButton(R.drawable.button_delete);
        mAdapter.setMode(mMode);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 进入浏览模式
     */
    private void enterViewMode() {
        mMode = 0;// 进入浏览模式
        setFuncButton(R.drawable.button_add);
        // 重新设置选中状态
        for (int i = 0; i < mAdapter.getCount(); i++) {
            BillCategory bean = mAdapter.getItem(i);
            bean.setChecked(false);
        }
        mAdapter.setMode(mMode);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 删除记录
     */
    private void doDelete() {
        // 选择要删除的对象
        final List<BillCategory> list = new ArrayList<>();
        for (int i = 0; i < mAdapter.getCount(); i++) {
            BillCategory bean = mAdapter.getItem(i);
            LogUtils.d(bean.toString());
            if (bean.isChecked()) {
                list.add(bean);
            }
        }
        // 提交删除
        new Thread(new Runnable() {
            @Override
            public void run() {
                BillCategoryManager.getInstance().deleteAll(list, handler);
            }
        }).start();
    }
}
