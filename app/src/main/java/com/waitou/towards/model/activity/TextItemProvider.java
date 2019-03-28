package com.waitou.towards.model.activity;


import android.text.Editable;
import android.text.TextWatcher;

import com.to.aboomy.recycler_lib.BindingViewHolder;
import com.to.aboomy.recycler_lib.QyItemProvider;
import com.waitou.towards.R;
import com.waitou.towards.databinding.ItemTextBinding;

/**
 * auth aboom
 * date 2018/7/10
 */
public class TextItemProvider extends QyItemProvider<TextBean, BindingViewHolder<ItemTextBinding>> {

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
        RecommendHelper recommendHelper = (RecommendHelper) helper.getQyPresenter();
        helper.getBinding().etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recommendHelper.mRequestBean.phone = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public Object object() {
        return null;
    }
}
