package net.toeach.ibill.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.toeach.common.utils.DateUtil;
import net.toeach.ibill.R;

import java.util.Date;

/**
 * 月账单创建界面
 */
@ContentView(R.layout.monthly_bill_modify_layout)
public class MonthlyBillModifyActivity extends BaseActivity {
    @ViewInject(R.id.month)
    private EditText mTxtMonth;// 账单月份

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void doFunction() {
        showProgressDialog();
        // 生成月账单

    }

    /**
     * 初始化工作
     */
    private void init() {
        // 设置标题名称
        setTitleValue(R.string.bill_add_title);
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
}
