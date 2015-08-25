package net.toeach.ibill.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.toeach.ibill.R;
import net.toeach.ibill.model.BillDetailItem;

/**
 * 账单详情列表适配器
 */
public class MonthlyBillDetailListItemAdapter extends BaseArrayAdapter<BillDetailItem> {
    /**
     * 构造函数
     *
     * @param context
     */
    public MonthlyBillDetailListItemAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.monthly_bill_detail_list_item, parent, false);
            holder = new ViewHolder();
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BillDetailItem bean = getItem(position);
        if (bean != null) {
            if (bean.getType() == 0) {
                holder.title.setVisibility(View.VISIBLE);
                holder.item.setVisibility(View.GONE);
                holder.total.setVisibility(View.GONE);
                // 设置标题名称
                holder.title.setText(bean.getTitle());
            } else if (bean.getType() == 1) {
                holder.title.setVisibility(View.GONE);
                holder.item.setVisibility(View.VISIBLE);
                holder.total.setVisibility(View.GONE);
                // 设置金额名称
                holder.cost.setText(bean.getCost());
                // 设置日期
                holder.date.setText(bean.getDate());
                // 设置摘要
                holder.memo.setText(bean.getMemo());
            } else if (bean.getType() == 2) {
                holder.title.setVisibility(View.GONE);
                holder.item.setVisibility(View.GONE);
                holder.total.setVisibility(View.VISIBLE);
                // 设置合计
                holder.totalCost.setText(bean.getCost());
            }

        }
        return convertView;
    }

    public static class ViewHolder {
        @ViewInject(R.id.title)
        public TextView title;// 标题

        @ViewInject(R.id.item)
        public LinearLayout item;// 内容区域
        @ViewInject(R.id.memo)
        public TextView memo;// 摘要
        @ViewInject(R.id.cost)
        public TextView cost;// 金额
        @ViewInject(R.id.date)
        public TextView date;// 日期

        @ViewInject(R.id.total)
        public LinearLayout total;// 合计区域
        @ViewInject(R.id.total_cost)
        public TextView totalCost;// 合计
    }
}
