/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.LayoutInflater;

public class ContextThemeWrapper
extends ContextWrapper {
    @UnsupportedAppUsage
    private LayoutInflater mInflater;
    private Configuration mOverrideConfiguration;
    @UnsupportedAppUsage
    private Resources mResources;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123768723L)
    private Resources.Theme mTheme;
    @UnsupportedAppUsage
    private int mThemeResource;

    public ContextThemeWrapper() {
        super(null);
    }

    public ContextThemeWrapper(Context context, int n) {
        super(context);
        this.mThemeResource = n;
    }

    public ContextThemeWrapper(Context context, Resources.Theme theme) {
        super(context);
        this.mTheme = theme;
    }

    private Resources getResourcesInternal() {
        if (this.mResources == null) {
            Configuration configuration = this.mOverrideConfiguration;
            this.mResources = configuration == null ? super.getResources() : this.createConfigurationContext(configuration).getResources();
        }
        return this.mResources;
    }

    @UnsupportedAppUsage
    private void initializeTheme() {
        boolean bl = this.mTheme == null;
        if (bl) {
            this.mTheme = this.getResources().newTheme();
            Resources.Theme theme = this.getBaseContext().getTheme();
            if (theme != null) {
                this.mTheme.setTo(theme);
            }
        }
        this.onApplyThemeResource(this.mTheme, this.mThemeResource, bl);
    }

    public void applyOverrideConfiguration(Configuration configuration) {
        if (this.mResources == null) {
            if (this.mOverrideConfiguration == null) {
                this.mOverrideConfiguration = new Configuration(configuration);
                return;
            }
            throw new IllegalStateException("Override configuration has already been set");
        }
        throw new IllegalStateException("getResources() or getAssets() has already been called");
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override
    public AssetManager getAssets() {
        return this.getResourcesInternal().getAssets();
    }

    public Configuration getOverrideConfiguration() {
        return this.mOverrideConfiguration;
    }

    @Override
    public Resources getResources() {
        return this.getResourcesInternal();
    }

    @Override
    public Object getSystemService(String string2) {
        if ("layout_inflater".equals(string2)) {
            if (this.mInflater == null) {
                this.mInflater = LayoutInflater.from(this.getBaseContext()).cloneInContext(this);
            }
            return this.mInflater;
        }
        return this.getBaseContext().getSystemService(string2);
    }

    @Override
    public Resources.Theme getTheme() {
        Resources.Theme theme = this.mTheme;
        if (theme != null) {
            return theme;
        }
        this.mThemeResource = Resources.selectDefaultTheme(this.mThemeResource, this.getApplicationInfo().targetSdkVersion);
        this.initializeTheme();
        return this.mTheme;
    }

    @UnsupportedAppUsage
    @Override
    public int getThemeResId() {
        return this.mThemeResource;
    }

    protected void onApplyThemeResource(Resources.Theme theme, int n, boolean bl) {
        theme.applyStyle(n, true);
    }

    @Override
    public void setTheme(int n) {
        if (this.mThemeResource != n) {
            this.mThemeResource = n;
            this.initializeTheme();
        }
    }

    public void setTheme(Resources.Theme theme) {
        this.mTheme = theme;
    }
}

