# imgloader_lib
基于glide4.8封装的工具类

## 使用
```groovy
dependencies{
    compile 'com.zmsoft.library:tdfglidecompat:1.0.3'  //最新版本
}
```

## ImageLoader.class 工具类


### 加载一张图片
```groovy
String url = "http://img.hb.aicdn.com/621034b37c53ffc81f5d6a23ae1226d5c67e2b9628267-BYuZLo_fw658";
ImageView img6 = findViewById(R.id.img);
ImageLoader.displayImage(img, url);

```


### 带回调的加载 BitmapImageViewTarget，DrawableImageViewTarget

```groovy
     ImageLoader.displayImage(hsImageLoaderView, url
            , new DrawableImageViewTarget(hsImageLoaderView) {
                @Override
                public void onLoadStarted(@Nullable Drawable placeholder) {
                    super.onLoadStarted(placeholder);
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                }

                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    super.onResourceReady(resource, transition);
                    //默认会设置图片到imageview上
                }
            });
```

### 参数配置 DisplayOptions.class

```groovy

     DisplayOptions o =  DisplayOptions.build()
                     .setPlaceholder() //占位图
                     .setError() //失败的图
                     .setTransformation()//对加载的图片的变换，展位图 和 加载失败显示的图片不生效
                     .setHeight()//设置图片加载的高
                     .setWidth()//设置图片加载的宽
       //目前只提供了那么多

       ImageLoader.displayImage(img, url, o);

```

### 内置圆角 和 圆形的一个 HsRoundImageView

```groovy
        //自定义的属性

            <declare-styleable name="HsRoundImageView">
                <attr name="hs_oval" format="boolean" /> //是否圆形
                <attr name="hs_cover_src" format="boolean" /> 绘制的边框线是否覆盖图片
                <attr name="hs_corner_radius" format="dimension" /> //圆角 这个优先极高
                <attr name="hs_corner_top_left_radius" format="dimension" /> //下面四个属性是 上下左右的四个圆角
                <attr name="hs_corner_top_right_radius" format="dimension" />
                <attr name="hs_corner_bottom_left_radius" format="dimension" />
                <attr name="hs_corner_bottom_right_radius" format="dimension" />
                <attr name="hs_border_width" format="dimension" /> //边框线
                <attr name="hs_border_color" format="color" /> //边框颜色
                <attr name="hs_inner_border_width" format="dimension" /> //内边框线
                <attr name="hs_inner_border_color" format="color" /> //内边框颜色
                <attr name="hs_mask_color" format="color" />// 绘制遮罩
            </declare-styleable>

        <com.zmsoft.module.tdfglidecompat.HsRoundImageView
            android:id="@+id/rounded1"
            android:layout_gravity="center_horizontal"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:src="@drawable/rest_widget_ico_default_avatar"
            app:hs_border_color="#008fea"
            app:hs_border_width="2dip"
            app:hs_corner_bottom_left_radius="4dp"
            app:hs_corner_top_left_radius="4dp"
            app:hs_corner_bottom_right_radius="4dp"
            app:hs_corner_top_right_radius="4dp"/>

            //可直接通过工具类对图片加载
           ImageLoader.displayImage(img, url, o);

```

### 对dataBinding的支持


```groovy
       // 如果你是写在dataBinding的xml中 可以下面这样

               <ImageView
                    android:layout_width="200dp"
                    android:layout_height="200dp"
                    app:imageURI="@{url}"
                    app:placeholderImage="@{@drawable/base_ic_retry}"
                    app:failureImage="@{@drawable/base_ic_retry}"/>
```