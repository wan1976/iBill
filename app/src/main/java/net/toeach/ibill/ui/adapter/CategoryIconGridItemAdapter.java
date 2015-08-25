package net.toeach.ibill.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.toeach.ibill.R;
import net.toeach.ibill.model.CategoryIcon;

/**
 * 分类图标适配器
 */
public class CategoryIconGridItemAdapter extends BaseArrayAdapter<CategoryIcon> {
    /**
     * 构造函数
     *
     * @param context
     */
    public CategoryIconGridItemAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bill_category_icon_grid_item, parent, false);
            holder = new ViewHolder();
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final CategoryIcon bean = getItem(position);
        if (bean != null) {
            holder.icon.setImageResource(bean.getValue());
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

    static class ViewHolder {
        @ViewInject(R.id.cat_icon)
        ImageView icon;// 图标
        @ViewInject(R.id.checkbox)
        CheckBox checkBox;// 选择框
    }
}
