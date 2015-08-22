package net.toeach.ibill.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import net.toeach.common.utils.CurrencyInputFilter;
import net.toeach.common.utils.DateUtil;
import net.toeach.ibill.R;
import net.toeach.ibill.business.BillCategoryManager;
import net.toeach.ibill.business.BillRecordManager;
import net.toeach.ibill.model.BillCategory;
import net.toeach.ibill.model.BillEvent;
import net.toeach.ibill.model.BillRecord;
import net.toeach.ibill.ui.adapter.BillCategoryGridItemAdapter;
import net.toeach.widget.CustomGridView;

import org.parceler.Parcels;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;

/**
 * 明细添加/修改界面
 */
@ContentView(R.layout.bill_record_modify_layout)
public class BillRecordModifyActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @ViewInject(R.id.bill_date)
    private TextView mTxtDate;// 日期
    @ViewInject(R.id.cost)
    private EditText mTxtCost;// 金额
    @ViewInject(R.id.cat_icon)
    private CustomGridView mGridCategory;// 分类
    @ViewInject(R.id.memo)
    private EditText mTxtMemo;// 备注
    @ViewInject(R.id.btn_del)
    private TextView mBtnDel;// 删除按钮

    private BillCategoryGridItemAdapter mAdapter;// 分类图标适配器
    private BillRecord mRecord;// 费用明细
    private BillCategory mCategory;// 选中的分类对象
    private int mMode;// 编辑模式，0:新建，1:修改
    private DatePickerDialog.OnDateSetListener mDateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BillCategory bean = mAdapter.getItem(position);
        if (bean != null) {
            if (bean.getIcon().equals("ic_cat_add")) {// 新建分类
                Intent i = new Intent(this, BillCategoryModifyActivity.class);
                startActivity(i);
            } else {// 选择分类
                for (int i = 0; i < mAdapter.getCount(); i++) {
                    BillCategory icon = mAdapter.getItem(i);
                    icon.setChecked(false);
                }
                bean.setChecked(true);// 选中图标
                mCategory = bean;
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void handleMessage(Message message) {
        super.handleMessage(message);
        switch (message.what) {
            case BillCategoryManager.MSG_LIST_SUCCESS: {
                mAdapter.clear();
                List<BillCategory> list = (List<BillCategory>) message.obj;
                int i = 0;
                for (BillCategory bean : list) {
                    if ((mMode == 0 && i == 0) ||// 新增模式下，默认选中第一个
                            (mMode == 1 && bean.getId() == mRecord.getCatId())) {// 编辑模式下，选中对应的分类
                        bean.setChecked(true);
                        mCategory = bean;
                    }
                    mAdapter.add(bean);
                    i++;
                }

                // 设置添加分类按钮
                BillCategory button = new BillCategory();
                button.setIcon("ic_cat_add");
                button.setName(getString(R.string.record_add_cat));
                mAdapter.add(button);

                break;
            }
            case BillRecordManager.MSG_SAVE_SUCCESS: {
                showToast(R.string.record_modify_save_success);
                finish();
                // 通知分类列表面刷新UI
                BillEvent event = new BillEvent(BillEvent.EventType.EVENT_RELOAD_RECORD, null);
                EventBus.getDefault().post(event);
                break;
            }
            case BillRecordManager.MSG_DEL_SUCCESS: {
                showToast(R.string.record_modify_delete_success);
                finish();
                // 通知分类列表面刷新UI
                BillEvent event = new BillEvent(BillEvent.EventType.EVENT_RELOAD_RECORD, null);
                EventBus.getDefault().post(event);
                break;
            }
        }
    }

    @Override
    public void doFunction() {
        if (mMode == 0) {// 新建模式
            mRecord = new BillRecord();
            mRecord.setCreateTime(new Date());
        }

        // 设置账单记录日期
        try {
            mRecord.setBillDate(DateUtil.parseDate(mTxtDate.getText().toString(), "yyyy-M-d"));
        } catch (Exception e) {
            mRecord.setBillDate(new Date());
        }
        try {
            mRecord.setBillMonth(DateUtil.formatPatternDate(mRecord.getBillDate(), "yyyyMM"));
            // 金额以分为单位
            double cost = Double.parseDouble(mTxtCost.getText().toString()) * 100;
            mRecord.setCost((int) cost);// 金额
        } catch (Exception e) {
        }
        mRecord.setMemo(mTxtMemo.getText().toString());// 备注
        if (mCategory != null) {// 分类
            mRecord.setCatId(mCategory.getId());
        }
        // 提交保存数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                BillRecordManager.getInstance().save(mRecord, handler);
            }
        }).start();
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
        if (eventType.equals(BillEvent.EventType.EVENT_RELOAD_CAT)) {
            // 加载分类数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BillCategoryManager.getInstance().getList(handler);
                }
            }).start();
        }

        super.onEvent(event);
    }

    /**
     * 初始化工作
     */
    private void init() {
        // 获取数据
        mRecord = Parcels.unwrap(getIntent().getParcelableExtra("record"));
        if (mRecord != null) {
            mMode = 1;// 编辑模式
            // 金额是以分为单位的，要转换为以元为单位
            double cost = mRecord.getCost();
            cost = cost / 100;
            String s = String.valueOf(cost);
            mTxtCost.setText(s);
            mTxtCost.setSelection(s.length());
            try {
                mTxtDate.setText(DateUtil.formatPatternDate(mRecord.getBillDate(), "yyyy-M-d"));
            } catch (Exception e) {
            }
            mTxtMemo.setText(mRecord.getMemo());
            mBtnDel.setVisibility(View.VISIBLE);

            validateCost(mTxtCost.getText().toString());
        } else {
            mMode = 0;// 新建模式
            disableButtonSave();
            mBtnDel.setVisibility(View.GONE);
            try {
                mTxtDate.setText(DateUtil.formatPatternDate(new Date(), "yyyy-M-d"));
            } catch (Exception e) {
            }
        }

        // 设置标题名称
        setTitleValue(mMode == 1 ? R.string.record_modify_title : R.string.record_add_title);
        setFuncButton(R.drawable.button_save);// 设置功能按钮

        // 监听输入金额
        mTxtCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable edit) {
                String s = edit.toString();
                validateCost(s);
            }
        });
        // 保持小数点后2位数字
        mTxtCost.setFilters(new InputFilter[]{new CurrencyInputFilter()});

        // 图标GridView点击事件
        mGridCategory.setOnItemClickListener(this);
        mAdapter = new BillCategoryGridItemAdapter(this);
        mGridCategory.setAdapter(mAdapter);

        // 加载分类数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                BillCategoryManager.getInstance().getList(handler);
            }
        }).start();

        // 日期选择框监听器
        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                // Calendar月份是从0开始,所以month要加1
                String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                mTxtDate.setText(date);
            }
        };
    }

    /**
     * 校验是否正确的金额
     *
     * @param cost 输入的金额
     */
    private void validateCost(String cost) {
        cost = cost.trim();
        if (TextUtils.isEmpty(cost)) {// 不能为空
            disableButtonSave();
            return;
        }
        enableButtonSave();
    }

    /**
     * 设置保存按钮可用
     */
    private void enableButtonSave() {
        showFuncButton();
    }

    /**
     * 设置保存按钮禁用
     */
    private void disableButtonSave() {
        hideFuncButton();
    }

    /**
     * 删除按钮点击事件
     *
     * @param view 点击对象
     */
    @OnClick(R.id.btn_del)
    private void onButtonDeleteClicked(View view) {
        new SweetAlertDialog(this)
                .setTitleText(getString(R.string.record_list_confirm_del))
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
    }

    /**
     * 删除记录
     */
    private void doDelete() {
        // 提交删除
        new Thread(new Runnable() {
            @Override
            public void run() {
                BillRecordManager.getInstance().deleteById(mRecord.getId(), handler);
            }
        }).start();
    }

    /**
     * 日期点击事件
     *
     * @param view 点击对象
     */
    @OnClick(R.id.bill_date)
    private void onButtonDateClicked(View view) {
        Calendar c = Calendar.getInstance();
        // 如果编辑模式，则设置日期选择控件初始数据为对应明细的日期
        if (mRecord != null) {
            c.setTime(mRecord.getBillDate());
        }
        // 弹出日期选择框
        DatePickerDialog dialog = new DatePickerDialog(this,
                mDateListener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
}
