package com.waitou.towards.util.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

/**
 * Simple layout behavior that will track the state of the AppBarLayout
 * and match its offset for a corresponding footer.
 */
public class FooterBarBehavior extends CoordinatorLayout.Behavior<View> {
    //Required to instantiate as a default behavior
    public FooterBarBehavior() {
    }

    //Required to attach behavior via XML
    public FooterBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //This is called to determine which views this behavior depends on
    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof Toolbar;
    }

    //This is called for each change to a dependent view
    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        int offset = -dependency.getTop();
        child.setTranslationY(offset);
        return true;
    }
}