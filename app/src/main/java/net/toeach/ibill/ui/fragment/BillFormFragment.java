package net.toeach.ibill.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
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
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuPopup;

import net.toeach.ibill.Constants;
import net.toeach.ibill.R;
import net.toeach.ibill.business.BillFormManager;
import net.toeach.ibill.model.BillEvent;
import net.toeach.ibill.model.BillForm;
import net.toeach.ibill.ui.activity.MonthlyBillAddActivity;
import net.toeach.ibill.ui.activity.MonthlyBillDetailActivity;
import net.toeach.ibill.ui.adapter.BillFormListItemAdapter;

import java.util.List;

/**
 * 账单界面
 */
public class BillFormFragment extends BaseFragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    @ViewInject(R.id.form_list)
    private ListView mListView;// 分类列表
    @ViewInject(R.id.empty_view)
    private TextView mEmptyView;// 无数据提示

    private BillFormListItemAdapter mAdapter;// 适配器
    private int mPageNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_from_list, container, false);
        ViewUtils.inject(this, view);
        initView(view);
        init();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BillForm bean = mAdapter.getItem(position);
        if (bean != null) {
            // 跳转到详情界面
            Intent intent = new Intent(getActivity(), MonthlyBillDetailActivity.class);
            intent.putExtra("id", bean.getId());
            intent.putExtra("title", bean.getTitle());
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
            case BillFormManager.MSG_LIST_SUCCESS:
                List<BillForm> list = (List<BillForm>) message.obj;
                showListView(list);
                break;
        }
    }

    private void init() {
        hideBackButton();// 隐藏返回按钮
        setTitleValue(R.string.bill_title);// 设置标题名称
        setFuncButton(R.drawable.button_more);// 设置功能按钮

        mAdapter = new BillFormListItemAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(this);
        mPageNo = 0;
        loadData();
    }

    @Override
    public void doFunction() {
        DroppyMenuPopup.Builder builder = new DroppyMenuPopup.Builder(getActivity(), mBtnFunc);
        DroppyMenuPopup mMenu = builder.fromMenu(R.menu.form_list_menu)
                .triggerOnAnchorClick(false)
                .setOnClick(new DroppyClickCallbackInterface() {
                    @Override
                    public void call(View v, int id) {
                        switch (id) {
                            case R.id.menu_1: {// 月账单
                                Intent i = new Intent(getActivity(), MonthlyBillAddActivity.class);
                                getActivity().startActivity(i);
                                break;
                            }
                            case R.id.menu_2: {// 自定义账单
                                break;
                            }
                        }
                    }
                })
                .build();
        mMenu.show();
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
        if (eventType.equals(BillEvent.EventType.EVENT_RELOAD_BILL)) {
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
                BillFormManager.getInstance().getList(mPageNo, Constants.PAGE_SIZE, handler);
            }
        }).start();
    }

    /**
     * 显示列表数据
     *
     * @param list
     */
    private void showListView(List<BillForm> list) {
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
