package net.toeach.ibill.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
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
import net.toeach.ibill.ui.adapter.BillCategoryItemAdapter;
import net.toeach.ibill.ui.adapter.CategoryIconItemAdapter;
import net.toeach.widget.CustomGridView;

import org.parceler.Parcels;

import java.util.List;

/**
 * 分类列表界面
 */
@ContentView(R.layout.bill_category_list_layout)
public class BillCategoryListActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @ViewInject(R.id.cat_list)
    private ListView mListView;// 分类列表
    @ViewInject(R.id.empty_view)
    private TextView mEmptyView;// 无数据提示

    private BillCategoryItemAdapter mAdapter;// 适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void handleMessage(Message message) {
        super.handleMessage(message);
        switch (message.what) {
            case BillCategoryManager.MSG_LIST_SUCCESS:
                List<BillCategory> list = (List<BillCategory>)message.obj;
                if(list != null && list.size()>0) {
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
        }
    }

    @Override
    public void doFunction() {
        Intent i = new Intent(this, BillCategoryModifyActivity.class);
        startActivity(i);
    }

    /**
     * 初始化工作
     */
    private void init() {
        setTitleValue(R.string.category_list_title);// 设置标题名称
        setFuncButton(R.drawable.button_add);// 设置功能按钮
        mAdapter = new BillCategoryItemAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BillCategory bean = mAdapter.getItem(position);
        if(bean != null) {
            Parcelable wrapped = Parcels.wrap(bean);

            // 跳转到编辑界面
            Intent intent = new Intent(this, BillCategoryModifyActivity.class);
            intent.putExtra("cat", wrapped);
            startActivity(intent);
        }
    }

    /**
     * 接收到事件通知
     * @param event
     */
    public void onEvent(BillEvent event) {
        if(event == null) {
            return;
        }
        LogUtils.d(event.toString());

        // 获取事件类型
        BillEvent.EventType eventType = event.getEventType();
        // 重新加载数据
        if(eventType.equals(BillEvent.EventType.EVENT_RELOAD)) {
            loadData();
        }

        super.onEvent(event);
    }
}
