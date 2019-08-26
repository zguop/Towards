package com.waitou.basic_lib.util;

import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waitou.basic_lib.R;
import com.waitou.normal_dialog_lib.SheetAdapterDialog;
import com.waitou.normal_dialog_lib.NormalDialog;
import com.waitou.three_library.share.ShareEnum;
import com.waitou.three_library.share.ShareInfo;
import com.waitou.three_library.share.UShare;

import java.util.Arrays;
import java.util.List;

/**
 * auth aboom
 * date 2019-04-27
 */
public class DialogUtils {

    public interface IConvert<T> {
        void convert(NormalDialog dialog, BaseViewHolder helper, T data, int position);
    }

    /**
     * @param manager    FragmentManager
     * @param title      标题
     * @param itemHeight 列表单个item的高度 0为warp 默认
     * @param maxHeight  列表弹起最大的高度 0为warp 默认
     * @param layoutId   布局id
     * @param data       数据源
     * @param spanCount  一行多少个 0 即 LinearLayoutManager
     * @param iConvert   回调
     * @param <T>        数据泛型
     */
    public static <T> void showBottomSheetDialog(
            FragmentManager manager,
            String title,
            int itemHeight,
            int maxHeight,
            @LayoutRes int layoutId,
            List<T> data,
            int spanCount,
            IConvert<T> iConvert
    ) {
        SheetAdapterDialog dialog = new SheetAdapterDialog();
        dialog.setRecyclerAdapter(new BaseQuickAdapter<T, BaseViewHolder>(layoutId, data) {
            @Override
            protected void convert(BaseViewHolder helper, T item) {
                if (iConvert != null) {
                    iConvert.convert(dialog, helper, item, helper.getAdapterPosition());
                }
            }
        });
        dialog.setItemHeight(itemHeight)
                .setTitle(title)
                .grid(spanCount)
                .setHeight(maxHeight)
                .show(manager);
    }

    public static void showShareDialog(FragmentActivity activity, ShareInfo shareInfo, UShare.OnShareListener onShareListener) {
        ShareEnum[] values = ShareEnum.values();
        showBottomSheetDialog(activity.getSupportFragmentManager(), "分享到",
                SizeUtils.dp2px(80), SizeUtils.dp2px(280),
                R.layout.bs_item_share, Arrays.asList(values), values.length, (dialog, helper, item, position) -> {
                    helper.setImageResource(R.id.img, item.getResId());
                    helper.setText(R.id.text, item.getName());
                    helper.itemView.setOnClickListener(v -> {
                        dialog.dismiss();
                        UShare.share(activity, item.getMedia(), shareInfo, onShareListener);
                    });
                });
    }
}
