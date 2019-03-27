package com.waitou.towards.model.activity;


import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.to.aboomy.recycler_lib.BindingViewHolder;
import com.to.aboomy.utils_lib.AlertToast;
import com.waitou.towards.R;
import com.waitou.towards.databinding.ItemTextBinding;

/**
 * auth aboom
 * date 2018/7/10
 */
public class TextItemProvider extends BaseItemProvider<TextBean, BindingViewHolder<ItemTextBinding>> {

    @Override
    public int viewType() {
        return 2;
    }

    @Override
    public int layout() {
        return R.layout.item_text;
    }

    @Override
    public void convert(BindingViewHolder<ItemTextBinding> helper, TextBean data, int position) {
        helper.getBinding().etUsername.setHint(data.title);
        RecommendHelper recommendHelper = (RecommendHelper) helper.getPresenter();

    }


    @Override
    public void onClick(BindingViewHolder<ItemTextBinding> helper, TextBean data, int position) {
        super.onClick(helper, data, position);

        AlertToast.show("data " + data.info);
    }

}
