package net.toeach.ibill.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuPopup;

import net.toeach.ibill.R;
import net.toeach.ibill.business.BillFormManager;
import net.toeach.ibill.model.BillDetailItem;
import net.toeach.ibill.model.BillEvent;
import net.toeach.ibill.ui.adapter.MonthlyBillDetailListItemAdapter;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;

/**
 * 月账单详情界面
 */
@ContentView(R.layout.monthly_bill_detail_layout)
public class MonthlyBillDetailActivity extends BaseActivity {
    @ViewInject(R.id.list)
    private ListView mListView;// 列表对象

    private MonthlyBillDetailListItemAdapter mAdapter;// 适配器
    private int mFormId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void doFunction() {
        DroppyMenuPopup.Builder builder = new DroppyMenuPopup.Builder(this, mBtnFunc);
        DroppyMenuPopup mMenu = builder.fromMenu(R.menu.form_detail_menu)
                .triggerOnAnchorClick(false)
                .setOnClick(new DroppyClickCallbackInterface() {
                    @Override
                    public void call(View v, int id) {
                        switch (id) {
                            case R.id.menu_1: {// 重新生成账单
                                showRedoConfirm();
                                break;
                            }
                            case R.id.menu_2: {// 删除账单
                                showDeleteConfirm();
                                break;
                            }
                        }
                    }
                })
                .build();
        mMenu.show();
    }

    @Override
    public void handleMessage(Message message) {
        super.handleMessage(message);
        switch (message.what) {
            case BillFormManager.MSG_LIST_SUCCESS:
                List<BillDetailItem> list = (List<BillDetailItem>) message.obj;
                if (list != null && list.size() > 0) {
                    mAdapter.clear();
                    mAdapter.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case BillFormManager.MSG_DEL_SUCCESS:
                showToast(R.string.form_detail_delete_success);
                finish();
                // 通知账单列表面刷新UI
                BillEvent event = new BillEvent(BillEvent.EventType.EVENT_RELOAD_BILL, null);
                EventBus.getDefault().post(event);
                break;
            case BillFormManager.MSG_SAVE_SUCCESS:
                // 重新加载
                loadData();
                dismissProgressDialog();
                break;
        }
    }

    /**
     * 初始化工作
     */
    private void init() {
        // 设置标题名称
        setTitleValue(R.string.form_detail_title);
        setFuncButton(R.drawable.button_more);// 设置功能按钮
        mFormId = getIntent().getIntExtra("id", -1);

        String title = getIntent().getStringExtra("title");
        title = String.format(getString(R.string.form_detail_title_name), title);
        // 设置列表标头
        View header = LayoutInflater.from(this).inflate(R.layout.bill_form_detail_header_layout, null, false);
        TextView txtTitle = (TextView)header.findViewById(R.id.title);
        txtTitle.setText(title);
        mListView.addHeaderView(header);

        // 设置列表Footer
        ImageView footer = new ImageView(this);
        footer.setBackgroundResource(R.drawable.bottom_line);
        mListView.addFooterView(footer);
        mAdapter = new MonthlyBillDetailListItemAdapter(this);
        mListView.setAdapter(mAdapter);
        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BillFormManager.getInstance().getMonthlyFormDetail(mFormId, handler);
            }
        }).start();
    }

    /**
     * 重新生成账单确认对话框
     */
    private void showRedoConfirm() {
        new SweetAlertDialog(this)
                .setTitleText(getString(R.string.form_detail_confirm_redo))
                .setConfirmText(getString(R.string.button_yes))
                .setCancelText(getString(R.string.button_no))
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        // 重新生成账单
                        createForm();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();
    }

    /**
     * 删除账单确认对话框
     */
    private void showDeleteConfirm() {
        new SweetAlertDialog(this)
                .setTitleText(getString(R.string.form_detail_confirm_del))
                .setConfirmText(getString(R.string.button_yes))
                .setCancelText(getString(R.string.button_no))
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        // 删除账单
                        deleteForm();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();
    }

    private void deleteForm() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BillFormManager.getInstance().deleteById(mFormId, handler);
            }
        }).start();
    }

    private void createForm() {
        showProgressDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                BillFormManager.getInstance().reCreateMonthlyForm(mFormId, handler);
            }
        }).start();
    }
}
