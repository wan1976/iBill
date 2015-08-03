package net.toeach.ibill.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.toeach.ibill.Constants;
import net.toeach.ibill.R;
import net.toeach.ibill.business.BillCategoryManager;
import net.toeach.ibill.model.BillCategory;
import net.toeach.ibill.model.BillEvent;
import net.toeach.ibill.model.CategoryIcon;
import net.toeach.ibill.ui.adapter.CategoryIconItemAdapter;
import net.toeach.widget.CustomGridView;

import org.parceler.Parcels;
import org.parceler.apache.commons.lang.ArrayUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Set;

import de.greenrobot.event.EventBus;

/**
 * 分类添加/修改界面
 */
@ContentView(R.layout.bill_category_modify_layout)
public class BillCategoryModifyActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @ViewInject(R.id.cat_name)
    private EditText mTxtName;// 名称
    @ViewInject(R.id.cat_icon)
    private CustomGridView mGridIcon;// 图标

    private CategoryIconItemAdapter mAdapter;// 图标适配器
    private BillCategory mCategory;// 分类对象
    private CategoryIcon mIcon;// 选中的图标
    private int mMode;// 编辑模式，0:新建，1:修改

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void handleMessage(Message message) {
        super.handleMessage(message);
        switch (message.what) {
            case BillCategoryManager.MSG_SAVE_SUCCESS:
                showToast(R.string.category_modify_save_success);
                finish();
                // 通知分类列表面刷新UI
                BillEvent event = new BillEvent(BillEvent.EventType.EVENT_RELOAD, null);
                EventBus.getDefault().post(event);
                break;
        }
    }

    @Override
    public void doFunction() {
        if(mMode == 0) {// 新建模式
            mCategory = new BillCategory();
        }

        mCategory.setName(mTxtName.getText().toString());// 名称
        if(mIcon != null) {// 图标
            mCategory.setIcon(mIcon.getName());
        }
        // 提交保存数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                BillCategoryManager.getInstance().save(mMode, mCategory, handler);
            }
        }).start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CategoryIcon bean = mAdapter.getItem(position);
        if (bean != null) {
            for (int i = 0; i < mAdapter.getCount(); i++) {
                CategoryIcon icon = mAdapter.getItem(i);
                icon.setChecked(false);
            }
            bean.setChecked(true);// 选中图标
            mIcon = bean;
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化工作
     */
    private void init() {
        // 获取数据
        mCategory = Parcels.unwrap(getIntent().getParcelableExtra("cat"));
        if (mCategory != null) {
            mMode = 1;// 编辑模式
            mTxtName.setText(mCategory.getName());
            mTxtName.setSelection(mCategory.getName().length());

            validateName(mTxtName.getText().toString());
        } else {
            mMode = 0;// 新建模式
            disableButtonSave();
        }

        // 设置标题名称
        setTitleValue(mMode == 1 ? R.string.category_modify_title : R.string.category_add_title);
        setFuncButton(R.drawable.button_save);// 设置功能按钮

        // 监听输入名称
        mTxtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable edit) {
                String name = edit.toString();
                validateName(name);
            }
        });
        // 图标GridView点击事件
        mGridIcon.setOnItemClickListener(this);
        mAdapter = new CategoryIconItemAdapter(this);

        int i=0;
        // 对Key进行排序
        LinkedList<String> keySet = new LinkedList<String>(Constants.CAT_ICONS.keySet());
        Collections.sort(keySet);
        for (String key : keySet) {
            CategoryIcon bean = new CategoryIcon();
            bean.setName(key);
            bean.setValue(Constants.CAT_ICONS.get(key));
            if((mMode == 0 && i==0) ||// 新增模式下，默认选中第一个
                    (mMode == 1 && bean.getName().equals(mCategory.getIcon()))) {// 编辑模式下，选中对应的分类
                bean.setChecked(true);
            }
            mAdapter.add(bean);
            i++;
        }
        mGridIcon.setAdapter(mAdapter);
    }

    /**
     * 校验是否正确的分类名称
     * @param name
     */
    private void validateName(String name) {
        name = name.trim();
        if (TextUtils.isEmpty(name)) {// 不能为空
            disableButtonSave();
            return;
        }
        int length = name.length();
        if (length<2 || length>10) {// 2-10个字符长度
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
}
