package com.waitou.towards.model.activity;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.waitou.towards.R;

/**
 * auth aboom
 * date 2018/7/10
 */
public class ImgItemProvider extends BaseItemProvider<MultiItemEntity, BaseViewHolder> {

    @Override
    public int viewType() {
        return 1;
    }

    @Override
    public int layout() {
        return R.layout.item_img;
    }

    @Override
    public void convert(BaseViewHolder helper, MultiItemEntity data, int position) {
    }
}
