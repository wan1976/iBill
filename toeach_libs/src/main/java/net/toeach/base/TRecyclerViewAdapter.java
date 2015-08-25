package net.toeach.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerViewAdapter基类
 */
public abstract class TRecyclerViewAdapter<T> extends RecyclerView.Adapter<TRecyclerViewHolder> {
    /**
     * 数据集合
     **/
    protected ArrayList<T> mData = new ArrayList<T>();
    /**
     * 单击事件监听器
     **/
    private OnItemClickListener mItemClickListener;
    /**
     * 长按事件监听器
     **/
    private OnItemLongClickListener mItemLongClickListener;
    /**
     * 布局文件
     **/
    private int mLayoutRes;

    /**
     * 构造函数
     *
     * @param layout 布局文件
     */
    public TRecyclerViewAdapter(int layout) {
        mLayoutRes = layout;
    }

    /**
     * 设置单击事件监听器
     *
     * @param listener 单击事件监听器
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    /**
     * 设置长按事件监听器
     *
     * @param listener 长按事件监听器
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mItemLongClickListener = listener;
    }

    /**
     * 动态增加一条数据
     *
     * @param data 数据实体类对象
     */
    public void add(T data) {
        if (data != null) {
            mData.add(data);
            notifyDataSetChanged();
        }
    }

    /**
     * 动态增加一组数据集合
     *
     * @param list 数据实体类集合
     */
    public void addAll(List<T> list) {
        if (list.size() > 0) {
            for (T itemDataType : list) {
                mData.add(itemDataType);
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 替换全部数据
     *
     * @param list 数据实体类集合
     */
    public void replace(List<T> list) {
        mData.clear();
        if (list.size() > 0) {
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }

    /**
     * 移除一条数据集合
     *
     * @param position
     */
    public void remove(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 移除所有数据
     */
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        if (getItemCount() > position) {
            return mData.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(final TRecyclerViewHolder holder, final int position) {
        bindView(holder, position);
        // 单击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(holder, position);
                }
            }
        });
        // 长按事件
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItemLongClickListener != null) {
                    mItemLongClickListener.onItemLongClick(holder, position);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public TRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载item的布局
        View view = View.inflate(parent.getContext(), mLayoutRes, null);
        return createViewHolder(view);
    }

    /**
     * 显示数据抽象函数
     *
     * @param holder   基类ViewHolder,需要向下转型为对应的ViewHolder
     * @param position 位置
     */
    public abstract void bindView(TRecyclerViewHolder holder, int position);

    /**
     * 加载一个ViewHolder,为BaseRecyclerViewHolder子类,直接返回子类的对象即可
     *
     * @param view item 的view
     * @return TRecyclerViewHolder 基类ViewHolder
     */
    public abstract TRecyclerViewHolder createViewHolder(View view);

    /**
     * Item点击事件
     */
    public interface OnItemClickListener {
        public void onItemClick(TRecyclerViewHolder holder, int position);
    }

    /**
     * Item长按事件
     */
    public interface OnItemLongClickListener {
        public void onItemLongClick(TRecyclerViewHolder holder, int position);
    }
}
