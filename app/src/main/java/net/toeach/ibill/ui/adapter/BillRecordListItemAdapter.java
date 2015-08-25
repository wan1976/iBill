package net.toeach.ibill.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.toeach.common.utils.DateUtil;
import net.toeach.common.utils.StringUtil;
import net.toeach.ibill.Constants;
import net.toeach.ibill.R;
import net.toeach.ibill.model.BillRecord;

/**
 * 分类列表适配器
 */
public class BillRecordListItemAdapter extends BaseArrayAdapter<BillRecord> {
    /**
     * 构造函数
     *
     * @param context
     */
    public BillRecordListItemAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bill_record_list_item, parent, false);
            holder = new ViewHolder();
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final BillRecord bean = getItem(position);
        if (bean != null) {
            // 设置金额
            double cost = bean.getCost();
            cost = cost / 100;
            holder.cost.setText("￥" + StringUtil.formatMoney(String.valueOf(cost), 2));
            // 设置日期
            try {
                holder.date.setText(DateUtil.formatDate(bean.getBillDate()));
            } catch (Exception e) {
            }
            // 设置图标
            Integer value = Constants.CAT_ICONS.get(bean.getCatIcon());
            if (value != null) {
                int icon = value.intValue();
                holder.icon.setImageResource(icon);
            }
            holder.catName.setText(bean.getCatName());
        }
        return convertView;
    }

    public static class ViewHolder {
        @ViewInject(R.id.cat_icon)
        public ImageView icon;// 分类图标
        @ViewInject(R.id.cat_name)
        public TextView catName;// 分类名称
        @ViewInject(R.id.cost)
        public TextView cost;// 金额
        @ViewInject(R.id.date)
        public TextView date;// 日期
    }
}
