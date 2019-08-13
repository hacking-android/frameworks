/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.view.View;
import android.view.ViewTreeObserver;

public class OneShotPreDrawListener
implements ViewTreeObserver.OnPreDrawListener,
View.OnAttachStateChangeListener {
    private final boolean mReturnValue;
    private final Runnable mRunnable;
    private final View mView;
    private ViewTreeObserver mViewTreeObserver;

    private OneShotPreDrawListener(View view, boolean bl, Runnable runnable) {
        this.mView = view;
        this.mViewTreeObserver = view.getViewTreeObserver();
        this.mRunnable = runnable;
        this.mReturnValue = bl;
    }

    public static OneShotPreDrawListener add(View view, Runnable runnable) {
        return OneShotPreDrawListener.add(view, true, runnable);
    }

    public static OneShotPreDrawListener add(View view, boolean bl, Runnable object) {
        object = new OneShotPreDrawListener(view, bl, (Runnable)object);
        view.getViewTreeObserver().addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)object);
        view.addOnAttachStateChangeListener((View.OnAttachStateChangeListener)object);
        return object;
    }

    @Override
    public boolean onPreDraw() {
        this.removeListener();
        this.mRunnable.run();
        return this.mReturnValue;
    }

    @Override
    public void onViewAttachedToWindow(View view) {
        this.mViewTreeObserver = view.getViewTreeObserver();
    }

    @Override
    public void onViewDetachedFromWindow(View view) {
        this.removeListener();
    }

    public void removeListener() {
        if (this.mViewTreeObserver.isAlive()) {
            this.mViewTreeObserver.removeOnPreDrawListener(this);
        } else {
            this.mView.getViewTreeObserver().removeOnPreDrawListener(this);
        }
        this.mView.removeOnAttachStateChangeListener(this);
    }
}

