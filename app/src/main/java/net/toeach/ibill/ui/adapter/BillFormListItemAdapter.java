package net.toeach.ibill.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.toeach.common.utils.DateUtil;
import net.toeach.ibill.R;
import net.toeach.ibill.model.BillForm;

/**
 * 账单列表适配器
 */
public class BillFormListItemAdapter extends BaseArrayAdapter<BillForm> {
    /**
     * 构造函数
     *
     * @param context
     */
    public BillFormListItemAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bill_form_list_item, parent, false);
            holder = new ViewHolder();
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final BillForm bean = getItem(position);
        if (bean != null) {
            holder.title.setText(bean.getTitle());// 设置标题
            // 设置类型
            if (bean.getType() == 1) {
                holder.type.setText(getContext().getString(R.string.form_type_custom));
            } else {
                holder.type.setText(getContext().getString(R.string.form_type_monthly));
            }
            // 设置日期
            try {
                holder.date.setText(DateUtil.formatDate(bean.getCreateTime()));
            } catch (Exception e) {
            }
        }
        return convertView;
    }

    public static class ViewHolder {
        @ViewInject(R.id.title)
        public TextView title;// 标题
        @ViewInject(R.id.type)
        public TextView type;// 类型
        @ViewInject(R.id.date)
        public TextView date;// 日期
    }
}
