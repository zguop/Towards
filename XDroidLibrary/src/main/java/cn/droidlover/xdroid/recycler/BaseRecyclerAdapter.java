package cn.droidlover.xdroid.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<T> mList = new ArrayList<>();

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected OnItemClickListener mOnItemClickListener;

    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return mContext;
    }

    private void initList() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
    }

    public List<T> getData() {
        return mList;
    }

    public int getSize() {
        return mList == null ? 0 : mList.size();
    }

    public void addData(T bean) {
        initList();
        mList.add(bean);
        notifyDataSetChanged();

    }

    public void addData(Collection<T> c) {
        initList();
        if (c != null) {
            mList.addAll(c);
            notifyDataSetChanged();
        }
    }

    /**
     * 仅仅用于添加数据，而不是notify。
     *
     * @param data
     * @return
     */
    public BaseRecyclerAdapter addDataWithoutNoti(Collection<T> data) {
        initList();
        if (data != null) {
            mList.addAll(data);
        }
        return this;
    }

    public void addDataAt(int location, T bean) {
        initList();
        mList.add(location, bean);
        notifyDataSetChanged();

    }

    public void addDataAt(int location, Collection<T> c) {
        initList();
        mList.addAll(location, c);
        notifyDataSetChanged();

    }

    public void clear() {
        if (mList != null) {
            mList.clear();
            notifyDataSetChanged();
        }
    }

    public T getItem(int pos) {
        if (mList != null) {
            return mList.get(pos);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    protected void itemClick(int position) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onClick(position);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }
}
