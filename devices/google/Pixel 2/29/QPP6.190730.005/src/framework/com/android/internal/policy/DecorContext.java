/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.policy;

import android.content.AutofillOptions;
import android.content.ContentCaptureOptions;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManagerImpl;
import android.view.contentcapture.ContentCaptureManager;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.policy.PhoneWindow;
import java.lang.ref.WeakReference;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public class DecorContext
extends ContextThemeWrapper {
    private WeakReference<Context> mActivityContext;
    private Resources mActivityResources;
    private ContentCaptureManager mContentCaptureManager;
    private PhoneWindow mPhoneWindow;
    private WindowManager mWindowManager;

    @VisibleForTesting
    public DecorContext(Context context, Context context2) {
        super(context.createDisplayContext(context2.getDisplay()), null);
        this.mActivityContext = new WeakReference<Context>(context2);
        this.mActivityResources = context2.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return this.mActivityResources.getAssets();
    }

    @Override
    public AutofillOptions getAutofillOptions() {
        Context context = (Context)this.mActivityContext.get();
        if (context != null) {
            return context.getAutofillOptions();
        }
        return null;
    }

    @Override
    public ContentCaptureOptions getContentCaptureOptions() {
        Context context = (Context)this.mActivityContext.get();
        if (context != null) {
            return context.getContentCaptureOptions();
        }
        return null;
    }

    @Override
    public Resources getResources() {
        Context context = (Context)this.mActivityContext.get();
        if (context != null) {
            this.mActivityResources = context.getResources();
        }
        return this.mActivityResources;
    }

    @Override
    public Object getSystemService(String string2) {
        if ("window".equals(string2)) {
            if (this.mWindowManager == null) {
                this.mWindowManager = ((WindowManagerImpl)super.getSystemService("window")).createLocalWindowManager(this.mPhoneWindow);
            }
            return this.mWindowManager;
        }
        if ("content_capture".equals(string2)) {
            Context context;
            if (this.mContentCaptureManager == null && (context = (Context)this.mActivityContext.get()) != null) {
                this.mContentCaptureManager = (ContentCaptureManager)context.getSystemService(string2);
            }
            return this.mContentCaptureManager;
        }
        return super.getSystemService(string2);
    }

    void setPhoneWindow(PhoneWindow phoneWindow) {
        this.mPhoneWindow = phoneWindow;
        this.mWindowManager = null;
    }
}

