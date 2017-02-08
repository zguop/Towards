package com.waitou.towards.model.jokes.fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.waitou.towards.R;
import com.waitou.towards.databinding.FragmentHomeBinding;
import com.waitou.towards.model.presenter.MainPresenter;

import cn.droidlover.xdroid.base.XFragment;


/**
 * Created by waitou on 16/12/23.
 * 首页
 */

public class HomeFragment extends XFragment<MainPresenter,FragmentHomeBinding> {

    @Override
    public boolean initXView() {
        return false;
    }


    @Override
    public int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        getBinding().recyclerView.setAdapter(new Adapter());
//        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void reloadData() {

    }

    @Override
    public void setPresenter(MainPresenter presenter) {

    }


    public class presenter {
        public static final String ImageUrl = "https://avatars2.githubusercontent.com/u/1106500?v=3&s=460";

        public void onClick() {
            Toast.makeText(getActivity(), "sfsasa", Toast.LENGTH_SHORT).show();
        }
    }

//
//    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
//
//        @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            return new ViewHolder(View.inflate(parent.getContext(), R.layout.activity_recommended, null));
//        }
//
//        @Override public void onBindViewHolder(ViewHolder holder, int position) {
//
//        }
//
//        @Override public int getItemCount() {
//            return 100;
//        }
//
//        class ViewHolder extends RecyclerView.ViewHolder {
//            TextView textView;
//
//            public ViewHolder(View itemView) {
//                super(itemView);
//                textView = (TextView) itemView.findViewById(R.id.text);
//            }
//        }
//    }


}
