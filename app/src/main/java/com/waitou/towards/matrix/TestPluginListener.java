package com.waitou.towards.matrix;

import android.content.Context;

import com.tencent.matrix.plugin.DefaultPluginListener;
import com.tencent.matrix.report.Issue;
import com.tencent.matrix.util.MatrixLog;

/**
 * @author jarson, email:liqianjiang07157@hellobike.com
 * @date 2019/3/12
 */
public class TestPluginListener extends DefaultPluginListener {

    static final String TAG = "Matrix.TestPluginListener";

    public TestPluginListener(Context context) {
        super(context);
    }

    @Override
    public void onReportIssue(Issue issue) {
        super.onReportIssue(issue);
        MatrixLog.e(TAG, issue.toString());

        //add your code to process data
    }
}
