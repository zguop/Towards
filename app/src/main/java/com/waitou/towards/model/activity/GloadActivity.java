package com.waitou.towards.model.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.waitou.wt_library.base.BasePageActivity;
import com.waitou.wt_library.base.TitleBar;
import com.waitou.wt_library.manager.TitleBarManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * auth aboom
 * date 2019/4/6
 */
public class GloadActivity extends BasePageActivity {

    private TextView textView;

    @Override
    public void reloadData() {
        VM vm = ViewModelProviders.of(this).get(VM.class);
//        final Observer<Long> elapsedTimeObserver = new Observer<Long>() {
//            @Override
//            public void onChanged(@Nullable final Long aLong) {
//                Log.e("aa" , " along = "+ aLong);
//                textView.setText(String.valueOf(aLong));
//            }
//        };
        vm.getMap().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                textView.setText(String.valueOf(aBoolean));
            }
        });
    }

    List<String> data = new ArrayList<>();

    boolean add;

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        showContent();
        TitleBar titleBar = TitleBarManager.attachViewGet(this);
        titleBar.initializeHeader("我是标题");
        titleBar.setRightText("增加", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.clear();
                add = !add;

                if (add) {
                    for (int i = 0; i < 3; i++) {
                        data.add(i + " =====");
                    }
                } else {
                    for (int i = 0; i < 10; i++) {
                        data.add(i + " =====");
                    }
                }
            }
        });
        reloadData();
    }


    @Override
    public View getContentView() {
        textView = new TextView(this);
        textView.setTextSize(16);
        textView.setTextColor(Color.BLUE);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disposable subscribe = Observable.just(1, 2, 3)
                        .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
                            @Override
                            public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
                                Log.e("aa" , " throwable = " + throwable.getMessage());
                                return io.reactivex.Observer::onComplete;
                            }
                        })

                        .flatMap(new Function<Integer, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Integer integer) throws Exception {
                                return observer -> observer.onError(new Throwable("2111"));
                            }
                        })
                        .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
                            @Override
                            public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
                                Log.e("aa" , " throwable = " + throwable.getMessage());
                                return io.reactivex.Observer::onComplete;
                            }
                        })


                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {

                            }
                        });


//                new SheetAdapterDialog()
//                        .setTitle("标题")
//                        .setRecyclerAdapter(new MainAdapter(GloadActivity.this, data))
//                        .setItemHeight(SizeUtils.dp2px(40))
//                        .grid(4)
//                        .setHeight(SizeUtils.dp2px(350f))
//                        .show(getSupportFragmentManager());
//

//                NormalDialog normalDialog = new NormalDialog();
//                normalDialog.setGravity(Gravity.BOTTOM);
//                normalDialog.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
//                normalDialog.setWidget(WindowManager.LayoutParams.MATCH_PARENT);
//                normalDialog.setDialogView(new IDialogView() {
//                    @Override
//                    public View getContentView(Context activity) {
//                        View view = ff(R.layout.include_view_loading);
//                        return view;
//                    }
//                });
//                normalDialog.show(getSupportFragmentManager());
            }
        });
        return textView;
    }


    public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {

        private Context      context;
        private List<String> data;

        public MainAdapter(Context context, List<String> data) {
            this.context = context;
            this.data = data;
        }

        @NonNull
        @Override
        public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(context);
            return new MainHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull MainHolder holder, final int position) {
            String item = data.get(position);
            holder.mTextView.setText(item);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class MainHolder extends RecyclerView.ViewHolder {

            TextView mTextView;

            public MainHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView;
                mTextView.setTextSize(15);
                mTextView.setGravity(Gravity.CENTER);
                mTextView.setTextColor(Color.WHITE);
                mTextView.setPadding(40, 40, 40, 40);
                mTextView.setBackgroundColor(Color.BLACK);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mTextView.setLayoutParams(params);


            }
        }
    }
}
