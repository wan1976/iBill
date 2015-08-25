package net.toeach.ibill.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.toeach.common.utils.DateUtil;
import net.toeach.ibill.R;
import net.toeach.ibill.business.BillFormManager;
import net.toeach.ibill.model.BillEvent;
import net.toeach.ibill.model.BillForm;

import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;

/**
 * 月账单创建界面
 */
@ContentView(R.layout.activity_monthly_bill_add)
public class MonthlyBillAddActivity extends BaseActivity {
    @ViewInject(R.id.month)
    private EditText mTxtMonth;// 账单月份

    private BillForm mForm;// 主账单对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void handleMessage(Message message) {
        super.handleMessage(message);
        switch (message.what) {
            case BillFormManager.MSG_GET_VALUE:
                final BillForm ret = (BillForm) message.obj;
                if (ret == null) {
                    // 生成月账单
                    createForm(mForm);
                } else {
                    dismissProgressDialog();
                    showConfirm(ret);
                }
                break;
            case BillFormManager.MSG_SAVE_SUCCESS:
                // 进入到详情界面
                BillForm form = (BillForm) message.obj;
                Intent i = new Intent(this, MonthlyBillDetailActivity.class);
                i.putExtra("id", form.getId());
                i.putExtra("title", form.getTitle());
                startActivity(i);
                dismissProgressDialog();

                // 通知账单列表面刷新UI
                BillEvent event = new BillEvent(BillEvent.EventType.EVENT_RELOAD_BILL, null);
                EventBus.getDefault().post(event);
                finish();
                break;
        }
    }

    @Override
    public void doFunction() {
        showProgressDialog();

        mForm = new BillForm();
        mForm.setType(0);// 月账单
        mForm.setTitle(mTxtMonth.getText().toString());
        mForm.setCreateTime(new Date());

        // 先判断账单是否存在
        new Thread(new Runnable() {
            @Override
            public void run() {
                BillFormManager.getInstance().isMonthlyFormExisted(mForm.getTitle(), handler);
            }
        }).start();
    }

    /**
     * 初始化工作
     */
    private void init() {
        // 设置标题名称
        setTitleValue(R.string.form_add_title);
        setFuncButton(R.drawable.button_next);

        // 设置默认月份
        try {
            String month = DateUtil.formatPatternDate(new Date(), "yyyyMM");
            mTxtMonth.setText(month);
            mTxtMonth.setSelection(month.length());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 监听输入月份
        mTxtMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable edit) {
                String month = edit.toString();
                validateMonth(month);
            }
        });
    }

    /**
     * 校验是否正确的月份
     *
     * @param month
     */
    private void validateMonth(String month) {
        month = month.trim();
        if (TextUtils.isEmpty(month)) {// 不能为空
            disableButtonSave();
            return;
        }

        enableButtonSave();
    }

    /**
     * 设置下一步按钮可用
     */
    private void enableButtonSave() {
        showFuncButton();
    }

    /**
     * 设置下一步按钮禁用
     */
    private void disableButtonSave() {
        hideFuncButton();
    }

    /**
     * 生成账单信息
     *
     * @param form 账单对象
     */
    private void createForm(final BillForm form) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BillFormManager.getInstance().saveMonthlyForm(form, handler);
            }
        }).start();
    }

    /**
     * 显示确认框
     *
     * @param form 账单对象
     */
    private void showConfirm(final BillForm form) {
        new SweetAlertDialog(this)
                .setTitleText(getString(R.string.form_existed))
                .setConfirmText(getString(R.string.button_yes))
                .setCancelText(getString(R.string.button_no))
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        // 生成月账单
                        createForm(form);
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
}
