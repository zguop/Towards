package com.waitou.towards;

import com.networkbench.agent.impl.NBSAppAgent;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.iocanary.IOCanaryPlugin;
import com.tencent.matrix.iocanary.config.IOConfig;
import com.to.aboomy.tinker_lib.TinkerApplicationBase;
import com.waitou.towards.matrix.DynamicConfigImplDemo;
import com.waitou.towards.matrix.TestPluginListener;

/**
 * author   itxp
 * date     2018/5/6 0:15
 * des
 */

public class TowardsApplication extends TinkerApplicationBase{

    public TowardsApplication() {
        super(TowardsApplicationLike.class.getName());

    }

    @Override
    public void onCreate() {
        super.onCreate();

        Matrix.Builder builder = new Matrix.Builder(this); // build matrix
        builder.patchListener(new TestPluginListener(this)); // add general pluginListener
        DynamicConfigImplDemo dynamicConfig = new DynamicConfigImplDemo(); // dynamic config

        // init plugin
        IOCanaryPlugin ioCanaryPlugin = new IOCanaryPlugin(new IOConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .build());
        //add to matrix
        builder.plugin(ioCanaryPlugin);

        //init matrix
        Matrix.init(builder.build());

        // start plugin
        ioCanaryPlugin.start();
//                                   6716ac569657475290bc4831c4b4503c
        NBSAppAgent.setLicenseKey("6716ac569657475290bc4831c4b4503c")
                .withLocationServiceEnabled(true)
                .start(this.getApplicationContext());//Appkey 请从官网获取
    }
}
