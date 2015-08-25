package net.toeach.ibill.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
public class BillRecordFragment extends BaseFragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    @ViewInject(R.id.record_list)
    private ListView mListView;// 分类列表
    @ViewInject(R.id.empty_view)
    private TextView mEmptyView;// 无数据提示

    private BillRecordListItemAdapter mAdapter;// 适配器
    private int mPageNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_list, container, false);
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
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 当不滚动时
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            // 判断是否滚动到底部
            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                LogUtils.d("listview load more data");
                //加载更多功能的数据
                mPageNo++;
                loadData();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    @Override
    public void handleMessage(Message message) {
        super.handleMessage(message);
        switch (message.what) {
            case BillRecordManager.MSG_LIST_SUCCESS:
                List<BillRecord> list = (List<BillRecord>) message.obj;
                showListView(list);
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
        mListView.setOnScrollListener(this);
        mPageNo = 0;
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
    @Override
    public void onEvent(BillEvent event) {
        if (event == null) {
            return;
        }
        LogUtils.d(event.toString());

        // 获取事件类型
        BillEvent.EventType eventType = event.getEventType();
        // 重新加载数据
        if (eventType.equals(BillEvent.EventType.EVENT_RELOAD_RECORD)) {
            mPageNo = 0;
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

    /**
     * 显示列表数据
     *
     * @param list
     */
    private void showListView(List<BillRecord> list) {
        LogUtils.d("page_no:" + mPageNo + ", adapter:" + mAdapter.getCount());
        if (list != null && list.size() > 0) {
            LogUtils.d("list is not null, size:" + list.size());
            if (mPageNo == 0) {// 加载第一页数据
                mAdapter.clear();
            }
            mAdapter.addAll(list);
            mEmptyView.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
        } else {// 返回的结果为空
            LogUtils.d("list is null");
            if (mAdapter.getCount() == 0) {// 当前列表为空，则显示无数据提示
                mEmptyView.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
            } else {
                mPageNo--;
            }
        }
    }
}
