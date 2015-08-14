package net.toeach.ibill.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.toeach.ibill.Constants;
import net.toeach.ibill.R;
import net.toeach.ibill.model.BillCategory;

/**
 * 分类网格适配器
 */
public class BillCategoryGridItemAdapter extends BaseArrayAdapter<BillCategory> {
    /**
     * 构造函数
     *
     * @param context
     */
    public BillCategoryGridItemAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bill_category_grid_item_layout, parent, false);
            holder = new ViewHolder();
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final BillCategory bean = getItem(position);
        if (bean != null) {
            // 设置名称
            holder.name.setText(bean.getName());
            // 设置图标
            Integer value = Constants.CAT_ICONS.get(bean.getIcon());
            if (value != null) {
                int icon = value.intValue();
                holder.icon.setImageResource(icon);
            }
            // 设置选中状态
            holder.checkBox.setVisibility(bean.isChecked() ? View.VISIBLE : View.GONE);
            holder.checkBox.setChecked(bean.isChecked());
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    bean.setChecked(isChecked);
                }
            });
        }
        return convertView;
    }

    public static class ViewHolder {
        @ViewInject(R.id.cat_icon)
        public ImageView icon;// 图标
        @ViewInject(R.id.cat_name)
        public TextView name;// 名称
        @ViewInject(R.id.checkbox)
        public CheckBox checkBox;// 选择框
    }
}
