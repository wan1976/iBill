package net.toeach.ibill.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.toeach.ibill.Constants;
import net.toeach.ibill.R;
import net.toeach.ibill.business.BillRecordManager;
import net.toeach.ibill.model.BillEvent;
import net.toeach.ibill.model.BillRecord;
import net.toeach.ibill.ui.activity.BillRecordModifyActivity;
import net.toeach.ibill.ui.adapter.BillRecordListItemAdapter;

import org.parceler.Parcels;

import java.util.List;

/**
 * 账单清单界面
 */
public class BillRecordFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @ViewInject(R.id.record_list)
    private ListView mListView;// 分类列表
    @ViewInject(R.id.empty_view)
    private TextView mEmptyView;// 无数据提示

    private BillRecordListItemAdapter mAdapter;// 适配器
    private int mPageNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_fragment_layout, container, false);
        ViewUtils.inject(this, view);

        initView(view);
        init();

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BillRecord bean = mAdapter.getItem(position);
        if (bean != null) {
            Parcelable wrapped = Parcels.wrap(bean);
            // 跳转到编辑界面
            Intent intent = new Intent(getActivity(), BillRecordModifyActivity.class);
            intent.putExtra("record", wrapped);
            startActivity(intent);
        }
    }

    @Override
    public void handleMessage(Message message) {
        super.handleMessage(message);
        switch (message.what) {
            case BillRecordManager.MSG_LIST_SUCCESS:
                List<BillRecord> list = (List<BillRecord>) message.obj;
                if (list != null && list.size() > 0) {
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

    private void init() {
        hideBackButton();// 隐藏返回按钮
        setTitleValue(R.string.record_title);// 设置标题名称
        setFuncButton(R.drawable.button_add);// 设置功能按钮

        mAdapter = new BillRecordListItemAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        loadData();
    }

    @Override
    public void doFunction() {
        startActivity(new Intent(getActivity(), BillRecordModifyActivity.class));
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
        if (eventType.equals(BillEvent.EventType.EVENT_RELOAD)) {
            loadData();
        }

        super.onEvent(event);
    }

    /**
     * 加载数据
     */
    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BillRecordManager.getInstance().getList(mPageNo, Constants.PAGE_SIZE, handler);
            }
        }).start();
    }
}
