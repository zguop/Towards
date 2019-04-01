package com.to.aboomy.recycler_lib;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.chad.library.adapter.base.util.ProviderDelegate;

/**
 * auth aboom
 * date 2018/7/9
 */
public class MuRecyclerAdapter extends MultipleItemRvAdapter<Displayable, BindingViewHolder> {

    private IPresenter             iPresenter;

    public MuRecyclerAdapter() {
        super(null);
        finishInitialize();
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return binding.getRoot();
    }

    @Override
    protected BindingViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BindingViewHolder bindingViewHolder = super.onCreateDefViewHolder(parent, viewType);
        bindingViewHolder.setPresenter(iPresenter);
        return bindingViewHolder;
    }

    @Override
    protected void convert(BindingViewHolder helper, Displayable item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.item, item);
        binding.setVariable(BR.presenter, iPresenter);
        helper.getBinding().executePendingBindings();
        int itemViewType = helper.getItemViewType();
        BaseItemProvider provider =  mProviderDelegate.getItemProviders().get(itemViewType);
        provider.mContext = helper.itemView.getContext();
        int position = helper.getLayoutPosition() - getHeaderLayoutCount();
        provider.convert(helper, item, position);
        bindClick(helper, item, position, provider);    }

    @Override
    protected int getViewType(Displayable displayable) {
        SparseArray<BaseItemProvider> itemProviders = mProviderDelegate.getItemProviders();
        for (int i = 0; i < itemProviders.size(); i++) {
            int key = itemProviders.keyAt(i);
            BindingItemProvider provider = (BindingItemProvider) itemProviders.get(key);
            if (provider.isForViewType(displayable)) {
                return key;
            }
        }
        return -1;
    }


    @Override
    public void finishInitialize() {
        mProviderDelegate = new ProviderDelegate();
        setMultiTypeDelegate(new MultiTypeDelegate<Displayable>() {
            @Override
            protected int getItemType(Displayable displayable) {
                return getViewType(displayable);
            }
        });
    }

    @Override
    public void registerItemProvider() {
        SparseArray<BaseItemProvider> itemProviders = mProviderDelegate.getItemProviders();
        for (int i = 0; i < itemProviders.size(); i++) {
            int key = itemProviders.keyAt(i);
            BaseItemProvider provider = itemProviders.get(key);
            provider.mData = mData;
            getMultiTypeDelegate().registerItemType(key, provider.layout());
        }
    }

    public void addProvider(BindingItemProvider... baseItemProvider) {
        SparseArray<BaseItemProvider> itemProviders = mProviderDelegate.getItemProviders();
        for (BindingItemProvider bindingItemProvider : baseItemProvider) {
            int viewType = itemProviders.size();
            while (itemProviders.get(viewType) != null) {
                viewType++;
            }
            mProviderDelegate.registerProvider(bindingItemProvider);
        }
        registerItemProvider();
    }

    public void setPresenter(IPresenter presenter) {
        iPresenter = presenter;
    }

    private void bindClick(final BaseViewHolder helper, final Displayable item, final int position, final BaseItemProvider provider) {
        OnItemClickListener clickListener = getOnItemClickListener();
        OnItemLongClickListener longClickListener = getOnItemLongClickListener();

        if (clickListener != null && longClickListener != null){
            //如果已经设置了子条目点击监听和子条目长按监听
            // If you have set up a sub-entry click monitor and sub-entries long press listen
            return;
        }

        View itemView = helper.itemView;

        if (clickListener == null){
            //如果没有设置点击监听，则回调给itemProvider
            //Callback to itemProvider if no click listener is set
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    provider.onClick(helper, item, position);
                }
            });
        }

        if (longClickListener == null){
            //如果没有设置长按监听，则回调给itemProvider
            // If you do not set a long press listener, callback to the itemProvider
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return provider.onLongClick(helper, item, position);
                }
            });
        }
    }
}
