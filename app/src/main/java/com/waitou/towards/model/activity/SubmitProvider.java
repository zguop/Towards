package com.waitou.towards.model.activity;

import com.to.aboomy.recycler_lib.BindingViewHolder;
import com.to.aboomy.recycler_lib.QyItemProvider;
import com.waitou.towards.R;
import com.waitou.towards.databinding.ItemButtonBinding;

/**
 * auth aboom
 * date 2018/7/22
 */
public class SubmitProvider extends QyItemProvider<ItemButton, BindingViewHolder<ItemButtonBinding>> {

    @Override
    public int viewType() {
        return 4;
    }

    @Override
    public int layout() {
        return R.layout.item_button;
    }

    @Override
    public void convert(BindingViewHolder<ItemButtonBinding> helper, ItemButton data, int position) {
        helper.getBinding().button.setText(data.buttonDes);
    }

    @Override
    public void onClick(BindingViewHolder<ItemButtonBinding> helper, ItemButton data, int position) {
        super.onClick(helper, data, position);
        RecommendHelper helper1 = (RecommendHelper) helper.getQyPresenter();
        helper1.submit();
    }

    @Override
    public Object object() {
        return null;
    }
}
