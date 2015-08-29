package net.toeach.ibill.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
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
@ContentView(R.layout.activity_bill_detail)
public class BillDetailActivity extends BaseActivity {
    @ViewInject(R.id.list)
    private ListView mListView;// 列表对象

    private MonthlyBillDetailListItemAdapter mAdapter;// 适配器
    private PieChart mChart;// 图表对象
    private int mFormId;
    private int mFormType;
    private String mFormTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void doFunction() {
        DroppyMenuPopup.Builder builder = new DroppyMenuPopup.Builder(this, mBtnFunc);
        DroppyMenuPopup mMenu;
        if (mFormType == 0) {
            mMenu = builder.fromMenu(R.menu.form_detail_monthly_menu)
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
        } else {
            mMenu = builder.fromMenu(R.menu.form_detail_customized_menu)
                    .triggerOnAnchorClick(false)
                    .setOnClick(new DroppyClickCallbackInterface() {
                        @Override
                        public void call(View v, int id) {
                            switch (id) {
                                case R.id.menu_1: {// 修改自定义账单
                                    doModifyCustomizedBill();
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
        }
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
                    // 加载图表数据
                    loadChartData();
                }
                break;
            case BillFormManager.MSG_DEL_SUCCESS:
                showToast(R.string.form_detail_delete_success);
                finish();
                // 通知账单列表面刷新UI
                BillEvent event = new BillEvent(BillEvent.EVENT_RELOAD_BILL, null);
                EventBus.getDefault().post(event);
                break;
            case BillFormManager.MSG_SAVE_SUCCESS:
                // 重新加载
                loadData();
                dismissProgressDialog();
                break;
            case BillFormManager.MSG_GET_VALUE:
                PieData data = (PieData) message.obj;
                showChart(data);
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
        mFormType = getIntent().getIntExtra("type", 0);

        // 设置列表标头
        mFormTitle = getIntent().getStringExtra("title");
        String title = String.format(getString(R.string.form_detail_title_name), mFormTitle);
        View header = LayoutInflater.from(this).inflate(R.layout.bill_form_detail_header, null, false);
        TextView txtTitle = (TextView) header.findViewById(R.id.title);
        txtTitle.setText(title);
        mListView.addHeaderView(header);

        // 添加图表到footer
        View chartView = LayoutInflater.from(this).inflate(R.layout.bill_form_detail_chart, null, false);
        mChart = (PieChart) chartView.findViewById(R.id.chart);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setUsePercentValues(true);// 是否显示百分比
        mChart.setDescription("账单分析");
        mChart.setCenterText("各类型费用\n所占比例");// 显示在中心部分的文字
        mChart.setDrawCenterText(true);// 是否在空心部分显示文字
        mChart.setDrawSliceText(false);// 是否显示X轴值
        mChart.setDrawHoleEnabled(true);// 是否显示中间空心部分
        mChart.setHoleColorTransparent(true);// 中间空心部分是否透明
        mChart.setHoleRadius(55f);// 空心部分的半径
        mChart.setTransparentCircleRadius(58f);// 阴影部分的半径
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(false);// 是否允许旋转
        mListView.addFooterView(chartView);

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
        showProgressDialog();
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

    /**
     * 加载饼图图形数据
     */
    private void loadChartData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BillFormManager.getInstance().getFormChartData(mFormId, handler);
            }
        }).start();
    }

    /**
     * 显示饼图图形
     *
     * @param data 饼图数据对象
     */
    private void showChart(PieData data) {
        if (data.getDataSet().getYVals().size() > 0) {
            mChart.setData(data);
            mChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
            Legend l = mChart.getLegend();
            l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(0f);
            l.setYOffset(0f);

            // undo all highlights
            mChart.highlightValues(null);
            mChart.invalidate();
            mChart.setVisibility(View.VISIBLE);
        }
        dismissProgressDialog();
    }

    /**
     * 修改账单资料
     */
    private void doModifyCustomizedBill() {
        Intent intent = new Intent(this, CustomizedBillModifyActivity.class);
        intent.putExtra("id", mFormId);
        intent.putExtra("title", mFormTitle);
        startActivity(intent);
    }
}
