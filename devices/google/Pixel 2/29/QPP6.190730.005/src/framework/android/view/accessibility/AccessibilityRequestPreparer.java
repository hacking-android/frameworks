/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

public abstract class AccessibilityRequestPreparer {
    public static final int REQUEST_TYPE_EXTRA_DATA = 1;
    private final int mAccessibilityViewId;
    private final int mRequestTypes;
    private final WeakReference<View> mViewRef;

    public AccessibilityRequestPreparer(View view, int n) {
        if (view.isAttachedToWindow()) {
            this.mViewRef = new WeakReference<View>(view);
            this.mAccessibilityViewId = view.getAccessibilityViewId();
            this.mRequestTypes = n;
            view.addOnAttachStateChangeListener(new ViewAttachStateListener());
            return;
        }
        throw new IllegalStateException("View must be attached to a window");
    }

    int getAccessibilityViewId() {
        return this.mAccessibilityViewId;
    }

    public View getView() {
        return (View)this.mViewRef.get();
    }

    public abstract void onPrepareExtraData(int var1, String var2, Bundle var3, Message var4);

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RequestTypes {
    }

    private class ViewAttachStateListener
    implements View.OnAttachStateChangeListener {
        private ViewAttachStateListener() {
        }

        @Override
        public void onViewAttachedToWindow(View view) {
        }

        @Override
        public void onViewDetachedFromWindow(View view) {
            Context context = view.getContext();
            if (context != null) {
                context.getSystemService(AccessibilityManager.class).removeAccessibilityRequestPreparer(AccessibilityRequestPreparer.this);
            }
            view.removeOnAttachStateChangeListener(this);
        }
    }

}

