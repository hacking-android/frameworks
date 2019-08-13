/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import com.android.internal.R;

public class ActionBarPolicy {
    @UnsupportedAppUsage
    private Context mContext;

    @UnsupportedAppUsage
    private ActionBarPolicy(Context context) {
        this.mContext = context;
    }

    @UnsupportedAppUsage
    public static ActionBarPolicy get(Context context) {
        return new ActionBarPolicy(context);
    }

    public boolean enableHomeButtonByDefault() {
        boolean bl = this.mContext.getApplicationInfo().targetSdkVersion < 14;
        return bl;
    }

    @UnsupportedAppUsage
    public int getEmbeddedMenuWidthLimit() {
        return this.mContext.getResources().getDisplayMetrics().widthPixels / 2;
    }

    @UnsupportedAppUsage
    public int getMaxActionButtons() {
        Configuration configuration = this.mContext.getResources().getConfiguration();
        int n = configuration.screenWidthDp;
        int n2 = configuration.screenHeightDp;
        if (!(configuration.smallestScreenWidthDp > 600 || n > 960 && n2 > 720 || n > 720 && n2 > 960)) {
            if (!(n >= 500 || n > 640 && n2 > 480 || n > 480 && n2 > 640)) {
                if (n >= 360) {
                    return 3;
                }
                return 2;
            }
            return 4;
        }
        return 5;
    }

    @UnsupportedAppUsage
    public int getStackedTabMaxWidth() {
        return this.mContext.getResources().getDimensionPixelSize(17104922);
    }

    @UnsupportedAppUsage
    public int getTabContainerHeight() {
        TypedArray typedArray = this.mContext.obtainStyledAttributes(null, R.styleable.ActionBar, 16843470, 0);
        int n = typedArray.getLayoutDimension(4, 0);
        Resources resources = this.mContext.getResources();
        int n2 = n;
        if (!this.hasEmbeddedTabs()) {
            n2 = Math.min(n, resources.getDimensionPixelSize(17104921));
        }
        typedArray.recycle();
        return n2;
    }

    @UnsupportedAppUsage
    public boolean hasEmbeddedTabs() {
        if (this.mContext.getApplicationInfo().targetSdkVersion >= 16) {
            return this.mContext.getResources().getBoolean(17891334);
        }
        Configuration configuration = this.mContext.getResources().getConfiguration();
        int n = configuration.screenWidthDp;
        int n2 = configuration.screenHeightDp;
        boolean bl = configuration.orientation == 2 || n >= 480 || n >= 640 && n2 >= 480;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean showsOverflowMenuButton() {
        return true;
    }
}

