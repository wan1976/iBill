package net.toeach.ibill.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.toeach.ibill.R;
import net.toeach.ibill.model.CategoryIcon;

/**
 * 分类图标适配器
 */
public class CategoryIconItemAdapter extends BaseArrayAdapter<CategoryIcon> {
    /**
     * 构造函数
     *
     * @param context
     */
    public CategoryIconItemAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bill_category_icon_item_layout, parent, false);
            holder = new ViewHolder();
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CategoryIcon bean = getItem(position);
        if (bean != null) {
            holder.icon.setImageResource(bean.getValue());
            holder.checkbox.setVisibility(bean.isChecked() ? View.VISIBLE : View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        @ViewInject(R.id.cat_icon)
        ImageView icon;// 图标
        @ViewInject(R.id.cat_checkbox)
        ImageView checkbox;// 选择框
    }
}
