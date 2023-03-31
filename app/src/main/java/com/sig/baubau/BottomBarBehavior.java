package com.sig.baubau;

import android.view.View;
import android.view.ViewPropertyAnimator;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import org.jetbrains.annotations.NotNull;

import kotlin.jvm.internal.Intrinsics;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class BottomBarBehavior extends  CoordinatorLayout.Behavior {
    private int height = 0;

    public boolean onLayoutChild(@NotNull CoordinatorLayout parent, @NotNull AnimatedBottomBar child, int layoutDirection) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(child, "child");
        this.height = child.getHeight();
        return super.onLayoutChild(parent, (View)child, layoutDirection);
    }

    // $FF: synthetic method
    // $FF: bridge method
    public boolean onLayoutChild(CoordinatorLayout var1, View var2, int var3) {
        return this.onLayoutChild(var1, (AnimatedBottomBar)var2, var3);
    }

    public boolean onStartNestedScroll(@NotNull CoordinatorLayout coordinatorLayout, @NotNull AnimatedBottomBar child, @NotNull View directTargetChild, @NotNull View target, int nestedScrollAxes) {
        Intrinsics.checkNotNullParameter(coordinatorLayout, "coordinatorLayout");
        Intrinsics.checkNotNullParameter(child, "child");
        Intrinsics.checkNotNullParameter(directTargetChild, "directTargetChild");
        Intrinsics.checkNotNullParameter(target, "target");
        return nestedScrollAxes == 2;
    }

    // $FF: synthetic method
    // $FF: bridge method
    public boolean onStartNestedScroll(CoordinatorLayout var1, View var2, View var3, View var4, int var5) {
        return this.onStartNestedScroll(var1, (AnimatedBottomBar)var2, var3, var4, var5);
    }

    public void onNestedScroll(@NotNull CoordinatorLayout coordinatorLayout, @NotNull AnimatedBottomBar child, @NotNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Intrinsics.checkNotNullParameter(coordinatorLayout, "coordinatorLayout");
        Intrinsics.checkNotNullParameter(child, "child");
        Intrinsics.checkNotNullParameter(target, "target");
        if (dyConsumed > 0) {
            this.slideDown(child);
        } else if (dyConsumed < 0) {
            this.slideUp(child);
        }

    }

    private final void slideUp(AnimatedBottomBar child) {
        child.clearAnimation();
        ViewPropertyAnimator var10000 = child.animate().translationY(0.0F);
        Intrinsics.checkNotNullExpressionValue(var10000, "child.animate().translationY(0f)");
        var10000.setDuration(200L);
    }

    private final void slideDown(AnimatedBottomBar child) {
        child.clearAnimation();
        ViewPropertyAnimator var10000 = child.animate().translationY((float)this.height);
        Intrinsics.checkNotNullExpressionValue(var10000, "child.animate().translationY(height.toFloat())");
        var10000.setDuration(200L);
    }
}
