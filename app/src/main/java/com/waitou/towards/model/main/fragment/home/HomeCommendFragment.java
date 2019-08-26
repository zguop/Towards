package com.waitou.towards.model.main.fragment.home;

/**
 * Created by waitou on 17/2/10.
 * 首页推荐
 */

//public class HomeCommendFragment extends XFragment<HomePresenter, IncludeMatchRecyclerViewBinding> {

//    private MultiTypeAdapter<Displayable> mAdapter; //整体页面适配器
//
//    @Override
//    public int getContentViewId() {
//        return R.layout.include_match_recycler_view;
//    }
//
//    @Override
//    public void afterCreate(Bundle savedInstanceState) {
//        mAdapter = new MultiTypeAdapter<>(getActivity());
//        mAdapter.addViewTypeToLayoutMap(0, R.layout.item_banner);
//        mAdapter.addViewTypeToLayoutMap(1, R.layout.item_wrap_recycler_view);
//        mAdapter.addViewTypeToLayoutMap(2, R.layout.item_gank_page);
//        mAdapter.addViewTypeToLayoutMap(3, R.layout.item_empty);
//        mAdapter.addViewTypeToLayoutMap(4, R.layout.item_bottom);
//        mAdapter.setPresenter(getP());
//        getBinding().setManager(LayoutManagerUtil.getVerticalLayoutManager(getActivity()));
//        getBinding().setAdapter(mAdapter);
//    }
//
//    @Override
//    protected void visibleCall() {
//        super.visibleCall();
//        showLoading();
//        getP().loadHomeData();
//    }
//
//    @Override
//    public void reloadData() {
//        showLoading();
//        getP().loadHomeData();
//    }
//
//    public void onBannerSuccess(List<BannerPageInfo> bannerPageInfo) {
//        SingleViewPagerAdapter<BannerPageInfo> bannerAdapter = new SingleViewPagerAdapter<>(getActivity(), bannerPageInfo, R.layout.item_banner_image);
//        bannerAdapter.setPresenter(getP());
//        IndicatorView qyIndicator = new Indicator(getActivity())
//                .setGravity(Gravity.CENTER)
//                .setIndicatorInColor(ThemeUtils.getThemeAttrColor(getActivity(), R.attr.colorPrimary));
//        SkinModeController.getInstance().addSkinView(qyIndicator);
//        mAdapter.add(0, new BannerAdapterInfo(bannerAdapter, qyIndicator), 0);
//
//    }
//
//    public void onFunctionSuccess(List<FunctionInfo> homeFunctionInfo) {
//        SingleTypeAdapter<FunctionInfo> functionInfoAdapter = new SingleTypeAdapter<>(getActivity(), R.layout.item_home_function);
//        functionInfoAdapter.set(homeFunctionInfo);
//        functionInfoAdapter.setPresenter(getP());
//        RecyclerAdapterInfo recyclerAdapterInfo = new RecyclerAdapterInfo(functionInfoAdapter, LayoutManagerUtil.getGridLayoutManager(getActivity(), homeFunctionInfo.size()));
//        mAdapter.add(recyclerAdapterInfo, 1);
//    }
//
//    public void onSuccess(List<List<GankResultsTypeInfo>> gankIoDayInfo) {
//        showContent();
//        if (gankIoDayInfo.size() > 0) {
//            for (int i = 0; i < gankIoDayInfo.size(); i++) {
//                List<GankResultsTypeInfo> gankResultsTypeInfo = gankIoDayInfo.getInstance(i);
//                mAdapter.addAll(gankResultsTypeInfo, 2);
//            }
//        } else {
////            mAdapter.add(new CanInfo(), 3);
//        }
////        mAdapter.add(new CanInfo(), 4);
//    }
//
//    public void onError(Throwable throwable) {
//        ToastUtils.showShort(throwable.getMessage());
//        showError();
//    }
//}
