package net.toeach.ibill.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.toeach.ibill.Constants;
import net.toeach.ibill.R;
import net.toeach.ibill.model.BillCategory;
import net.toeach.ibill.model.CategoryIcon;

/**
 * 分类适配器
 */
public class BillCategoryItemAdapter extends BaseArrayAdapter<BillCategory> {
    /**
     * 构造函数
     *
     * @param context
     */
    public BillCategoryItemAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bill_category_item_layout, parent, false);
            holder = new ViewHolder();
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BillCategory bean = getItem(position);
        if(bean != null) {
            holder.name.setText(bean.getName());
            Integer value = Constants.CAT_ICONS.get(bean.getIcon());
            if(value != null) {
                int icon = value.intValue();
                holder.icon.setImageResource(icon);
            }
        }
        return convertView;
    }

    static class ViewHolder {
        @ViewInject(R.id.cat_icon)
        ImageView icon;// 图标
        @ViewInject(R.id.cat_name)
        TextView name;// 名称
    }
}
