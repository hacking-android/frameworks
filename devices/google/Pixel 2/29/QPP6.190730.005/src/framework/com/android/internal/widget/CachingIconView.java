/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.widget.ImageView;
import android.widget.RemoteViews;
import java.util.Objects;

@RemoteViews.RemoteView
public class CachingIconView
extends ImageView {
    private int mDesiredVisibility;
    private boolean mForceHidden;
    private boolean mInternalSetDrawable;
    private String mLastPackage;
    private int mLastResId;

    @UnsupportedAppUsage
    public CachingIconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private String normalizeIconPackage(Icon object) {
        if (object == null) {
            return null;
        }
        if (TextUtils.isEmpty((CharSequence)(object = ((Icon)object).getResPackage()))) {
            return null;
        }
        if (((String)object).equals(this.mContext.getPackageName())) {
            return null;
        }
        return object;
    }

    private void resetCache() {
        synchronized (this) {
            this.mLastResId = 0;
            this.mLastPackage = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean testAndSetCache(int n) {
        synchronized (this) {
            Throwable throwable2;
            block6 : {
                boolean bl;
                block5 : {
                    block4 : {
                        if (n != 0) {
                            try {
                                if (this.mLastResId == 0) break block4;
                                bl = n == this.mLastResId && this.mLastPackage == null;
                                break block5;
                            }
                            catch (Throwable throwable2) {
                                break block6;
                            }
                        }
                    }
                    bl = false;
                }
                this.mLastPackage = null;
                this.mLastResId = n;
                return bl;
            }
            throw throwable2;
        }
    }

    private boolean testAndSetCache(Icon icon) {
        synchronized (this) {
            block6 : {
                boolean bl = false;
                if (icon != null) {
                    String string2;
                    block7 : {
                        if (icon.getType() != 2) break block6;
                        string2 = this.normalizeIconPackage(icon);
                        if (this.mLastResId == 0 || icon.getResId() != this.mLastResId || !Objects.equals(string2, this.mLastPackage)) break block7;
                        bl = true;
                    }
                    this.mLastPackage = string2;
                    this.mLastResId = icon.getResId();
                    return bl;
                }
            }
            this.resetCache();
            return false;
        }
    }

    private void updateVisibility() {
        int n = this.mDesiredVisibility == 0 && this.mForceHidden ? 4 : this.mDesiredVisibility;
        super.setVisibility(n);
    }

    @Override
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.resetCache();
    }

    public void setForceHidden(boolean bl) {
        this.mForceHidden = bl;
        this.updateVisibility();
    }

    @RemotableViewMethod
    @Override
    public void setImageBitmap(Bitmap bitmap) {
        this.resetCache();
        super.setImageBitmap(bitmap);
    }

    @Override
    public void setImageDrawable(Drawable drawable2) {
        if (!this.mInternalSetDrawable) {
            this.resetCache();
        }
        super.setImageDrawable(drawable2);
    }

    @RemotableViewMethod(asyncImpl="setImageIconAsync")
    @Override
    public void setImageIcon(Icon icon) {
        if (!this.testAndSetCache(icon)) {
            this.mInternalSetDrawable = true;
            super.setImageIcon(icon);
            this.mInternalSetDrawable = false;
        }
    }

    @Override
    public Runnable setImageIconAsync(Icon icon) {
        this.resetCache();
        return super.setImageIconAsync(icon);
    }

    @RemotableViewMethod(asyncImpl="setImageResourceAsync")
    @Override
    public void setImageResource(int n) {
        if (!this.testAndSetCache(n)) {
            this.mInternalSetDrawable = true;
            super.setImageResource(n);
            this.mInternalSetDrawable = false;
        }
    }

    @Override
    public Runnable setImageResourceAsync(int n) {
        this.resetCache();
        return super.setImageResourceAsync(n);
    }

    @RemotableViewMethod(asyncImpl="setImageURIAsync")
    @Override
    public void setImageURI(Uri uri) {
        this.resetCache();
        super.setImageURI(uri);
    }

    @Override
    public Runnable setImageURIAsync(Uri uri) {
        this.resetCache();
        return super.setImageURIAsync(uri);
    }

    @RemotableViewMethod
    @Override
    public void setVisibility(int n) {
        this.mDesiredVisibility = n;
        this.updateVisibility();
    }
}

